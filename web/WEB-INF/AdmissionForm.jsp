<%--
  Created by IntelliJ IDEA.
  User: Sze
  Date: 09/08/2017
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>form</title>
</head>
<body>

<h1>form</h1>

<form action="/submitAdmissionForm.html"  method="post">
    <p>
        Student Name : <input type="text" name= "studentName" />
    </p>


    <p>
        Student Hobby : <input type="text" name="studentHobby" />
    </p>
    <input type="submit" value="Submit this form by clicking here" />
</form>

</body>
</html>
