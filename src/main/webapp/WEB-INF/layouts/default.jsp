<%--
Created by IntelliJ IDEA.
User: Vitalii
Date: 22.06.2015
Time: 10:37
To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="tilesx" uri="http://tiles.apache.org/tags-tiles-extras" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false"%>
<html>
<%--
<jsp:output doctype-root-element="HTML" doctype-system="about:legacy-compact"/>
<jsp:directive.page contentType="text/html; charset=UTF-8"/>
<jsp:directive.page pageEncoding="UTF-8"/>
--%>
<head>
    <c:url value='/' var="rootUrl"/>
    <spring:url value="${pageContext.request.contextPath}" var="systemPath"/>
    <spring:message code="application_name" var="app_name" htmlEscape="false"/>
    <spring:message code="welcome_h3" var="welcome" arguments=""/>
    <meta http-equiv="content-type" content="text/html" charset="UTF-8"/>
    <meta property="fb:app_id" content="777058695740737"/>
    <c:if test="${not empty post}">
        <title>${app_name}/${post.pstTitle}</title>
        <meta property="og:type" content="article"/>
        <meta property="og:url" content="${post.pstUrl}"/>
        <meta property="og:title" content="${post.pstTitle}"/>
        <meta property="og:description" content="${fbContent}"/>
        <c:choose>
            <c:when test="${fn:length(fbImages)>0}">
                <c:forEach items="${fbImages}" var="fbImage">
                    <meta property="og:image" content="${fbImage}"/>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <meta property="og:image" content="${rootUrl}resources/images/BS3.png"/>
            </c:otherwise>
        </c:choose>

    </c:if>

    <c:if test="${empty post}">
        <title>${welcome}&nbsp;${app_name}</title>
        <meta property="og:type" content="website"/>
        <meta property="og:url" content="${rootUrl}"/>
        <meta property="og:title" content="${welcome} ${app_name}"/>
        <meta property="og:description" content="Blog storage system"/>
    </c:if>


    <!--CSS STYLE-->
    <!--FAVICON-->
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/images/favicon1.png"/>
    <%--Load resources using spring--%>
    <%--<spring:theme code="styleSheetApp" var="app_css"/>
    <spring:url value="${rootUrl}${app_css}" var="app_css_url"/>
    <link rel="stylesheet" type="text/css" media="screen" href="${app_css_url}"/>--%>

    <tilesx:useAttribute name="styles" classname="java.util.List" id="styles" />
    <c:forEach items="${styles}" var="s">
        <link rel="stylesheet"
              href="${rootUrl}/resources${s}"/>
    </c:forEach>

    <!-- JavaScripts-->
    <tilesx:useAttribute name="js" classname="java.util.List" id="js"/>
    <c:forEach items="${js}" var="j">
        <script src="${j}"><jsp:text/></script>
    </c:forEach>


    <%-- <spring:url value="${rootUrl}resources/js/nprogress/nprogress.js" var="jsNprogress"/>
     <script src="${jsNprogress}" type="text/javascript"><jsp:text/></script>-->

    <!--<spring:url value="${rootUrl}resources/js/main.js" var="jsMain"/>
    <script src="${jsMain}" type="text/javascript"><jsp:text/></script>-->
    <!-- Notify-->
    <!--<spring:url value="${rootUrl}resources/js/bootstrap/bootstrap-notify.js" var="jsNotify"/>
    <script src="${jsNotify}" type="text/javascript"><jsp:text/></script>--%>


    <c:set var="userLocale">
        <c:set var="plocale">${pageContext.response.locale}</c:set>
        <c:out value="${fn:replace(plocale, '_','-' )}" default="en"/>
    </c:set>

</head>

<body>
<div class="notifications top-right"> &nbsp;</div>

<tiles:insertAttribute name="header" ignore="true"/>
<!--Wrapper--><!--Body Wrapper-->
<tiles:insertAttribute name="body" ignore="true"/>

<tiles:insertAttribute name="footer" ignore="true"/>

<%-- <div class="${message.type}" role="alert">
<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
aria-hidden="true">&amp;times</span></button>
<strong>${message.firstWord}</strong> ${message.message}
</div>--%>

<c:if test="${not empty message}">
    <script>
        message = "<strong>${message.firstWord}</strong>${message.message}";
        if (message.length > 20)
            notify(message, '${message.type}', 'alert-blackgloss');
        /*er=$('.error').toArray();
         for(var i=0;er.length;i++){
         notify(er[i],'','alert-blackgloss');
         }*/
    </script>
</c:if>

</body>


</html>
