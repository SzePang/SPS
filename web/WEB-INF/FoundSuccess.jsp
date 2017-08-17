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


<p>
    <%--Takes the user to the function of finding a table for the customer--%>
    <a href="/findTable.html"/>Seat another customer</a>
</p>


</body>
</html>
