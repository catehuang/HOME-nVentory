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
            <h1>HOME nVentory</h1>
            <h3>Login</h3>
            <form method="POST" action="login">
                <table>
                    <tr>
                        <td><label>Email: </label></td>
                        <td><input type="text" name="email" value="${email}"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><lable>Password: </lable></td>
                    <td><input type="password" name="password" value="${password}"></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Login"><span class="registerLink"><a href="register">Register</a></span></td>
                    </tr>    
                </table>
            </form>     
            <div id="loginMessaage">
                <c:if test="${message eq 'registered'}">
                    <p> Registered successfully. </p>
                </c:if>
                <c:if test="${message eq 'empty'}">
                    <p> Invalid login. Please enter username and password.</p>
                </c:if>
                <c:if test="${message eq 'invalid_login'}">
                    <p> Invalid login.</p>
                </c:if>
                <c:if test="${message eq 'logout'}">
                    <p>You have successfully logged out.</p>
                </c:if> 
                <c:if test="${message eq 'deactivated_account'}">
                    <p>This account is deactivated. Please contact administrators to reactivate account.</p>
                </c:if> 
            </div>
        </div>
    </body>
</html>
