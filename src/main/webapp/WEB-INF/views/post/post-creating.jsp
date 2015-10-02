<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <sec:authorize access="hasRole('ADMIN')" var="auth">
    </sec:authorize>

    <div id="masthead">
        <div class="container-fluid">
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
    <div class="container-fluid">
        <div class="col-xs-8">
            <c:if test="${not empty blgPost.pstTimeCreate}">
                <div class="modal fade preview" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">Preview</h4>
                            </div>
                            <div class="modal-body">
                                <h2>Short version</h2>
                                <hr/>
                                ${blgPost.pstDocumentShort}
                                <hr/>
                                <h2>Full version</h2>
                                ${blgPost.pstDocument}
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>

            <div class="row">
                <c:url value="${homeUrl}${firstname}.${lastname}/post/create" var="action"/>


                <form:form autocomplete="off" commandName="blgPost" method="post"
                           action="${action}" enctype="multipart/form-data">


                            <form:errors cssClass="error" path="blgDicCategorySet" cssStyle="color: red;"/>

                            <form:errors cssClass="error" path="pstTitle"/>

                            <form:errors cssClass="error" path="pstDocument"/>

                            <form:errors cssClass="error" path="blgDicTagSet"/>

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h2 class="panel-title">${labelPostCreate}</h2>
                        </div>
                        <div class="panel-body panel-correct">
                            <div class="row">
                                <div class="row">
                                    <div class="form-group col-xs-5">
                                        <label for="category">${labelCategoryPost}<span
                                                style="color: red;">*</span></label>
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
                                        <form:input data-validation="length" data-validation-length="20"
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
                                                   data-validation="length" data-validation-length="50"
                                                   style="width:100%; height: 500px;"
                                                   value="text"></form:textarea>
                                </div>
                                <div class="form-group">
                                    <label for="listTag">${labelTagPost}<span style="color: red;">*</span></label>
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


                            </div>
                        </div>

                        <div class="panel-footer">
                            <div class=" btn-group">
                                <button type="submit" class="btn btn btn-success">
                                    <span class="fa fa-floppy-o"></span>
                                        ${labelButtonSave}
                                </button>
                                <c:if test="${not empty blgPost.pstTimeCreate}">
                                    <%--${blgPost.pstTimeCreate}--%>
                                    <button type="button" class="btn btn btn-info" data-toggle="modal" data-target="#myModal">
                                        Pre View
                                    </button>
                                </c:if>
                            </div>
                            <span class="pull-right btn-group">
                                <button type="submit" class="btn btn btn-primary">
                                    <span class="fa fa-floppy-o"></span>
                                        ${labelButtonSave}/${labelButtonSendModerate}
                                </button>
                            <c:if test="${auth}">
                                <button data-toggle="tooltip" type="button" onclick="publishSend()"
                                        class="btn btn btn-success">
                                    <span class="fa fa-floppy-o"></span>
                                        ${labelButtonPublish}
                                </button>
                            </c:if>
                                </span>
                        </div>
                    </div>
                    <form:hidden path="pstId" name="pstId"/>
                </form:form>
            </div>
        </div>
        <div class="col-xs-4">
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
                <div id="collapseTwo" class="panel-collapse collapse-in" role="tabpanel"
                     aria-labelledby="headingTwo">
                    <div class="panel-body" id="filelist"></div>
                </div>
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
    <c:if test="${auth}">
    function publishSend() {
        $('form').attr("action", "${action}?public=true").submit();
    }
    </c:if>
    function moderateSend() {
        $('form').attr("action", "${action}?moderate=true").submit();
    }
    /*function preViewSend() {
        $('form').attr("action", "${action}?preview=true").submit();
    }*/
    function showPreview(){

    }


    $(document).ready(function () {

                updateFrame();
                /*function checkCutBlog(){
                 var html= $.parseHTML($('#content').val());
                 $.each(html, function(i,item){
                 if(item.nodeName==='CUTBLOG'){
                 c = confirm("No tag of cut blog!");
                 if(c===true){

                 }
                 }
                 })



                 }*/

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
            $('#filelist').children('div').removeClass('col-xs-6').css('padding-left', '0');
        });

    }


</script>