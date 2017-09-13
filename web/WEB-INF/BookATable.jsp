<%--
  Created by IntelliJ IDEA.
  User: Sze
  Date: 12/09/2017
  Time: 01:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book A Table</title>
</head>
<body>

<%--FORM to book a table--%>
<form action="/bookAnyTable/" method="post">
    <h1>Book any free table</h1>
    <p>
        Book table number : <input type="number" min="1" step="1" value="0" name="tableNumber" />
    </p>

    <p> <input type="submit" value="Book Table" /> </p>

</form>



    <%--Takes the user to the function of finding a table for the customer--%>
    <p>
        <a href="/findTable.html"/>Seat a customer</a>
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
