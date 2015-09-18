<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Vitalii
  Date: 19.06.2015
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
    <%--<c:forEach items="${files}" var="file">
        <c:forEach items="${file.value}" var="meta">
            <c:set var="path" value="${meta.key}"/>
            <c:set var="size" value="${meta.value}"/>
        </c:forEach>
    </c:forEach>--%>

<c:forEach items="${files}" var="file">
        <div class="col-xs-6" style="padding-right: 0; padding-left: 5px;">
            <div class="files">
                <div class="col-xs-5 thmb">
                    <a href="${file.path}" class="readingglass" target="_blank"></a>
                    <img id="${file.fileName}" src="${file.path}" class="img-responsive img-rounded"/>
                </div>
                <div class="info col-xs-7">
                    <a href="#" onclick="deleteFileClick(this);" id="fileDelete" name="${file.fileName}" class="file-delete" title="Delete">&#10060;</a>

                    <div class="filename">${file.fileName}</div>
                    <div class="meta" name="${file.fileName}">
                        <span class="file_size"><script>$("div[name='${file.fileName}']").find('.file_size').append(sizeH(${file.size}))</script></span>
                        <span class="image_size">
                            <script>
                                var img = document.createElement('img');
                                img.src = '${file.path}';
                                img.onload = function (e) {$("div[name='${file.fileName}']").find('.image_size').append(img.width+"x"+img.height);}
                            </script>
                        </span>
                    </div>
                    <div class="copypasta">
                        <input type="text" class="html_code"
                               value="&#8249;img src=&#171;${file.path}&#187;/&#8250;"/>
                        <input type="text" class="url"
                               value="${file.path}"/>
                    </div>
                </div>
            </div>
        </div>
</c:forEach>

