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
    <spring:message code="labele_blgPost_post" var="labelPost"/>

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
                    <h1>${labelPost}
                        <p class="lead">${labelPostCreate}</p>
                    </h1>
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
            <div id="post" class="tab-pane active">
                <form:form autocomplete="off" commandName="blgPost" method="post"
                           action="${homeUrl}${firstname}.${lastname}/post/create" enctype="multipart/form-data">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h2 class="panel-title">${labelPostCreate}</h2>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="form-group">
                                <label for="category">${labelCategoryPost}<span style="color: red;">*</span></label>
                                <form:select cssClass="form-control" path="blgDicCategorySet" id="category" multiple="false">
                                    <c:forEach items="${blgPostCatList}" var="listCat">
                                        <form:option value="${listCat.dicCatName}"/>
                                    </c:forEach>
                                </form:select>
                                <p class="help-block">${labelCategoryPostDesc}</p>
                            </div>

                            <div class="form-group">
                                <label for="title">${labelTitlePost}<span
                                        style="color: red;">*</span></label>
                                <form:input value="TitleBlogPost" path="pstTitle" cssClass="form-control"
                                            type="text" id="title" style="width: 100%;"/>
                                <form:errors path="pstTitle"/>
                                <p class="help-block">${labelTitlePostDesc}</p>
                            </div>

                            <div class="form-group">
                                <label for="titleImageFile">${labelImagePost}<span
                                        style="color: red;">*</span></label>
                                <form:input path="file" cssClass="form-control" type="file"
                                            id="titleImageFile"  style="width: 100%;"/>
                                <form:errors path="pstTitleImage"/>

                                <p class="help-block">${labelImegePostDesc}</p>
                            </div>

                            <div class="form-group">
                                <label for="content">${labelTextPost}<span
                                        style="color: red;">*</span></label>
                                <form:textarea path="pstDocument" id="content" name="content"
                                               style="width:100%; height: 100%;"
                                               value="text"></form:textarea>
                            </div>
                            <for class="form-group">
                                <label for="listTag">${labelTagPost}<span
                                        style="color: red;">*</span></label>
                                <form:select path="blgDicTagSet" items="${tagCache}" itemLabel="dicTagName" name="dicTagName" itemValue="dicTagName" id="listTag" cssClass="form-control" multiple="true">
                                    <%--<input  type="hidden" value="${tagCache.dicTagName}"/>--%>
                                    <%--<c:forEach items="${blgPostTagList}" var="listTag">
                                        <form:option value="${listTag.dicTagId}" label="${listTag.dicTagName}"/>
                                    </c:forEach>--%>
                                    <%--<form:options items="${blgPostTagList}" itemLabel="${blgPostTagList.dicTagName}" itemValue="${blgPostTagList.dicTagId}"/>--%>
                                </form:select>
                                    <%-- <form:select path="blgDicTagSet"  id="listTag"
                                                  cssClass="form-control" multiple="true">
                                         <c:forEach items="${blgPostTagList}" var="listTag">
                                             <form:option value="${listTag.dicTagId}" label="${listTag.dicTagName}">
                                             <form:input path="blgDicTagSet" type="hidden" value="${listTag.dicTagName}"/>
                                             </form:option>
                                         </c:forEach>

                                     </form:select>--%>

                                <p class="help-block">${labelTagPostDesc}</p>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <button data-original-title="Broadcast Message" data-toggle="tooltip"
                            type="submit"
                            class="btn btn btn-primary">
                        <span class="fa fa-floppy-o"></span>
                        Save
                    </button>
                </div>
            </div>
            </form:form>
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

    $("#listTag").multiselect({
        enableFiltering: true,
        buttonWidth: '100%',
        maxHeight: 200
    });
</script>