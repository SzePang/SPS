<%--
  Created by IntelliJ IDEA.
  User: Sze
  Date: 15/08/2017
  Time: 13:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find Table</title>
</head>
<body>

<h1>Find a suitable table</h1>

<%--FORM to search for a table--%>
<form action="/findPriority"  method="post">

    <p>
        Number of people : <input type="number" name="numPeople" />
    </p>

    <p>Check if any of the following apply to the party:</p>

    <p>Children (Age below 8) : <input type="checkbox" name="priorities" value="child"/>

    <p>Elders (Age 70+) : <input type="checkbox" name="priorities" value="elder"/>

    <p>Pregnant : <input type="checkbox" name="priorities" value="pregnant"/>

    <p>Disabilities : <input type="checkbox" name="priorities" value="disabled"/>

    <p> <input type="submit" value="Find a Table" /> </p>

    </form>

</body>
</html>
