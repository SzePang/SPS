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
    <%--Takes the user to the function of finding a table for the customer--%>
    <a href="/deleteAllTables"/>delete all Tables</a>
</p>

</body>
</html>
