<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Car Query</title>
</head>
<body>
<h1>Search for Cars</h1>
<form action="queryCars" method="post">
  <label for="carType">Car Type:</label>
  <input type="text" id="carType" name="carType">
  <input type="submit" value="Search">
</form>
</body>
</html>