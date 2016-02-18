<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h2><spring:message code="label.signADocument"/></h2>

<c:set var="backslash">\</c:set>
<c:set var="backslashReplace">\\</c:set>

<script src="//www.java.com/js/deployJava.js"></script>
<script type="text/javascript">
    var attributes = {
        width: 1,
        height: 1
    };
    var parameters = {
        operation : 'load_certificates',
        token : '<c:out value="${signatureDocumentForm.token}"/>',
        pkcs11LibPath : '<c:out value="${fn:replace(signatureDocumentForm.pkcsPath, backslash, backslashReplace)}" />',
        pkcs11Pwd : '<c:out value="${signatureDocumentForm.pkcsPassword}"/>',
        jnlp_href: 'jnlp/light-applet.jnlp'
    };
    var version = '1.6';
    deployJava.runApplet(attributes, parameters, version);
</script>

<form:form method="post" modelAttribute="signatureDocumentForm" cssClass="form-horizontal" enctype="multipart/form-data">

    <div class="form-group">
        <form:label path="base64Certificate" cssClass="col-sm-2 control-label">
            <spring:message code="label.select.certificate" />
        </form:label>
        <div class="col-sm-10" id="certificate"></div>
        <div class="col-sm-10" id="certificateChain"></div>
        <div class="col-sm-10" id="encryptionAlgo"></div>
    </div>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary" name="data-to-sign">
                <spring:message code="label.submit" />
            </button>
        </div>
    </div>

</form:form>

<script type="text/javascript">
    var mapCertificateChain = new Object();
    var mapEncryptionAlgo = new Object();

	function addCertificate(base64Certificate, readableCertificate, encryptionAlgo, tooltip) {
	    if (tooltip == null){
        	$('#certificate').append('<input type="radio" name="base64Certificate" value="'+base64Certificate+'" /> ' + readableCertificate + '<br />');
	    } else{
        	$('#certificate').append('<input type="radio" name="base64Certificate" value="'+base64Certificate+'" /> <span data-toggle="tooltip" data-placement="right" title="'+tooltip+'">' + readableCertificate + '</span><br />');
        	$('[data-toggle="tooltip"]').tooltip();
	    }
        mapEncryptionAlgo[base64Certificate] = encryptionAlgo;
	}

	function addCertificateChain(base64Certificate, chainElement) {
	    var tab = mapCertificateChain[base64Certificate];
	    if (tab == null){
	        tab = [];
	        mapCertificateChain[base64Certificate] = tab;
	    }
	    tab[tab.length] = chainElement;
	}
       

    $("#certificate").on("change", "input[type=radio]", function() {
        $("#certificateChain").empty();
        $("#encryptionAlgo").empty();
        var chain = mapCertificateChain[$(this).val()];
        var algo = mapEncryptionAlgo[$(this).val()];

        if (chain != null) {
            for (var i = 0; i < chain.length; i++) {
                $("#certificateChain").append('<input type="hidden" name="base64CertificateChain['+i+']" value="'+chain[i]+'" />');
            }
        }
        
        if (algo != null) {
            $("#encryptionAlgo").append('<input type="hidden" name="encryptionAlgorithm" value="'+algo+'" />');
        }
    });
</script>

<jsp:include page="applet-warning.jsp" />