<%---
Created by IntelliJ IDEA.
User: Vitalii
Date: 22.06.2015
Time: 10:24
To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<div id="header" class="navbar navbar-default navbar-fixed-top">


    <spring:message code="header_text" var="headerText"/>
    <spring:message code="label_en_US" var="labelEnUs"/>
    <spring:message code="label_ru_RU" var="labelRuRu"/>
    <spring:message code="label_blgStorage" var="labelBlgStorage"/>

    <sec:authorize access="isAuthenticated()">
        <sec:authentication var="principal" property="principal"/>
        <%---<sec:authentication property="firstname" var="firstname"/>
        <sec:authentication property="lastname" var="lastname"/>--%>
        <c:set var="firstname" value="${principal.getBlgUserDetail().getUsrDetFirstname()}"/>
        <c:set var="lastname" value="${principal.getBlgUserDetail().getUsrDetLastname()}"/>

    </sec:authorize>
    <spring:url value="/" var="rootUrl"/>
    <spring:url value="/sign" var="showRegUrl"/>
    <input type="hidden" value="${rootUrl}${firstname}.${lastname}" id="homePath"/>

    <div class="container ">

        <div class="navbar-header">
            <button class="navbar-toggle collapsed" type="button" data-toggle="collapse"
                    data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>

        <a href="${rootUrl}" class="navbar-brand">Home</a>
        <nav class="collapse navbar-collapse" role="navigation">
            <ul class="nav navbar-nav">
                <%--<li>
                    <a href="#">Category</a>
                </li>--%>
                <li>
                    <a href="#" onClick="notify(message)">message test</a>
                </li>
            </ul>
            <ul class="nav navbar-right navbar-nav">
                <sec:authorize access="isAuthenticated()">
                    <li>
                        <a href="${rootUrl}${firstname}.${lastname}/storage">
                                ${labelBlgStorage}&nbsp;
                            <span class="fa fa-cloud-upload"></span>
                        </a>
                    </li>
                    <li>
                        <a href="${rootUrl}${firstname}.${lastname}/post/create">Post create <span
                                class="fa fa-pencil"/></a>
                    </li>
                </sec:authorize>
                <li>
                    <a href="${usersUrl}?lang=en_US">${labelEnUs}</a>
                </li>
                <li>
                    <a href="${usersUrl}?lang=ru_RU">${labelRuRu}</a>
                </li>
                <sec:authorize access="isAnonymous()">
                    <li>

                        <a id="accountSignInText" href="${showRegUrl}"
                           onClick="$('#signinbox, #accountSignInText').hide(); $('#signupbox, #accountSignUpText').show()">Sign
                            In</a>
                        <a style="display: none" id="accountSignUpText" href="#"
                           onClick="$('#signinbox, #accountSignInText').hide(); $('#signupbox, #accountSignUpText').show()">Sign
                            Up</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li class="dropdown user-menu">
                        <a class="dropdown-toggle" data-toggle="dropdown" id="user"
                           style="padding-bottom: 0; padding-top: 10px;"
                           href="">
                            <c:choose>
                                <c:when test="${principal.getBlgUserDetail().getUsrPhotoLink()!=null}">
                                    <c:choose>
                                    <c:when test="${fn:indexOf(principal.getBlgUserDetail().getUsrPhotoLink(), 'http')==0}">
                                        <img src="${principal.getBlgUserDetail().getUsrPhotoLink()}"
                                             style="width:30px;height:30px" class="img-rounded"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${rootUrl}${principal.getBlgUserDetail().getUsrPhotoLink()}"
                                             style="width:30px;height:30px" class="img-rounded"/>
                                    </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <img src="//placehold.it/30" style="width:30px;height:30px" class="img-rounded"/>
                                </c:otherwise>
                            </c:choose>
                            &nbsp; ${principal.getBlgUserDetail().getUsrDetFirstname()}
                            <span class="caret"/>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                                <%--- TODO i18n--%>
                            <li><a id="profileEdit" href="${rootUrl}${firstname}.${lastname}">Show profile</a></li>
                            <li class="divider"></li>
                            <li><a id="signOut" href="${rootUrl}j_spring_security_logout">Sign Out</a></li>
                        </ul>
                    </li>
                </sec:authorize>
            </ul>

            <%----%>
        </nav>
    </div>
</div>
