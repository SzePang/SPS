<%--
  Created by IntelliJ IDEA.
  User: Sze
  Date: 30/08/2017
  Time: 01:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Free Tables</title>
</head>
<body>


<h1>${msg}</h1>

<form action="/freeTheTable"  method="post">

    <p>Table Number to FREE : <input type="number" min="1" step="1" value="0" name= "theTable" />
    </p>



    <input type="submit" value="FREE the Table" />

</form>

<p>
    <%--Takes the user to the function of finding a table for the customer--%>
    <a href="/freeAllTables"/>FREE all Tables</a>
</p>


</body>
</html>
