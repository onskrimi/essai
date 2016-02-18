package eu.europa.esig.dss.standalone.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.FutureTask;

import javafx.application.Platform;
import javafx.concurrent.Task;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import eu.europa.esig.dss.BLevelParameters;
import eu.europa.esig.dss.DSSDocument;
import eu.europa.esig.dss.FileDocument;
import eu.europa.esig.dss.RemoteCertificate;
import eu.europa.esig.dss.RemoteDocument;
import eu.europa.esig.dss.RemoteSignatureParameters;
import eu.europa.esig.dss.SignatureForm;
import eu.europa.esig.dss.SignatureValue;
import eu.europa.esig.dss.ToBeSigned;
import eu.europa.esig.dss.signature.RemoteDocumentSignatureService;
import eu.europa.esig.dss.standalone.exception.ApplicationException;
import eu.europa.esig.dss.standalone.model.SignatureModel;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.token.MSCAPISignatureToken;
import eu.europa.esig.dss.token.Pkcs11SignatureToken;
import eu.europa.esig.dss.token.Pkcs12SignatureToken;
import eu.europa.esig.dss.token.SignatureTokenConnection;
import eu.europa.esig.dss.x509.CertificateToken;

public class SigningTask extends Task<DSSDocument> {

	private RemoteDocumentSignatureService<RemoteDocument, RemoteSignatureParameters> service;
	private SignatureModel model;

	public SigningTask(RemoteDocumentSignatureService<RemoteDocument, RemoteSignatureParameters> service, SignatureModel model) {
		this.service = service;
		this.model = model;
	}

	@Override
	protected DSSDocument call() throws Exception {
		updateProgress(0, 100);
		SignatureTokenConnection token = getToken(model);

		updateProgress(5, 100);
		List<DSSPrivateKeyEntry> keys = token.getKeys();

		updateProgress(10, 100);

		DSSPrivateKeyEntry signer = getSigner(keys);

		RemoteDocument toSignDocument = new RemoteDocument(new FileDocument(model.getFileToSign()));
		RemoteSignatureParameters parameters = buildParameters(signer);

		ToBeSigned toBeSigned = getDataToSign(toSignDocument, parameters);
		SignatureValue signatureValue = signDigest(token, signer, toBeSigned);
		DSSDocument signDocument = signDocument(toSignDocument, parameters, signatureValue);
		updateProgress(100, 100);

		return signDocument;
	}

	private RemoteSignatureParameters buildParameters(DSSPrivateKeyEntry signer) {
		updateProgress(20, 100);
		RemoteSignatureParameters parameters = new RemoteSignatureParameters();
		parameters.setDigestAlgorithm(model.getDigestAlgorithm());
		parameters.setSignatureLevel(model.getSignatureLevel());
		parameters.setSignaturePackaging(model.getSignaturePackaging());
		BLevelParameters bLevelParams = new BLevelParameters();
		bLevelParams.setSigningDate(new Date());
		parameters.setBLevelParams(bLevelParams);

		if (SignatureForm.ASiC_S.equals(model.getSignatureForm()) || SignatureForm.ASiC_E.equals(model.getSignatureForm())) {
			parameters.setUnderlyingASiCForm(model.getAsicUnderlyingForm());
		}

		parameters.setSigningCertificate(new RemoteCertificate(signer.getCertificate().getEncoded()));
		parameters.setEncryptionAlgorithm(signer.getEncryptionAlgorithm());
		CertificateToken[] certificateChain = signer.getCertificateChain();
		if (ArrayUtils.isNotEmpty(certificateChain)) {
			List<RemoteCertificate> certificateChainList = new ArrayList<RemoteCertificate>();
			for (CertificateToken certificateToken : certificateChain) {
				certificateChainList.add(new RemoteCertificate(certificateToken.getEncoded()));
			}
			parameters.setCertificateChain(certificateChainList);
		}

		return parameters;
	}

	private ToBeSigned getDataToSign(RemoteDocument toSignDocument, RemoteSignatureParameters parameters) {
		updateProgress(25, 100);
		ToBeSigned toBeSigned = null;
		try {
			toBeSigned = service.getDataToSign(toSignDocument, parameters);
		} catch (Exception e) {
			throwException("Unable to compute the digest to sign", e);
		}
		return toBeSigned;
	}

	private SignatureValue signDigest(SignatureTokenConnection token, DSSPrivateKeyEntry signer, ToBeSigned toBeSigned) {
		updateProgress(50, 100);
		SignatureValue signatureValue = null;
		try {
			signatureValue = token.sign(toBeSigned, model.getDigestAlgorithm(), signer);
		} catch (Exception e) {
			throwException("Unable to sign the digest", e);
		}
		return signatureValue;
	}

	private DSSDocument signDocument(RemoteDocument toSignDocument, RemoteSignatureParameters parameters, SignatureValue signatureValue) {
		updateProgress(75, 100);
		DSSDocument signDocument = null;
		try {
			signDocument = service.signDocument(toSignDocument, parameters, signatureValue);
		} catch (Exception e) {
			throwException("Unable to sign the document", e);
		}
		return signDocument;
	}

	private DSSPrivateKeyEntry getSigner(List<DSSPrivateKeyEntry> keys) throws Exception {
		DSSPrivateKeyEntry selectedKey = null;
		if (CollectionUtils.isEmpty(keys)) {
			throwException("No certificate found", null);
		} else if (CollectionUtils.size(keys) == 1) {
			selectedKey = keys.get(0);
		} else {
			FutureTask<DSSPrivateKeyEntry> future = new FutureTask<DSSPrivateKeyEntry>(new SelectCertificateTask(keys));
			Platform.runLater(future);
			selectedKey = future.get();
			if (selectedKey == null) {
				throwException("No selected certificate", null);
			}
		}
		return selectedKey;
	}

	private SignatureTokenConnection getToken(SignatureModel model) {
		switch (model.getTokenType()) {
			case PKCS11:
				return new Pkcs11SignatureToken(model.getPkcsFile().getAbsolutePath(), model.getPassword().toCharArray());
			case PKCS12:
				return new Pkcs12SignatureToken(model.getPassword().toCharArray(), model.getPkcsFile());
			case MSCAPI:
				return new MSCAPISignatureToken();
			default:
				throw new IllegalArgumentException("Unsupported token type " + model.getTokenType());
		}
	}

	private void throwException(String message, Exception e) {
		String exceptionMessage = message + ((e != null) ? " : " + e.getMessage() : "");
		updateMessage(exceptionMessage);
		failed();
		throw new ApplicationException(exceptionMessage, e);
	}

}
