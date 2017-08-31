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

    <p>Table Number : <input type="number" min="1" step="1" value="0" name= "tableNumber" />
    </p>


    <p>Number of seats : <input type="number" min="1" max="99" step="1" value="0" name="seatQty" />
    </p>

    <p>
        Is the table currently available to use? <input type="checkbox" name="free" />  <br>
        Rank all the properties that are near the table from 1-10 (10 being the nearest, 1 being very distant: <br>
        Wall <input type="number" min="1" max="10" step="1" value="1" name="wall" /> <br>
        Window <input type="number" min="1" max="10" step="1" value="1" name="window" /> <br>
        Toilets <input type="number" min="1" max="10" step="1" value="1" name="toilets" /> <br>
        Kitchen <input type="number" min="1" max="10" step="1" value="1" name="kitchen" /> <br>
        Walkway <input type="number" min="1" max="10" step="1" value="1" name="walkway" /> <br>
        Bar <input type="number" min="1" max="10" step="1" value="1" name="bar" /> <br>
        Entrance<input type="number" min="1" max="10" step="1" value="1" name="entrance" />
    </p>



    <input type="submit" value="Add Table" />

</form>




</body>
</html>
