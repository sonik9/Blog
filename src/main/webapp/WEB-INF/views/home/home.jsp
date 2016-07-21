<%--
  Created by IntelliJ IDEA.
  User: Vitalii
  Date: 22.06.2015
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ page session="false" %>
<div class="content">
    <%--<jsp:output omit-xml-declaration="yes"/>--%>
    <spring:url value="/" var="homeUrl"/>
    <spring:url value="" var="currentUrl"/>
    <c:url value="" var="hurl"/>
    <%--<c:set var="now" value="<%=new java.util.Date();%>"/>--%>
    <jsp:useBean id="now" class="java.util.Date"/>
    <fmt:formatDate value="${now}" pattern="yyyy/MM/dd" var="today"/>
    <jsp:setProperty name="now" property="time" value="${now.time - 86400000}"/>
    <fmt:formatDate value="${now}" pattern="yyyy/MM/dd" var="yesterday"/>
    <spring:message code="label_publish_by" var="labelPublishBy"/>
    <spring:message code="label_like" var="labelLike"/>
    <spring:message code="label_dislike" var="labelDislike"/>
    <spring:message code="label_comments" var="labelComments"/>
    <spring:message code="label_share" var="labelShare"/>
    <spring:message code="label_readmore" var="labelReadmore"/>
    <spring:message code="label_yesterday" var="label_yesterday"/>
    <spring:message code="label_today" var="label_today"/>
    <spring:message code="header_text" var="headerText"/>
    <spring:message code="label_home_all_post" var="allPost"/>
    <spring:message code="label_home_best_post" var="bestPost"/>
    <spring:message code="label_home_interest_post" var="interestPost"/>


    <div id="masthead" class="home">
        <div class="container">
            <div class="row">
                <div class="col-md-8">
                    <h2>${headerText}
                        <p class="lead">
                            <c:if test="${not empty currentCategory}">
                                <i class="fa fa-arrow-right" aria-hidden="true"></i> ${currentCategory}
                            </c:if>
                        </p>
                    </h2>
                    <div class="tabs">
                        <ul class="navigation">
                            <li class="active">
                                <a href="${homeUrl}" class="tab-item ">
                                    <span class="tab-item__value">${allPost}</span>
                                </a>
                            </li>
                            <li>
                                <a href="/top/" class="tab-item disabled">
                                    <span class="tab-item__value">${bestPost}</span>
                                </a>
                            </li>
                            <li>
                                <a href="/interesting/" class="tab-item disabled">
                                    <span class="tab-item__value">${interestPost}</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
                <sec:authorize access="hasRole('ADMIN')">
                    <div class="col-md-4">
                        <div class="well">
                            <a href="#">Administration</a>
                        </div>
                    </div>
                </sec:authorize>
            </div>
        </div>
    </div>

    <%-- /cont --%>

    <div class="container">
        <div class="row">
            <section class="col-md-8">
                <%--/stories--%>
                <c:if test="${not empty posts}">
                    <c:forEach var="item" items="${posts.getContent()}">
                        <c:set value="${item.blgUserSet.iterator().next().blgUserDetail.usrPhotoLink}" var="profilePhoto"/>
                        <div class="row post">
                            <ul class="pre-header-post">
                                <li class="author-photo">
                                    <c:choose>
                                        <c:when test="${profilePhoto!=null}">
                                            <c:choose>
                                                <c:when test="${fn:indexOf(profilePhoto, 'http')==0}">
                                                    <img src="${profilePhoto}" class="img-rounded photo"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${homeUrl}${profilePhoto}" class="img-rounded photo"/>
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
                                                ${item.blgUserSet.iterator().next().blgUserDetail.usrDetFirstname}
                                                ${item.blgUserSet.iterator().next().blgUserDetail.usrDetLastname}
                                        </a>
                                    </div>
                                    <div class="published-time">
                                        <fmt:formatDate value="${item.pstTimeCreate}" pattern="yyyy/MM/dd"
                                                        var="pstDate"/>
                                        <c:choose>
                                            <c:when test="${pstDate eq today}">
                                                <a href="#">${label_today} <fmt:formatDate value="${item.pstTimeCreate}"
                                                                                           pattern="HH:mm:ss"/></a>
                                            </c:when>
                                            <c:when test="${pstDate eq yesterday}">
                                                <a href="#">${label_yesterday} <fmt:formatDate
                                                        value="${item.pstTimeCreate}"
                                                        pattern="HH:mm:ss"/></a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="#"><fmt:formatDate value="${item.pstTimeCreate}"
                                                                            pattern="yyyy/MM/dd HH:mm:ss"/></a>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                </li>
                            </ul>
                            <h3>
                                <a href="${homeUrl}${pstDate}/${item.pstId}">${item.pstTitle}</a>
                            </h3>
                            <div class="row">
                                <div class="col-xs-12">
                                    <article>
                                        <c:choose>
                                            <c:when test="${fn:length(item.pstDocumentShort)>0}">
                                                <p>${item.pstDocumentShort}</p>
                                            </c:when>
                                            <c:otherwise>
                                                <p>${item.pstDocument}</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </article>
                                    <p class="lead">
                                            <%-- <c:url value="${homeUrl}${pstDate}/${item.pstId}" var="activePage">
                                                 <c:param name="id" value="${item.pstId}"/>
                                             </c:url>--%>
                                        <a href="${homeUrl}${pstDate}/${item.pstId}"
                                           class="btn btn-default">${labelReadmore}</a>
                                    </p>

                                    <p class="pull-right list-inline">
                                        <c:forEach var="tag" items="${item.blgDicTagSet}">
                                            <a href="/tag/${tag.dicTagName}">
                                                <span class="label label-default">${tag.dicTagName}</span>
                                            </a>
                                        </c:forEach>
                                    </p>

                                    <ul class="list-inline">
                                        <li>
                                            <span class="fa fa-thumbs-up"/>
                                                ${labelLike}(${item.pstCountLike})
                                        </li>
                                        <li>
                                            <span class="fa fa-thumbs-down"/>
                                                ${labelDislike}(${item.pstCountDislike})
                                        </li>
                                        <li>
                                            <a href="#">
                                                <span class="fa fa-comments"/>
                                                    ${labelComments}(${item.pstCountComm})
                                            </a>
                                        </li>
                                            <%--<li><span class="fa fa-share-alt"/>${labelShare}(34)</li>--%>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <hr/>
                    </c:forEach>
                </c:if>
                <%--/stories--%>
                <div class="row">
                    <nav>
                        <ul class="pagination pagination-control" style="margin: 0;">
                            <li>
                                <c:choose>
                                    <c:when test="${posts.getNumber()==0}">
                                        <a href="#" aria-label="Previous" class="btn disabled">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="${currentUrl}" var="activePage">
                                            <c:param name="page" value="${posts.getNumber()+1}"/>
                                            <c:param name="rows" value="5"/>
                                        </c:url>
                                        <a href="#" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                            <c:forEach var="itemPage" begin="${1}" end="${posts.getTotalPages()}">
                                <c:choose>
                                    <c:when test="${posts.getNumber()==itemPage-1}">
                                        <li class="active">
                                            <c:url value="${currentUrl}" var="activePage">
                                                <c:param name="page" value="${itemPage-1}"/>
                                                <c:param name="rows" value="5"/>
                                            </c:url>
                                            <a href="${activePage}">${itemPage}</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li>
                                            <c:url value="${currentUrl}" var="activePage">
                                                <c:param name="page" value="${itemPage-1}"/>
                                                <c:param name="rows" value="5"/>
                                            </c:url>
                                            <a href="${activePage}">${itemPage}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <li>
                                <c:choose>
                                    <c:when test="${posts.getNumber()==posts.getTotalPages()-1}">
                                        <a href="" aria-label="Next" class="btn disabled">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="${currentUrl}" var="activePage">
                                            <c:param name="page" value="${posts.getNumber()+1}"/>
                                            <c:param name="rows" value="5"/>
                                        </c:url>
                                        <a href="${activePage}" aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                        </ul>
                        <a href="${pageContext.request.contextPath}/"
                           class="btn btn-primary pull-right btnNext">More</a>
                    </nav>
                </div>
            </section>
            <%--/col-8--%>
            <aside class="col-md-4 ">
                <div class="aside-block">
                    <div class="cat-block-header">
                        Category
                    </div>
                    <hr/>
                    <ul class="cat-block-content">
                        <c:if test="${not empty categories}">
                            <c:forEach var="itemCat" items="${categories}">
                                <c:choose>
                                    <c:when test="${itemCat.dicCatName == currentCategory}">
                                        <li class="cat-item active">
                                            <a href="/category/${itemCat.dicCatName}">${itemCat.dicCatName}</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="cat-item">
                                            <a href="/category/${itemCat.dicCatName}">${itemCat.dicCatName}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:if>
                    </ul>
                </div>
                <div class="aside-block">
                    <div class="cat-block-header">
                        History
                    </div>
                    <hr/>
                    <ul class="cat-block-content">
                        <c:if test="${not empty history}">
                            <c:forEach var="itemYear" items="${history}">
                                <li class="cat-item">
                                    <a class="year-item" href="">${itemYear.key}</a>
                                    <ul class="month-list">
                                        <c:forEach var="itemMonth" items="${itemYear.value}">
                                            <li class="month-item">
                                                <a href="/${itemYear.key}/${itemMonth}">${itemMonth}</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </li>
                            </c:forEach>
                        </c:if>
                    </ul>
                </div>
            </aside>
            <%--/col-4--%>
        </div>
    </div>
    <script>
        $(document).ready(function () {
            $("div.panel-body div.col-xs-9 img").addClass("img-responsive");

        });
    </script>
</div>
