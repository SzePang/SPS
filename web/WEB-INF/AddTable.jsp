<%--
  Created by IntelliJ IDEA.
  User: Sze
  Date: 12/08/2017
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Tables</title>
</head>
<body>

<h1>Add a table to the system</h1>

<%--TODO check that the user is not attempting to add a table that already exits--%>

<form action="/inputTable.html"  method="post">

    <p>Table Number : <input type="number" name= "tableNumber" />
    </p>


    <p>Number of seats : <input type="number" name="seatQty" />
    </p>

    <p>
        Is the table currently available to use? <input type="checkbox" name="free" />  <br>
        Check all the properties that are near the table: <br>
        Wall <input type="checkbox" name="wall" /> <br>
        Window <input type="checkbox" name="window" /> <br>
        Toilets <input type="checkbox" name="toilets" /> <br>
        Kitchen <input type="checkbox" name="kitchen" /> <br>
        Walkway <input type="checkbox" name="walkway" /> <br>
        Bar <input type="checkbox" name="bar" /> <br>
        Entrance<input type="checkbox" name="entrance" />
    </p>



    <input type="submit" value="Add Table" />

</form>




</body>
</html>
