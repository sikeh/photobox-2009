<%@ taglib uri="/WEB-INF/tld/wall.tld" prefix="wall" %>
<wall:document><wall:xmlpidtd/>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <wall:load_capabilities/>

    <wall:head>
        <wall:title>WALL Demos</wall:title>
    </wall:head>
    <wall:body>
        <%--varStatus="rowCounter"--%>
        <div class="photo">
            <p>
                <c:out value="${image.id}"/><wall:br/>
                <c:choose>
                    <c:when test="${capabilities.resolution_width >= 160}">
                        <wall:img src="./imageContent.do?id=${image.id}" alt="Photo"/>
                    </c:when>
                    <c:otherwise>
                        <wall:img src="./imageContent_m.do?id=${image.id}" alt="Photo"/>
                    </c:otherwise>
                </c:choose>
                <wall:br/>
                <c:out value="${image.description}"/><wall:br/>
                <c:out value="${image.timestamp}"/>
            </p>
        </div>


        </body>
    </wall:body>
</wall:document>