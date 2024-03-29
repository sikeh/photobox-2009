<%@ taglib uri="/WEB-INF/tld/wall.tld" prefix="wall" %>
<wall:document><wall:xmlpidtd/>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <wall:load_capabilities/>

    <wall:head>
        <wall:title>List of Photos</wall:title>
        <wall:menu_css/>
        <c:set var="gridsize" value="1"/>
        <c:if test="${capabilities.resolution_width >= 160}">
            <c:set var="gridsize" value="2"/>
        </c:if>
        <wall:cool_menu_css colnum="${gridsize}"/>
    </wall:head>

    <wall:body>
        <wall:cool_menu colnum="${gridsize}">
            <%--varStatus="rowCounter"--%>
            <c:forEach items="${images}" var="image">
                <wall:cell>
                    <c:out value="${image.id}"/><wall:br/>
                    <c:choose>
                        <c:when test="${capabilities.resolution_width >= 160}">
                            <wall:a href="./image.do?id=${image.id}" title=""><wall:img
                                    src="./imageThumbnailContent.do?id=${image.id}" alt="Photo"/></wall:a>
                        </c:when>
                        <c:otherwise>
                            <wall:a href="./image.do?id=${image.id}" title=""><wall:img
                                    src="./imageThumbnailContent_m.do?id=${image.id}" alt="Photo"/></wall:a>
                        </c:otherwise>
                    </c:choose>
                </wall:cell>
            </c:forEach>
        </wall:cool_menu>
        <wall:hr/>
        <wall:menu colorize="true" autonumber="true">
            <wall:a href="./" title="Home">Home</wall:a>
        </wall:menu>
    </wall:body>
</wall:document>