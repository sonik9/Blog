<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%--
  Created by IntelliJ IDEA.
  User: Vitalii
  Date: 19.06.2015
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<jsp:directive.page contentType="text/html; charset=UTF-8"/>
<%--<jsp:output omit-xml-declaration="yes"/>--%>

<spring:message code="label_blgUser_info" var="labelUserInfo"/>
<spring:message code="label_blgUsers_login" var="labelUsersLogin"/>
<spring:message code="label_blgUsers_pass" var="labelUsersPass"/>
<spring:message code="label_blgUsers_timeChange" var="labelUsersTimeChange"/>


<div>
    <c:if test="${not empty blgUser}">
        <c:set var="user" value="${blgUser}"/>
        <div id="masthead">
            <div class="container">
                <div class="row">
                    <div class="col-md-7">
                        <h1>${user.usrLogin}
                            <p class="lead">${label_blgUser_info}</p>
                        </h1>
                    </div>
                </div>
            </div>
        </div>

        <%--TODO comments--%>
        <div class="container">
            <c:if test="${not empty message}">
                <div class="${message.type}" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&amp;times</span></button>
                    <strong>${message.firstWord}</strong> ${message.message}
                </div>
            </c:if>
            <div class="row">
                <div id="no-more-tables-vertical">

                    <table class="col-md-12 table-bordered table-striped table-condensed cf">
                        <tbody>

                            <tr>
                                <td data-title="Id" class="numeric">${user.usrId}</td>
                                <td data-title="${labelUsersLogin}">${user.usrLogin}</td>
                                <td data-title="${labelUsersPass}">${user.usrPassword}</td>
                                <td data-title="${labelUsersTimeChange}">${user.usrDateTimeChange}</td>
                            </tr>
                        </tbody>
                    </table>

                </div>
            </div>
        </div>
    </c:if>

</div>
