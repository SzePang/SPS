<%--
  Created by IntelliJ IDEA.
  User: Sze
  Date: 09/07/2017
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>SeatingPlanner</title>
  </head>
  <body>


  <h1>Welcome to Seating Planner</h1>

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
    <a href="/findTable.html"/>Seat a customer</a>
  </p>

  <p>
    <%--Frees all tables--%>
    <a href="/freeAllTables"/>Free all tables</a>
  </p>



  </body>
</html>
