<%--
  Created by IntelliJ IDEA.
  User: Sze
  Date: 12/08/2017
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Added Table Successfully</title>
</head>
<body>

<h1>${headerMessage}</h1>

<table>

    <tr>
        <td>Table Number:</td>
        <td>${table1.tableNumber}</td>
    </tr>

    <tr>
        <td>Number of Seats:</td>
        <td>${table1.seatQty}</td>
    </tr>

    <tr>
        <td>Near Wall:</td>
        <td>${table1.wall}</td>
    </tr>

    <tr>
        <td>Is Free?:</td>
        <td>${table1.free}</td>
    </tr>

    <tr>
        <td>Near Window:</td>
        <td>${table1.window}</td>
    </tr>

    <tr>
        <td>Near Walkway:</td>
        <td>${table1.walkway}</td>
    </tr>

    <tr>
        <td>Near Toilet:</td>
        <td>${table1.toilet}</td>
    </tr>

    <tr>
        <td>Near kitchen:</td>
        <td>${table1.kitchen}</td>
    </tr>

    <tr>
        <td>Near bar:</td>
        <td>${table1.bar}</td>
    </tr>

    <tr>
        <td>Near entrance:</td>
        <td>${table1.entrance}</td>
    </tr>

</table>
<p>
<%--redirects the user to the add another table--%>
<a href="/addTable.html"/>Add another table</a>
</p>

<p>
    <%--Take the user to DELETE a table--%>
    <a href="/deleteTable.html"/>Delete Tables</a>
</p>

<p>
    <%--Takes the user to the function of finding a table for the customer--%>
    <a href="/findTable.html"/>Seat a customer</a>
</p>

</body>
</html>
