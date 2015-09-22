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
    <spring:message code="label_blgStorage_downloaded_files" var="labelBlgStorageDownloadedFiles"/>
    <spring:message code="label_save" var="labelButtonSave"/>
    <spring:message code="label_publish" var="labelButtonPublish"/>
    <spring:message code="label_send_moderate" var="labelButtonSendModerate"/>


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
        <div class="row">
            <form:form autocomplete="off" commandName="blgPost" method="post"
                       action="${homeUrl}${firstname}.${lastname}/post/create" enctype="multipart/form-data">
                <c:if test="${not empty message}">
                    <div class="${message.type}" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                aria-hidden="true"> <![CDATA[&times;]]></span></button>
                        <strong>${message.firstWord}</strong> ${message.message}
                        <p><form:errors path="blgDicCategorySet" cssStyle="color: red;"/></p>
                        <p><form:errors path="pstTitle"/></p>
                        <p><form:errors path="pstDocument"/></p>
                        <p><form:errors path="blgDicTagSet"/></p>
                    </div>
                </c:if>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h2 class="panel-title">${labelPostCreate}</h2>
                    </div>
                    <div class="panel-body panel-correct">
                        <div class="row">
                            <div class="row">
                                <div class="form-group col-xs-5">
                                    <label for="category">${labelCategoryPost}<span style="color: red;">*</span></label>
                                    <form:select cssClass="form-control chosen-select " path="blgDicCategorySet"
                                                 id="category" data-validation="selection" data-validation-count="1"
                                                 multiple="false">
                                        <form:option value=""/>
                                        <c:forEach items="${blgPostCatList}" var="listCat">
                                            <form:option value="${listCat.dicCatName}"/>
                                        </c:forEach>
                                    </form:select>

                                    <p class="help-block">${labelCategoryPostDesc}</p>
                                </div>

                                <div class="form-group col-xs-7">
                                    <label for="title">${labelTitlePost}<span
                                            style="color: red;">*</span></label>
                                    <form:input  data-validation="length" data-validation-length="20"
                                               placeholder="Title" path="pstTitle" cssClass="form-control"
                                                type="text" id="title" style="width: 100%;"/>
                                        <p class="help-block">${labelTitlePostDesc}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="titleImage">${labelImagePost}
                                    <span style="color: red;">*</span>
                                </label>
                                <form:input id="titleImage" path="pstTitleImage" cssClass="form-control" type="url"
                                            placeholder="http://"/>
                                <form:errors path="pstTitleImage"/>
                                <p class="help-block">${labelImagePostDesc}</p>
                            </div>

                            <div class="form-group has-error">
                                <label for="content">${labelTextPost}<span
                                        style="color: red;">*</span></label>
                                <form:textarea path="pstDocument" id="content" name="content"
                                               data-validation="length" data-validation-length="200"
                                               style="width:100%; height: 500px;"
                                               value="text"></form:textarea>
                            </div>
                            <div class="form-group">
                                <label for="listTag">${labelTagPost}<span
                                        style="color: red;">*</span></label>
                                <form:select path="blgDicTagSet" items="${tagCache}" itemLabel="dicTagName"
                                             data-validation="selection" data-validation-count="1"
                                             name="dicTagName" itemValue="dicTagName" id="listTag"
                                             cssClass="form-control chosen-select">
                                </form:select>

                                    <%--<input  type="hidden" value="${tagCache.dicTagName}"/>--%>
                                    <%--<c:forEach items="${blgPostTagList}" var="listTag">
                                        <form:option value="${listTag.dicTagId}" label="${listTag.dicTagName}"/>
                                    </c:forEach>--%>
                                    <%--<form:options items="${blgPostTagList}" itemLabel="${blgPostTagList.dicTagName}" itemValue="${blgPostTagList.dicTagId}"/>--%>

                                    <%-- <form:select path="blgDicTagSet"  id="listTag"
                                                  cssClass="form-control" multiple="true">
                                         <c:forEach items="${blgPostTagList}" var="listTag">
                                             <form:option value="${listTag.dicTagId}" label="${listTag.dicTagName}">
                                             <form:input path="blgDicTagSet" type="hidden" value="${listTag.dicTagName}"/>
                                             </form:option>
                                         </c:forEach>

                                     </form:select>--%>

                                <p class="help-block">${labelTagPostDes}</p>
                            </div>
                            <div class="panel panel-info">
                                <div class="panel-heading" role="tab" id="headingTwo">
                                    <h4 class="panel-title">
                                        <a class="collapsed" role="button" data-toggle="collapse"
                                           data-parent="#accordion" href="#collapseTwo"
                                           aria-expanded="false" aria-controls="collapseTwo">
                                                ${labelBlgStorageDownloadedFiles}
                                        </a>
                                    </h4>
                                </div>
                                <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel"
                                     aria-labelledby="headingTwo">
                                    <div class="panel-body" id="filelist"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="panel-footer">
                        <button data-original-title="Broadcast Message" data-toggle="tooltip"
                                type="submit"
                                class="btn btn btn-primary">
                            <span class="fa fa-floppy-o"></span>
                            ${labelButtonSendModerate}
                        </button>
                        <button data-original-title="Broadcast Message" data-toggle="tooltip"
                                type="submit"
                                class="btn btn btn-success">
                            <span class="fa fa-floppy-o"></span>
                                ${labelButtonPublish}
                        </button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>

</div>
<spring:url value="${homeUrl}resources/js/tinymce/tinymce.min.js" var="jsEditor"/>
<script src="//tinymce.cachefly.net/4.2/tinymce.min.js" type="text/javascript">
    <jsp:text/>
</script>

<spring:url value="${homeUrl}resources/js/bootstrap/bootstrap-multiselect.js" var="jsTag"/>
<script src="${jsTag}" type="text/javascript">
    <jsp:text/>
</script>

<spring:url value="${homeUrl}resources/js/jquery/chosen.jquery.min.js" var="jsChosen"/>
<script src="${jsChosen}" type="text/javascript">
    <jsp:text/>
</script>

<script>
    $(document).ready(function () {
                updateFrame();

                //$.validattion();
                tinymce.init({
                    convert_urls: false,
                            remove_script_host: false,
                    extended_valid_elements: "cutblog",
                    custom_elements: "cutblog",
                    selector: "textarea#content",
                    theme: "modern",
                    plugins: [
                        "advlist autolink lists link image charmap print preview anchor",
                        "searchreplace visualblocks code fullscreen",
                        "insertdatetime media table contextmenu paste"
                    ],
                    toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | cutTag",
                    setup: function (ed) {
                        // Register example button
                        ed.addButton('cutTag', {
                            title: 'cut init',
                            text: 'Cut blog',
                            image: '${homeUrl}resources/images/favicon.gif',
                            onclick: function () {
                                //ed.windowManager.alert('Hello world!! Selection: ' + ed.selection.getContent({format : 'text'}));
                                ed.insertContent("<cutblog>.....</cutblog>");
                            }
                        });
                    },

                });





            }
    );
    var config = {
        '.chosen-select': {width: "100%"},
        '.chosen-select-deselect': {allow_single_deselect: true},
        '.chosen-select-no-single': {disable_search_threshold: 3},
        '.chosen-select-no-results': {no_results_text: 'Oops, nothing found!'},
        '.chosen-select-width': {width: "100%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
    function updateFrame() {
        $("#filelist").load("${homeUrl}${firstname}.${lastname}/storage/download", function () {
            if ($("#filelist").find('div').length === 0) { //is(":empty")){
                $('#deleteall').prop("disabled", true);
            } else
                $('#deleteall').prop("disabled", false)
        });
    }


</script>