//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.15 at 09:48:58 AM CEST 
//


package eu.europa.esig.jaxb.policy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TimestampConstraints complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TimestampConstraints">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TimestampDelay" type="{http://dss.esig.europa.eu/validation/diagnostic}TimeConstraint" minOccurs="0"/>
 *         &lt;element name="MessageImprintDataFound" type="{http://dss.esig.europa.eu/validation/diagnostic}LevelConstraint" minOccurs="0"/>
 *         &lt;element name="MessageImprintDataIntact" type="{http://dss.esig.europa.eu/validation/diagnostic}LevelConstraint" minOccurs="0"/>
 *         &lt;element name="RevocationTimeAgainstBestSignatureTime" type="{http://dss.esig.europa.eu/validation/diagnostic}LevelConstraint" minOccurs="0"/>
 *         &lt;element name="BestSignatureTimeBeforeIssuanceDateOfSigningCertificate" type="{http://dss.esig.europa.eu/validation/diagnostic}LevelConstraint" minOccurs="0"/>
 *         &lt;element name="SigningCertificateValidityAtBestSignatureTime" type="{http://dss.esig.europa.eu/validation/diagnostic}LevelConstraint" minOccurs="0"/>
 *         &lt;element name="AlgorithmReliableAtBestSignatureTime" type="{http://dss.esig.europa.eu/validation/diagnostic}LevelConstraint" minOccurs="0"/>
 *         &lt;element name="Coherence" type="{http://dss.esig.europa.eu/validation/diagnostic}LevelConstraint" minOccurs="0"/>
 *         &lt;element name="SigningCertificate" type="{http://dss.esig.europa.eu/validation/diagnostic}CertificateConstraints" minOccurs="0"/>
 *         &lt;element name="CACertificate" type="{http://dss.esig.europa.eu/validation/diagnostic}CertificateConstraints" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimestampConstraints", propOrder = {
    "timestampDelay",
    "messageImprintDataFound",
    "messageImprintDataIntact",
    "revocationTimeAgainstBestSignatureTime",
    "bestSignatureTimeBeforeIssuanceDateOfSigningCertificate",
    "signingCertificateValidityAtBestSignatureTime",
    "algorithmReliableAtBestSignatureTime",
    "coherence",
    "signingCertificate",
    "caCertificate"
})
public class TimestampConstraints {

    @XmlElement(name = "TimestampDelay")
    protected TimeConstraint timestampDelay;
    @XmlElement(name = "MessageImprintDataFound")
    protected LevelConstraint messageImprintDataFound;
    @XmlElement(name = "MessageImprintDataIntact")
    protected LevelConstraint messageImprintDataIntact;
    @XmlElement(name = "RevocationTimeAgainstBestSignatureTime")
    protected LevelConstraint revocationTimeAgainstBestSignatureTime;
    @XmlElement(name = "BestSignatureTimeBeforeIssuanceDateOfSigningCertificate")
    protected LevelConstraint bestSignatureTimeBeforeIssuanceDateOfSigningCertificate;
    @XmlElement(name = "SigningCertificateValidityAtBestSignatureTime")
    protected LevelConstraint signingCertificateValidityAtBestSignatureTime;
    @XmlElement(name = "AlgorithmReliableAtBestSignatureTime")
    protected LevelConstraint algorithmReliableAtBestSignatureTime;
    @XmlElement(name = "Coherence")
    protected LevelConstraint coherence;
    @XmlElement(name = "SigningCertificate")
    protected CertificateConstraints signingCertificate;
    @XmlElement(name = "CACertificate")
    protected CertificateConstraints caCertificate;

    /**
     * Gets the value of the timestampDelay property.
     * 
     * @return
     *     possible object is
     *     {@link TimeConstraint }
     *     
     */
    public TimeConstraint getTimestampDelay() {
        return timestampDelay;
    }

    /**
     * Sets the value of the timestampDelay property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeConstraint }
     *     
     */
    public void setTimestampDelay(TimeConstraint value) {
        this.timestampDelay = value;
    }

    /**
     * Gets the value of the messageImprintDataFound property.
     * 
     * @return
     *     possible object is
     *     {@link LevelConstraint }
     *     
     */
    public LevelConstraint getMessageImprintDataFound() {
        return messageImprintDataFound;
    }

    /**
     * Sets the value of the messageImprintDataFound property.
     * 
     * @param value
     *     allowed object is
     *     {@link LevelConstraint }
     *     
     */
    public void setMessageImprintDataFound(LevelConstraint value) {
        this.messageImprintDataFound = value;
    }

