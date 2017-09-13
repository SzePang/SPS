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

<button><a href="/displayAllTables">Display All Tables</a></button>

</body>
</body>
</html>
