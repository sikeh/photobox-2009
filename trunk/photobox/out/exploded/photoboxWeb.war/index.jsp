<%@ taglib uri="/WEB-INF/tld/wall.tld" prefix="wall" %>
<wall:document><wall:xmlpidtd/>
    <wall:head>
        <wall:title>Welcome to PhotoBox</wall:title>
        <wall:menu_css/>
    </wall:head>

    <wall:body>
        <wall:block>
            <wall:img src="./photobox.jpg" alt="Logo"/><wall:br/>
            <wall:b>Welcome to PhotoBox</wall:b>
        </wall:block>
        <wall:menu colorize="true" autonumber="true">
            <wall:a href="./imageList.do" title="Photos">List of Photos</wall:a>
        </wall:menu>
        <wall:form action="./image.do" method="get" enable_wml="true">
            Photo ID:
            <wall:input type="text" name="id" size="8" maxlength="4" value="" format="4N"/>
            <wall:input type="submit" value="Go"/>
        </wall:form>
    </wall:body>
</wall:document>