    /**
     * Gets the value of the messageImprintDataIntact property.
     * 
     * @return
     *     possible object is
     *     {@link LevelConstraint }
     *     
     */
    public LevelConstraint getMessageImprintDataIntact() {
        return messageImprintDataIntact;
    }

    /**
     * Sets the value of the messageImprintDataIntact property.
     * 
     * @param value
     *     allowed object is
     *     {@link LevelConstraint }
     *     
     */
    public void setMessageImprintDataIntact(LevelConstraint value) {
        this.messageImprintDataIntact = value;
    }

    /**
     * Gets the value of the revocationTimeAgainstBestSignatureTime property.
     * 
     * @return
     *     possible object is
     *     {@link LevelConstraint }
     *     
     */
    public LevelConstraint getRevocationTimeAgainstBestSignatureTime() {
        return revocationTimeAgainstBestSignatureTime;
    }

    /**
     * Sets the value of the revocationTimeAgainstBestSignatureTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link LevelConstraint }
     *     
     */
    public void setRevocationTimeAgainstBestSignatureTime(LevelConstraint value) {
        this.revocationTimeAgainstBestSignatureTime = value;
    }

    /**
     * Gets the value of the bestSignatureTimeBeforeIssuanceDateOfSigningCertificate property.
     * 
     * @return
     *     possible object is
     *     {@link LevelConstraint }
     *     
     */
    public LevelConstraint getBestSignatureTimeBeforeIssuanceDateOfSigningCertificate() {
        return bestSignatureTimeBeforeIssuanceDateOfSigningCertificate;
    }

    /**
     * Sets the value of the bestSignatureTimeBeforeIssuanceDateOfSigningCertificate property.
     * 
     * @param value
     *     allowed object is
     *     {@link LevelConstraint }
     *     
     */
    public void setBestSignatureTimeBeforeIssuanceDateOfSigningCertificate(LevelConstraint value) {
        this.bestSignatureTimeBeforeIssuanceDateOfSigningCertificate = value;
    }

    /**
     * Gets the value of the signingCertificateValidityAtBestSignatureTime property.
     * 
     * @return
     *     possible object is
     *     {@link LevelConstraint }
     *     
     */
    public LevelConstraint getSigningCertificateValidityAtBestSignatureTime() {
        return signingCertificateValidityAtBestSignatureTime;
    }

    /**
     * Sets the value of the signingCertificateValidityAtBestSignatureTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link LevelConstraint }
     *     
     */
    public void setSigningCertificateValidityAtBestSignatureTime(LevelConstraint value) {
        this.signingCertificateValidityAtBestSignatureTime = value;
    }

    /**
     * Gets the value of the algorithmReliableAtBestSignatureTime property.
     * 
     * @return
     *     possible object is
     *     {@link LevelConstraint }
     *     
     */
    public LevelConstraint getAlgorithmReliableAtBestSignatureTime() {
        return algorithmReliableAtBestSignatureTime;
    }

    /**
     * Sets the value of the algorithmReliableAtBestSignatureTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link LevelConstraint }
     *     
     */
    public void setAlgorithmReliableAtBestSignatureTime(LevelConstraint value) {
        this.algorithmReliableAtBestSignatureTime = value;
    }

    /**
     * Gets the value of the coherence property.
     * 
     * @return
     *     possible object is
     *     {@link LevelConstraint }
     *     
     */
    public LevelConstraint getCoherence() {
        return coherence;
    }

    /**
     * Sets the value of the coherence property.
     * 
     * @param value
     *     allowed object is
     *     {@link LevelConstraint }
     *     
     */
    public void setCoherence(LevelConstraint value) {
        this.coherence = value;
    }

    /**
     * Gets the value of the signingCertificate property.
     * 
     * @return
     *     possible object is
     *     {@link CertificateConstraints }
     *     
     */
    public CertificateConstraints getSigningCertificate() {
        return signingCertificate;
    }

    /**
     * Sets the value of the signingCertificate property.
     * 
     * @param value
     *     allowed object is
     *     {@link CertificateConstraints }
     *     
     */
    public void setSigningCertificate(CertificateConstraints value) {
        this.signingCertificate = value;
    }

    /**
     * Gets the value of the caCertificate property.
     * 
     * @return
     *     possible object is
     *     {@link CertificateConstraints }
     *     
     */
    public CertificateConstraints getCACertificate() {
        return caCertificate;
    }

    /**
     * Sets the value of the caCertificate property.
     * 
     * @param value
     *     allowed object is
     *     {@link CertificateConstraints }
     *     
     */
    public void setCACertificate(CertificateConstraints value) {
        this.caCertificate = value;
    }

}
