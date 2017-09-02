<%--
  Created by IntelliJ IDEA.
  User: Sze
  Date: 15/08/2017
  Time: 13:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find Table</title>
</head>
<body>

<h1>Find a suitable table</h1>

<%--FORM to search for a table--%>
<form action="/tableLookUp.html"  method="post">

    <p>
        Number of people : <input type="number" name="numPeople" />
    </p>

 <%--   <p>
        Any children (Age below 8) : <input type="checkbox" name="children" />
    </p>

    <p>
        Any customers with disabilities? : <input type="checkbox" name="disabled" />
    </p>

    <p>
        Any elders (Age 70+) : <input type="checkbox" name="elders" />

        <input type="submit" value="Find a Table" />--%>

    </form>



    <input type="submit" value="Find a Table" />


</form>

</body>
</html>
