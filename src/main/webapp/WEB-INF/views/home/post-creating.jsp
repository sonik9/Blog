<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!--
Created by IntelliJ IDEA.
User: Vitalii
Date: 28/08/2015
Time: 9:57 AM
To change this template use File | Settings | File Templates.
-->
<div>

    <jsp:directive.page contentType="text/html; charset=UTF-8"/>
    <%--<!--<jsp:output omit-xml-declaration="yes"/>-->--%>
    <spring:url value="/" var="homeUrl"/>
    <spring:message code="label_blgUser_info" var="labelUserInfo"/>
    <spring:message code="label_blgUsers_login" var="labelUsersLogin"/>
    <spring:message code="label_blgUsers_pass" var="labelUsersPass"/>
    <spring:message code="label_blgUsers_timeChange" var="labelUsersTimeChange"/>
    <spring:message code="label_blgUsers_firstName" var="labelUsersFirstName"/>
    <spring:message code="label_blgUsers_lastName" var="labelUsersLastName"/>
    <spring:message code="labele_blgPost_postCreate" var="labelPostCreate"/>

    <spring:message code="labele_blgPost_postCategory" var="labelCategoryPost"/>
    <spring:message code="labele_blgPost_postCategoryDesc" var="labelCategoryPostDesc"/>
    <spring:message code="labele_blgPost_postTitle" var="labelTitlePost"/>
    <spring:message code="labele_blgPost_postTitleDesc" var="labelTitlePostDesc"/>
    <spring:message code="labele_blgPost_postImage" var="labelImagePost"/>
    <spring:message code="labele_blgPost_postImageDesc" var="labelImagePostDesc"/>
    <spring:message code="labele_blgPost_postText" var="labelTextPost"/>
    <spring:message code="labele_blgPost_postTextDesc" var="labelTextPostDes"/>
    <spring:message code="labele_blgPost_postTag" var="labelTagPost"/>
    <spring:message code="labele_blgPost_postTagDesc" var="labelTagPostDes"/>


    <sec:authorize access="isAuthenticated()">
        <sec:authentication var="principal" property="principal"/>
        <%--<sec:authentication property="firstname" var="firstname"/>
        <sec:authentication property="lastname" var="lastname"/>--%>
        <c:set var="firstname" value="${principal.getBlgUserDetail().getUsrDetFirstname()}"/>
        <c:set var="lastname" value="${principal.getBlgUserDetail().getUsrDetLastname()}"/>
    </sec:authorize>

    <div id="masthead">
        <div class="container">
            <div class="row">
                <div class="col-xs-7">
                    <h1>${firstname} <![CDATA[&nbsp;]]> ${lastname}
                        <p class="lead">${labelPostCreate}</p>
                    </h1>
                </div>
                <div class="col-xs-5" style="top: 45px;">
                    <c:choose>
                        <c:when test="${principal.getBlgUserDetail().getUsrPhotoLink()!=null}">
                            <a href="#">
                                <img name="userpic" alt="User Pic"
                                     src="${principal.getBlgUserDetail().getUsrPhotoLink()}"
                                     style="width:200px;height:200px" class="img-rounded"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="#"> <img name="userpic" src="//placehold.it/200"
                                              style="width:200px;height:200px"
                                              class="img-rounded"/></a>
                        </c:otherwise>
                    </c:choose>
                    <input type="file" name="image" id="image" style="display: none;"/>
                </div>
            </div>
        </div>
    </div>


    <!--TODO comments -->
    <div class="container">

        <c:if test="${not empty message}">
            <div class="${message.type}" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true"> <![CDATA[&amp;]]>times</span></button>
                <strong>${message.firstWord}</strong> ${message.message}
            </div>
        </c:if>

        <div class="row">
            <div class="user-info-block">
                <ul class="navigation">
                    <li>
                        <a href="${homeUrl}${firstname}.${lastname}"
                           style="border-top-left-radius:6px;border-bottom-left-radius:6px;">
                            <span class="fa fa-user fa-2x"></span>
                        </a>
                    </li>
                    <li>
                        <a href="#settings">
                            <span class="fa fa-cog fa-2x"></span>
                        </a>
                    </li>
                    <li>
                        <a href="#email">
                            <span class="fa fa-envelope fa-2x"></span>
                        </a>
                    </li>
                    <li class="active">
                        <a href="#events"
                           style="border-top-right-radius:6px;border-bottom-right-radius:6px;">
                            <span class="fa fa-pencil-square fa-2x"></span>
                        </a>
                    </li>
                </ul>
            </div>

            <div class="user-body">
                <div class="tab-content">
                    <div id="post" class="tab-pane active">
                        <form:form autocomplete="off" commandName="blgPost" method="post"
                                   action="">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h2 class="panel-title">${labelPostCreate}</h2>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="form-group">
                                            <label for="category">${labelCategoryPost}</label>
                                                <form:select cssClass="form-control" path="blgPostCategories.pstCatName"
                                                             items="${blgPostCatList}" id="category"/>
                                            <p class="help-block">${labelCategoryPostDesc}</p>
                                        </div>

                                        <div class="form-group">
                                            <label for="title">${labelTitlePost}</label>
                                            <input class="form-control" type="text" id="title" style="width: 100%;"/>

                                            <p class="help-block">${labelTitlePostDesc}</p>
                                        </div>

                                        <div class="form-group">
                                            <label for="file">${labelImagePost}</label>
                                            <input class="form-control" type="file" id="file" style="width: 100%;"/>

                                            <p class="help-block">${labelImegePostDesc}</p>
                                        </div>

                                        <div class="form-group">
                                            <label for="content">${labelTextPost}</label>
                                            <textarea id="content" name="content" style="width:100%; height: 100%;"
                                                      placeholder="Describe yourself here...">
                                            &nbsp;
                                            </textarea>
                                        </div>
                                        <div class="form-group">
                                            <label for="tag">${labelTitlePost}</label>
                                            <input class="form-control" type="tag" id="tag" style="width: 100%;"/>

                                            <p class="help-block">${labelTagPostDesc}</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="panel-footer">
                                    <a data-original-title="Broadcast Message" data-toggle="tooltip"
                                       type="button"
                                       class="btn btn-sm btn-primary">
                                        <span class="glyphicon glyphicon-envelope"></span>
                                    </a>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<script>
    tinymce.init({
        selector: "textarea#content",
        theme: "modern",
        plugins: [
            "advlist autolink lists link image charmap print preview anchor",
            "searchreplace visualblocks code fullscreen",
            "insertdatetime media table contextmenu paste"
        ],
        toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image"
    });
</script>