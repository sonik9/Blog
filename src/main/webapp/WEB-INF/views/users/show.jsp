<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--
  Created by IntelliJ IDEA.
  User: Vitalii
  Date: 19.06.2015
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<jsp:directive.page contentType="text/html; charset=UTF-8"/>
<%--<jsp:output omit-xml-declaration="yes"/>--%>
<spring:url value="/" var="rootUrl"/>
<spring:message code="label_blgUser_info" var="labelUserInfo"/>
<spring:message code="label_blgUsers_login" var="labelUsersLogin"/>
<spring:message code="label_blgUsers_pass" var="labelUsersPass"/>
<spring:message code="label_blgUsers_timeChange" var="labelUsersTimeChange"/>
<spring:message code="label_blgUsers_firstName" var="labelUsersFirstName"/>
<spring:message code="label_blgUsers_lastName" var="labelUsersLastName"/>


<c:if test="${not empty blgUser}">
    <c:set var="user" value="${blgUser}"/>
    <c:set var="firstname" value="${user.blgUserDetail.usrDetFirstname}"/>
    <c:set var="lastname" value="${user.blgUserDetail.usrDetLastname}"/>
    <div id="masthead">
        <div class="container">
            <div class="row">
                <div class="col-xs-7">
                    <h1>${user.blgUserDetail.usrDetFirstname} ${user.blgUserDetail.usrDetLastname}
                        <p class="lead">${labelUserInfo}</p>
                    </h1>
                </div>
                    <%--<form:form id="formFileUpload" method="post" action="${rootUrl}${firstname}.${lastname}/uploadphoto" enctype="multipart/form-data">--%>
                <div class="col-xs-5" style="top: 45px;">
                    <c:choose>
                        <c:when test="${user.blgUserDetail.usrPhotoLink!=null}">
                            <c:choose>
                                <c:when test="${fn:indexOf(user.blgUserDetail.usrPhotoLink, 'http')==0}">
                                    <a href="#"><img name="userpic" alt="User Pic"
                                                     src="${user.blgUserDetail.usrPhotoLink}"
                                                     style="width:200px;height:200px" class="img-rounded"></a>
                                </c:when>
                                <c:otherwise>
                                    <a href="#"><img name="userpic" alt="User Pic"
                                                     src="${rootUrl}${user.blgUserDetail.usrPhotoLink}"
                                                     style="width:200px;height:200px" class="img-rounded"></a>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <img src="//placehold.it/200" style="width:200px;height:200px" class="img-rounded"/>
                        </c:otherwise>
                    </c:choose>

                    <input type="file" name="image" id="image" style="display: none;">
                </div>
                    <%--</form:form>--%>
            </div>
        </div>
    </div>


    <%--TODO comments--%>
    <div class="container">
            <%--Message show--%>
            <%-- <c:if test="${not empty message}">
                 <div class="${message.type}" role="alert">
                     <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                             aria-hidden="true">&amp;times</span></button>
                     <strong>${message.firstWord}</strong> ${message.message}
                 </div>
             </c:if>--%>

        <div class="row">
            <div class="user-info-block">
                <ul class="navigation">
                    <li class="active">
                        <a href="#information"
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
                        <a href="${rootUrl}${firstname}.${lastname}/storage">
                            <span class="fa fa-cloud-upload fa-2x"></span>
                        </a>
                    </li>
                    <li>
                        <a href="${rootUrl}${firstname}.${lastname}/post/create"
                           style="border-top-right-radius:6px;border-bottom-right-radius:6px;">
                            <span class="fa fa-pencil-square fa-2x"/>
                        </a>
                    </li>
                </ul>
            </div>

            <div class="user-body">
                <div class="tab-content">
                    <div id="information" class="tab-pane active">
                        <form:form autocomplete="off" modelAttribute="blgUser" id="userUpdateForm" method="post"
                                   action="${rootUrl}${firstname}.${lastname}/save">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h2 class="panel-title">${labelUserInfo}</h2>
                                </div>
                                <div class="panel-body">
                                    <div class="row">

                                        <div class="col-xs-8">
                                            <table class="table table-user-information">
                                                <tbody>
                                                <tr>
                                                    <td>${labelUsersFirstName}</td>
                                                    <td>
                                                        <div class="show1"
                                                             style="display: block;">${user.blgUserDetail.usrDetFirstname}</div>
                                                        <div class="edit1" style="display: none;">
                                                            <form:input cssClass="form-control"
                                                                        path="blgUserDetail.usrDetFirstname"/>
                                                            <form:errors path="blgUserDetail.usrDetFirstname"
                                                                         cssClass="error"/>
                                                        </div>
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td>${labelUsersLastName}</td>
                                                    <td>
                                                        <div class="show1"
                                                             style="display: block;">${user.blgUserDetail.usrDetLastname}</div>
                                                        <div class="edit1" style="display: none;">
                                                            <form:input cssClass="form-control"
                                                                        path="blgUserDetail.usrDetLastname"/>
                                                            <form:errors path="blgUserDetail.usrDetLastname"
                                                                         cssClass="error"/>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Date of Birth</td>
                                                    <td>
                                                        <div class="show1"
                                                             style="display: block;">${user.usrDateTimeChange}</div>
                                                        <div class="edit1" style="display: none;">
                                                                <%--<form:input cssClass="form-control" path="blgUserDetail.usrDetFirstname"/>
                                                                <form:errors path="blgUserDetail.usrDetFirstname" cssClass="error"/>--%>
                                                        </div>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Gender</td>
                                                    <td>
                                                        <div class="show1"
                                                             style="display: block;">${user.blgUserDetail.usrGender}</div>
                                                        <div class="edit1" style="display: none;">
                                                            <form:select cssClass="form-control"
                                                                         path="blgUserDetail.usrGender"
                                                                         items="${gender}"/>
                                                            <form:errors path="blgUserDetail.usrGender"
                                                                         cssClass="error"/>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>${labelUsersLogin}</td>
                                                    <td>
                                                        <div class="show1"
                                                             style="display: block;">${user.usrLogin}</div>
                                                        <div class="edit1" style="display: none;">
                                                            <form:input autocomplete="off" cssClass="form-control"
                                                                        path="usrLogin"/>
                                                            <form:errors path="usrLogin" cssClass="error"/>
                                                        </div>
                                                    </td>
                                                </tr>

                                                <tr class="edit1" style="display: none;">
                                                    <td>${labelUsersPass}</td>
                                                    <td>
                                                        <form:password cssClass="form-control" path="usrPassword"
                                                                       showPassword="false" autocomplete="false"/>
                                                            <%--<form:input  path="usrPassword" type="password" cssStyle="display: none;"/>--%>
                                                        <form:errors path="usrPassword" cssClass="error"/>
                                                    </td>
                                                </tr>

                                                </tbody>
                                            </table>

                                        </div>
                                    </div>
                                </div>
                                <div class="panel-footer">
                                    <a data-original-title="Broadcast Message" data-toggle="tooltip" type="button"
                                       class="btn btn-sm btn-primary"><i class="glyphicon glyphicon-envelope"></i></a>
                                    <span class="pull-right">
                                            <a data-original-title="Edit this user" id="edit"
                                               data-toggle="tooltip" type="button"
                                               class="btn btn-primary btn-warning"><span
                                                    class="fa fa-pencil"></span></a>
                                            <button data-original-title="Edit this user" id="save"
                                                    style="display: none;"
                                                    data-toggle="tooltip" type="submit"
                                                    class="btn btn-primary btn-success edit1"><span
                                                    class="-floppy-o"></span>Save
                                            </button>
                                            <a data-original-title="Remove this user" data-toggle="tooltip"
                                               style="display: none;"
                                               type="button" class="btn btn-primary btn-danger edit1"><span
                                                    class="fa fa-user-times"></span></a>
                                        </span>
                                </div>
                            </div>
                        </form:form>
                    </div>


                    <div id="settings" class="tab-pane">
                        <h4>Settings</h4>
                    </div>
                    <div id="email" class="tab-pane">
                        <h4>Send Message</h4>
                    </div>
                    <div id="events" class="tab-pane">
                        <h4>Events</h4>
                    </div>
                </div>
            </div>
        </div>


    </div>
    </div>
</c:if>

<div id="modalPhotoPreview" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Photo preview</h4>
            </div>
            <div class="modal-body">
                <div id="resizable"></div>
                <img id="imagePreview" src="" class="img-responsive"/>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button id="saveImage" type="button" class="btn btn-primary" data-dismiss="modal">Save</button>
            </div>
        </div>

    </div>
</div>