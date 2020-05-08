<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="info" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="content" tagdir="/WEB-INF/tags/contents" %>
<!doctype html>
<html lang="en">

<head>
    <link rel="stylesheet" type="text/css" href="./styles/styles.css">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Registration</title>
</head>

<body>
<!-- HEAD -->
<info:header/>
<!-- BODY -->
<content:signupcontent/>
<info:links/>
<script type="text/javascript" src="../scripts/jQuerySignInController.js"></script>
<script type="text/javascript" src="../scripts/jQueryValidation.js"></script>
<script type="text/javascript" src="../scripts/jQueryRegistrationController.js"></script>
</body>

</html>