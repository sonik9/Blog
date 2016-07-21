<%--
  Created by IntelliJ IDEA.
  User: Vitalii
  Date: 18/09/2015
  Time: 3:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div>
    <c:url value="/" var="homeUrl"/>
    <spring:message code="label_blgStorage" var="labelBlgStorage"/>
    <spring:message code="label_blgStorage_inf_b" var="labelBlgStorageInfB"/>
    <spring:message code="label_blgStorage_inf" var="labelBlgStorageInf"/>
    <spring:message code="label_blgStorage_upload_panel" var="labelBlogStorageUploadPanel"/>
    <jsp:useBean id="now" class="java.util.Date"/>
    <fmt:formatDate value="${now}" pattern="yyyy/MM/dd" var="today"/>
    <jsp:setProperty name="now" property="time" value="${now.time - 86400000}"/>
    <fmt:formatDate value="${now}" pattern="yyyy/MM/dd" var="yesterday"/>
    <spring:message code="label_publish_by" var="labelPublishBy"/>
    <spring:message code="label_like" var="labelLike"/>
    <spring:message code="label_dislike" var="labelDislike"/>
    <spring:message code="label_comments" var="labelComments"/>
    <spring:message code="label_share" var="labelShare"/>
    <spring:message code="label_yesterday" var="label_yesterday"/>
    <spring:message code="label_today" var="label_today"/>

    <spring:message code="label_postshow_userinfo_profile" var="labelUserProfile"/>
    <spring:message code="label_postshow_userinfo_publics" var="labelUserPublics"/>
    <spring:message code="label_postshow_userinfo_coments" var="labelUserComents"/>
    <spring:message code="label_postshow_userinfo_folows" var="labelUserFolows"/>

    <c:if test="${not empty post}">
        <c:set var="user" value="${post.blgUserSet.iterator().next()}"/>
        <c:set var="firstname" value="${user.blgUserDetail.usrDetFirstname}"/>
        <c:set var="lastname" value="${user.blgUserDetail.usrDetLastname}"/>
        <c:set var="profilePhoto" value="${user.blgUserDetail.usrPhotoLink}"/>

        <div id="masthead post">
            <div class="container">
                <div class="row post-show">
                    <div class="col-xs-7 ">
                        <ul class="pre-header-post">
                            <li class="author-photo">
                                <c:choose>
                                    <c:when test="${profilePhoto!=null}">
                                       <c:choose>
                                            <c:when test="${fn:indexOf(profilePhoto, 'http')==0}">
                                                <img src="${profilePhoto}" class="img-rounded"/>
                                            </c:when>
                                            <c:otherwise>
                                                <img src="${homeUrl}${profilePhoto}" class="img-rounded"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="//placehold.it/20" class="photo img-rounded"/>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                            <li>
                                <div>
                                    <a href="">
                                        <h3>${firstname}&nbsp;${lastname}
                                        </h3>
                                    </a>
                                </div>
                            </li>
                        </ul>


                        <div class="user-details">
                            <ul class="navigation">
                                <li class="active">
                                    <a href="#information"
                                       style="border-top-left-radius:6px;border-bottom-left-radius:6px;">
                                        <span class="fa fa-user"></span>
                                            ${labelUserProfile}
                                    </a>
                                </li>
                                <li>
                                    <a href="#settings">
                                        <span class="fa fa-file-text"></span>
                                            ${labelUserPublics}
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <span class="fa fa-comments"></span>
                                            ${labelUserComents}
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <span class="fa fa-eye"/>
                                            ${labelUserFolows}
                                    </a>
                                </li>
                            </ul>
                        </div>

                    </div>


                </div>
            </div>
        </div>
        <hr/>

        <div class="container">
            <div class="col-xs-8">
                <div class="row post">
                    <ul class="list-inline">
                        <li class="published-time">
                            <fmt:formatDate value="${post.pstTimeCreate}" pattern="yyyy/MM/dd" var="pstDate"/>
                            <c:choose>
                                <c:when test="${pstDate eq today}">
                                    <a href="#">${label_today} <fmt:formatDate value="${post.pstTimeCreate}"
                                                                               pattern="HH:mm:ss"/></a>
                                </c:when>
                                <c:when test="${pstDate eq yesterday}">
                                    <a href="#">${label_yesterday} <fmt:formatDate value="${post.pstTimeCreate}"
                                                                                   pattern="HH:mm:ss"/></a>
                                </c:when>
                                <c:otherwise>
                                    <a href="#"><fmt:formatDate value="${post.pstTimeCreate}"
                                                                pattern="yyyy/MM/dd HH:mm:ss"/></a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </ul>
                    <h3>${post.pstTitle}</h3>

                    <div class="row">
                        <div class="col-xs-12">
                            <p>${post.pstDocument}</p>

                            <p class="pull-right list-inline">
                                <c:forEach var="tag" items="${item.blgDicTagSet}">
                                    <a href="#"><span class="label label-default">${tag.dicTagName}</span></a>
                                </c:forEach>
                            </p>
                            <ul class="list-inline">

                                <li>
                                    <a href="#">
                                        <span class="fa fa-thumbs-up"/> ${labelLike}(${post.pstCountLike})
                                    </a>
                                </li>
                                <li>
                                    <a href="#"><span
                                            class="fa fa-thumbs-down"/> ${labelDislike}(${post.pstCountDislike})
                                    </a>
                                </li>
                                <li>
                                    <a href="#"><span class="fa fa-comments"/> ${labelComments}(${post.pstCountComm})
                                    </a>
                                </li>

                                <li id="fb-root">
                                        <%--<div id="fb-root"></div>--%>
                                    <script>
                                        (function (d, s, id) {
                                            var js, fjs = d.getElementsByTagName(s)[0];
                                            if (d.getElementById(id)) return;
                                            js = d.createElement(s);
                                            js.id = id;
                                            js.src = "//connect.facebook.net/en_US/sdk.js\#xfbml=1\&version=v2.4\&appId=777058695740737";
                                            fjs.parentNode.insertBefore(js, fjs);
                                        }(document, 'script', 'facebook-jssdk'));
                                    </script>
                                    <div class="fb-like" data-href="${post.pstUrl}" data-layout="button_count"
                                         data-action="like" data-show-faces="true" data-share="true">

                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <br/>
                </div>
                <hr/>
                <div class="row">
                    <ul class="list-inline">
                        <li>
                            <h3>
                                    ${labelComments}(${post.pstCountComm})
                            </h3>
                        </li>
                        <li class="checkbox">
                            <label>Look at:</label>
                            <label style="margin-left: 10px;"><input type="checkbox"
                                                                     value=""/>Email</label><![CDATA[&nbsp;]]>
                            <label style="margin-left: 10px;"><input type="checkbox" value=""/>Notifyter</label>
                        </li>
                    </ul>
                    <div class="">
                        <ul class="list-inline comments">
                            <li>
                                <a href="#">
                                    <c:choose>
                                        <c:when test="${user.blgUserDetail.usrPhotoLink!=null}">
                                            <c:choose>
                                                <c:when test="${fn:indexOf(user.blgUserDetail.usrPhotoLink, 'http')==0}">
                                                    <img src="${user.blgUserDetail.usrPhotoLink}"
                                                         style="width:25px;height:25px" class="img-rounded"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${homeUrl}${user.blgUserDetail.usrPhotoLink}"
                                                         style="width:25px;height:25px" class="img-rounded"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <img name="userpic" src="//placehold.it/20"
                                                 class="img-rounded"/>
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                            </li>
                            <li>
                                    ${firstname}&nbsp;${lastname}
                            </li>
                            <li class="published-time">
                                <fmt:formatDate value="${post.pstTimeCreate}" pattern="yyyy/MM/dd" var="pstDate"/>
                                <c:choose>
                                    <c:when test="${pstDate eq today}">
                                        <a href="#">${label_today} <fmt:formatDate value="${post.pstTimeCreate}"
                                                                                   pattern="HH:mm:ss"/></a>
                                    </c:when>
                                    <c:when test="${pstDate eq yesterday}">
                                        <a href="#">${label_yesterday} <fmt:formatDate value="${post.pstTimeCreate}"
                                                                                       pattern="HH:mm:ss"/></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="#"><fmt:formatDate value="${post.pstTimeCreate}"
                                                                    pattern="yyyy/MM/dd HH:mm:ss"/></a>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                        </ul>
                        <p>Coments don't work yet!</p>
                        <p>Coments don't work yet!</p>
                        <p>Coments don't work yet!</p>
                        <p>Coments don't work yet!</p>

                    </div>


                </div>
            </div>


        </div>
    </c:if>

</div>