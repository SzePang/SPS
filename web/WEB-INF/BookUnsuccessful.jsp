<%--
  Created by IntelliJ IDEA.
  User: Sze
  Date: 17/08/2017
  Time: 19:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Booking unsuccessful</title>
</head>
<body>

<h1>The table could not be booked</h1>

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

<p>
    <%--Takes the user to the ADD a table--%>
    <a href="/addTable.html"/>Add Tables</a>
</p>

<p>
    <%--Take the user to DELETE a table--%>
    <a href="/deleteTable.html"/>Delete Tables</a>
</p>

<button><a href="/displayAllTables">Display All Tables</a></button>


</body>
</html>
