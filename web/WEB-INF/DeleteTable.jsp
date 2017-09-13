<%--
  Created by IntelliJ IDEA.
  User: Sze
  Date: 15/08/2017
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete Tables</title>
</head>
<body>

<h1>${msg}</h1>

<form action="/deleteTheTable"  method="post">

    <p>Table Number to DELETE : <input type="number" min="1" step="1" value="0" name= "theTable" />
    </p>



    <input type="submit" value="delete Table" />

</form>

<p>
    <%--deletes all tables when this is selected--%>
    <a href="/deleteAllTables"/>delete all Tables</a>
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
