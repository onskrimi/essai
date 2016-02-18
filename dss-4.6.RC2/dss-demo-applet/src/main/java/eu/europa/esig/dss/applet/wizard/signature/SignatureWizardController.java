/**
 * DSS - Digital Signature Services
 * Copyright (C) 2015 European Commission, provided under the CEF programme
 *
 * This file is part of the "DSS - Digital Signature Services" project.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package eu.europa.esig.dss.applet.wizard.signature;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;

import eu.europa.esig.dss.DSSDocument;
import eu.europa.esig.dss.DSSException;
import eu.europa.esig.dss.DigestAlgorithm;
import eu.europa.esig.dss.Policy;
import eu.europa.esig.dss.RemoteCertificate;
import eu.europa.esig.dss.RemoteSignatureParameters;
import eu.europa.esig.dss.SignatureLevel;
import eu.europa.esig.dss.applet.controller.DSSWizardController;
import eu.europa.esig.dss.applet.main.DSSAppletCore;
import eu.europa.esig.dss.applet.model.SignatureModel;
import eu.europa.esig.dss.applet.swing.mvc.wizard.WizardController;
import eu.europa.esig.dss.applet.swing.mvc.wizard.WizardStep;
import eu.europa.esig.dss.applet.util.SigningUtils;
import eu.europa.esig.dss.applet.view.signature.CertificateView;
import eu.europa.esig.dss.applet.view.signature.FileView;
import eu.europa.esig.dss.applet.view.signature.FinishView;
import eu.europa.esig.dss.applet.view.signature.PKCS11View;
import eu.europa.esig.dss.applet.view.signature.PKCS12View;
import eu.europa.esig.dss.applet.view.signature.PersonalDataView;
import eu.europa.esig.dss.applet.view.signature.SaveView;
import eu.europa.esig.dss.applet.view.signature.SignatureDigestAlgorithmView;
import eu.europa.esig.dss.applet.view.signature.SignatureView;
import eu.europa.esig.dss.applet.view.signature.TokenView;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.token.SignatureTokenConnection;
import eu.europa.esig.dss.x509.CertificateToken;

public class SignatureWizardController extends DSSWizardController<SignatureModel> {

	private FileView fileView;
	private SignatureView signatureView;
	private TokenView tokenView;
	private PKCS11View pkcs11View;
	private PKCS12View pkcs12View;
	private SignatureDigestAlgorithmView signatureDigestAlgorithmView;
	private CertificateView certificateView;
	private PersonalDataView personalDataView;
	private SaveView saveView;
	private FinishView signView;

	/**
	 * The default constructor for SignatureWizardController.
	 *
	 * @param core
	 * @param model
	 */
	public SignatureWizardController(final DSSAppletCore core, final SignatureModel model) {
		super(core, model);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.europa.esig.dss.applet.swing.mvc.wizard.WizardController#doCancel()
	 */
	@Override
	protected void doCancel() {
	}

	/**
	 *
	 */
	public void doRefreshPrivateKeys() {

		try {
			final SignatureTokenConnection tokenConnection = getModel().getTokenConnection();
			getModel().setPrivateKeys(tokenConnection.getKeys());
		} catch (final DSSException e) {
			// FIXME
			LOG.error(e.getMessage(), e);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.europa.esig.dss.applet.swing.mvc.wizard.WizardController#doStart()
	 */
	@Override
	protected Class<? extends WizardStep<SignatureModel, ? extends WizardController<SignatureModel>>> doStart() {

		return FileStep.class;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.europa.esig.dss.applet.swing.mvc.wizard.WizardController#registerViews()
	 */
	@Override
	protected void registerViews() {

		fileView = new FileView(getCore(), this, getModel());
		signatureView = new SignatureView(getCore(), this, getModel());
		tokenView = new TokenView(getCore(), this, getModel());
		pkcs11View = new PKCS11View(getCore(), this, getModel());
		pkcs12View = new PKCS12View(getCore(), this, getModel());
		signatureDigestAlgorithmView = new SignatureDigestAlgorithmView(getCore(), this, getModel());
		certificateView = new CertificateView(getCore(), this, getModel());
		personalDataView = new PersonalDataView(getCore(), this, getModel());
		saveView = new SaveView(getCore(), this, getModel());
		signView = new FinishView(getCore(), this, getModel());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.europa.esig.dss.applet.swing.mvc.wizard.WizardController#registerWizardStep()
	 */
	@Override
	protected Map<Class<? extends WizardStep<SignatureModel, ? extends WizardController<SignatureModel>>>, ? extends WizardStep<SignatureModel, ? extends WizardController<SignatureModel>>> registerWizardStep() {

		final SignatureModel model = getModel();

		final Map steps = new HashMap();
		steps.put(FileStep.class, new FileStep(model, fileView, this));
		steps.put(SignatureStep.class, new SignatureStep(model, signatureView, this));
		steps.put(TokenStep.class, new TokenStep(model, tokenView, this));
		steps.put(PKCS11Step.class, new PKCS11Step(model, pkcs11View, this));
		steps.put(PKCS12Step.class, new PKCS12Step(model, pkcs12View, this));
		steps.put(SignatureDigestAlgorithmStep.class, new SignatureDigestAlgorithmStep(model, signatureDigestAlgorithmView, this));
		steps.put(CertificateStep.class, new CertificateStep(model, certificateView, this));
		steps.put(PersonalDataStep.class, new PersonalDataStep(model, personalDataView, this));
		steps.put(SaveStep.class, new SaveStep(model, saveView, this));
		steps.put(FinishStep.class, new FinishStep(model, signView, this));

		return steps;
	}

	/**
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws DSSException
	 */
	public void signDocument() throws IOException, NoSuchAlgorithmException, DSSException {

		final SignatureModel model = getModel();

		final File fileToSign = model.getSelectedFile();
		final SignatureTokenConnection tokenConnection = model.getTokenConnection();
		final DSSPrivateKeyEntry privateKey = model.getSelectedPrivateKey();

		final RemoteSignatureParameters parameters = new RemoteSignatureParameters();

		RemoteCertificate signingCertificate = new RemoteCertificate(privateKey.getCertificate().getEncoded());
		parameters.setSigningCertificate(signingCertificate);

		List<RemoteCertificate> chainCertificateList = new ArrayList<RemoteCertificate>();
		chainCertificateList.add(signingCertificate);

		CertificateToken[] certificateChain = privateKey.getCertificateChain();
		if (ArrayUtils.isNotEmpty(certificateChain)){
			for (CertificateToken certificateToken : certificateChain) {
				chainCertificateList.add(new RemoteCertificate(certificateToken.getEncoded()));
			}
		}
		parameters.setCertificateChain(chainCertificateList);

		parameters.setEncryptionAlgorithm(privateKey.getEncryptionAlgorithm());

		parameters.bLevel().setSigningDate(new Date());

		DigestAlgorithm digestAlgorithm = model.getSignatureDigestAlgorithm();
		if (digestAlgorithm == null) {
			parameters.setDigestAlgorithm(DigestAlgorithm.SHA256);
		} else {
			parameters.setDigestAlgorithm(digestAlgorithm);
		}

		prepareCommonSignature(model, parameters);

		final DSSDocument signedDocument = SigningUtils.signDocument(serviceURL, fileToSign, parameters, privateKey, tokenConnection);
		final FileOutputStream fileOutputStream = new FileOutputStream(model.getTargetFile());
		final InputStream inputStream = signedDocument.openStream();
		IOUtils.copy(inputStream, fileOutputStream);
		IOUtils.closeQuietly(inputStream);
		IOUtils.closeQuietly(fileOutputStream);
	}

	private void prepareCommonSignature(SignatureModel model, RemoteSignatureParameters parameters) {

		final String signatureLevelString = model.getLevel();
		parameters.setSignatureLevel(SignatureLevel.valueByName(signatureLevelString));
		parameters.setSignaturePackaging(model.getPackaging());

		if (model.isClaimedCheck()) {
			parameters.bLevel().addClaimedSignerRole(model.getClaimedRole());
		}

		if (model.isSignaturePolicyCheck()) {
			final byte[] hashValue = Base64.decodeBase64(model.getSignaturePolicyValue());
			final Policy policy = new Policy();
			policy.setId(model.getSignaturePolicyId());
			final DigestAlgorithm policyDigestAlgorithm = DigestAlgorithm.valueOf(model.getSignaturePolicyAlgo());
			policy.setDigestAlgorithm(policyDigestAlgorithm);
			policy.setDigestValue(hashValue);
			parameters.bLevel().setSignaturePolicy(policy);
		}
	}

}
