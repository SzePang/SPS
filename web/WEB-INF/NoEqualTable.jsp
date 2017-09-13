<%--
  Created by IntelliJ IDEA.
  User: Sze
  Date: 10/09/2017
  Time: 18:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Unsuccessful</title>
</head>
<body>

<h1>No equal seats for the number in your party.</h1>

<h2>Search again for a larger table that will have a remainder of two seats?</h2>
<a href="/searchAgain.html/${result1} ${result2} ">Search again</a>

<p>
    <%--Takes the user to the ADD a table--%>
    <a href="/addTable.html"/>Add Tables</a>
</p>

<p>
    <%--Take the user to DELETE a table--%>
    <a href="/deleteTable.html"/>Delete Tables</a>
</p>

<%--Takes the user to the function of finding a table for the customer--%>
<p>
    <a href="/findTable.html"/>Seat a customer</a>
</p>

<p>
    <%--Takes the user to the function of finding a table for the customer--%>
    <a href="/bookATable"/>Book any table</a>
</p>

<p>
    <%--Free a table--%>
    <a href="/freeATable"/>Free tables</a>
</p>

</body>
</html>
