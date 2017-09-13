<%--
  Created by IntelliJ IDEA.
  User: Sze
  Date: 15/08/2017
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Table Found!</title>
</head>
<body>

<h2>Table no. ${result} is most appropriate to seat your customer</h2>


<a href="/bookTable.html/${result}">Book table ${result}</a>


<%--Takes the user to the function of finding a table for the customer--%>
<p>
    <a href="/findTable.html"/>Seat another customer</a>
</p>

<p>
    <%--Takes the user to the ADD a table--%>
    <a href="/addTable.html"/>Add Tables</a>
</p>

<p>
    <%--Take the user to DELETE a table--%>
    <a href="/deleteTable.html"/>Delete Tables</a>
</p>

<p>
    <%--Takes the user to the function of finding a table for the customer--%>
    <a href="/bookATable"/>Book any table</a>
</p>

<p>
    <%--Free a table--%>
    <a href="/freeATable"/>Free tables</a>
</p>

<button><a href="/displayAllTables">Display All Tables</a></button>


</body>
</html>
