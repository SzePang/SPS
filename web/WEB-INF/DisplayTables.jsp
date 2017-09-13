<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Sze
  Date: 28/08/2017
  Time: 00:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Tables</title>

</head>
<body>
<h1>${result1}</h1>

<ul>
<%
    List<String> list = null;
    list = (List<String>)request.getAttribute("table1");

    for(String s : list){
%>

    <li><%=s %></li>

<%

}

%>
</ul>

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
</body>
</html>
