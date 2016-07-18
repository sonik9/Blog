<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--
  Created by IntelliJ IDEA.
  User: Vitalii
  Date: 19.06.2015
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<jsp:directive.page contentType="text/html; charset=UTF-8"/>

<spring:message code="label_blgStorage" var="labelBlgStorage"/>
<spring:message code="label_blgStorage_inf_b" var="labelBlgStorageInfB"/>
<spring:message code="label_blgStorage_inf" var="labelBlgStorageInf"/>
<spring:message code="label_blgStorage_upload_panel" var="labelBlogStorageUploadPanel"/>

<spring:message code="label_blgStorage_uploaded_files" var="labelBlgStorageUploadedFiles"/>
<spring:message code="label_blgStorage_downloaded_files" var="labelBlgStorageDownloadedFiles"/>
<spring:message code="label_add_files" var="labelAddFiles"/>
<spring:message code="label_clear_all" var="labelClearAll"/>
<spring:message code="label_upload_all" var="labelUploadAll"/>
<spring:message code="label_delete_all" var="labelDeleteAll"/>

<spring:url value="/" var="rootURL"/>
<style>
    .table > tbody > tr > td {
        vertical-align: inherit;
    }
</style>
<div>
    <div id="masthead">
        <div class="container">
            <div class="row">
                <div class="col-md-7" id="accountSignUpText">
                    <!-- TODO i18n-->
                    <h1>${labelBlgStorage}
                        <p class="lead">
                        <h4>${labelBlgStorageInfB}
                            <small>${labelBlgStorageInf}</small>
                        </h4>
                        </p>
                    </h1>
                </div>

            </div>
        </div>
    </div>


    <div class="container">
        <c:if test="${not empty message}">
            <div class="${message.type}" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">&amp;times</span></button>
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
                    <li class="active">
                        <a href="#upload">
                            <span class="fa fa-cloud-upload fa-2x"></span>
                        </a>
                    </li>
                    <li>
                        <a href="${homeUrl}${firstname}.${lastname}/post/create"
                           style="border-top-right-radius:6px;border-bottom-right-radius:6px;">
                            <span class="fa fa-pencil-square fa-2x"/>
                        </a>
                    </li>
                </ul>
            </div>

            <div class="user-body">
                <div class="tab-content">
                    <div id="information" class="tab-pane active">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h2 class="panel-title">${labelBlogStorageUploadPanel}</h2>
                            </div>
                            <div class="panel-body">
                                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                                    <div class="panel panel-default">
                                        <div class="panel-heading" role="tab" id="headingOne">
                                            <h4 class="panel-title">
                                                <a role="button" data-toggle="collapse" data-parent="#accordion"
                                                   href="#collapseOne" aria-expanded="true"
                                                   aria-controls="collapseOne">
                                                    ${labelBlgStorageUploadedFiles}
                                                </a>
                                            </h4>
                                        </div>
                                        <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel"
                                             aria-labelledby="headingOne">
                                            <div class="panel-body">
                                                <table id="filesUpload" class="table table-striped">
                                                    <tbody>
                                                    No files.
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">
                                        <div class="panel-heading" role="tab" id="headingTwo">
                                            <h4 class="panel-title">
                                                <a class="collapsed" role="button" data-toggle="collapse"
                                                   data-parent="#accordion" href="#collapseTwo"
                                                   aria-expanded="false" aria-controls="collapseTwo">
                                                    ${labelBlgStorageDownloadedFiles}
                                                </a>
                                            </h4>
                                        </div>
                                        <div id="collapseTwo" class="panel-collapse" role="tabpanel"
                                             aria-labelledby="headingTwo">
                                            <div class="panel-body" id="filelist"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <div class="pull-right">
                                    <button data-original-title="Edit this user" id="deleteall"
                                            data-toggle="tooltip" type="submit"
                                            class="btn btn-primary btn-danger">
                                        <span class="fa fa-trash-o"></span>
                                        ${labelDeleteAll}
                                    </button>
                                </div>

                                <div class=" btn-group">
                                             <span class="btn btn-success fileinput-button">
                                                <span>${labelAddFiles}</span>
                                                  <input path="file" type="file" name="files[]" multiple="true"
                                                         id="fileUpload"/>
                                             </span>
                                    <button data-original-title="Edit this user" id="clearall"
                                            data-toggle="tooltip" type="submit"
                                            class="btn btn-primary btn-warning" disabled="disabled">
                                        <span class="fa fa-ban"></span>
                                        ${labelClearAll}
                                    </button>
                                    <button data-original-title="Remove this user" data-toggle="tooltip"
                                            id="uploadall" class="btn btn-primary btn-default"
                                            disabled="disabled">
                                        <span class="fa fa-upload"></span>
                                        ${labelUploadAll}
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <%--<spring:url value="${systemPath}/resources/js/views/storage.js" var="jsStorage"/>
        <script src="${jsStorage}" type="text/javascript"><jsp:text/></script>--%>

    </div>
</div>