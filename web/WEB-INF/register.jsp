<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styles.css">
        <title>HOME nVentory</title>
    </head>
    <body>
        <div class="page">
            <h1>Registration</h1>
            <form method="POST" action="register">
                <table>
                    <tr>
                        <td>Email Address</td>
                        <td><input type="text" maxlength="40" name="email" value="${user.email}"></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" maxlength="20" name="password" value="${user.password}"></td>
                    </tr>
                    <tr>
                        <td>First Name</td>
                        <td><input type="text" maxlength="20" name="firstname" value="${user.firstName}"></td>
                    </tr>
                    <tr>
                        <td>Last Name</td>
                        <td><input type="text" maxlength="20" name="lastname" value="${user.lastName}"></td>
                    </tr>
                    <tr><td colspan="2"><input type="submit" value="Register"><td></tr>
                    <tr><td colspan="2" class="small center"><a href="login" class="btn blue">Back To Login</a><td></tr>
                </table>
            </form>
            <div id="registerMessaage">
                <c:if test="${message eq 'email_40'}">
                    <p>Error. The maximum length for email is 40.</p>
                </c:if>
                <c:if test="${message eq 'email_exists'}">
                    <p>Error. Email exists.</p>
                </c:if>
                <c:if test="${message eq 'empty_string'}">
                    <p>Error. Please fill all fields.</p>
                </c:if>
                <c:if test="${message eq 'password_20'}">
                    <p>Error. The maximum length for password is 20.</p>
                </c:if>    
                <c:if test="${message eq 'lastname_20'}">
                    <p>Error. The maximum length for lastname is 20.</p>
                </c:if> 
                <c:if test="${message eq 'firstname_20'}">
                    <p>Error. The maximum length for firstname is 20.</p>
                </c:if> 
                <c:if test="${message eq 'failed_from_DB'}">
                    <p>Error. Failed to add user.</p>
                </c:if> 
            </div>
        </div>
    </body>
</html>
