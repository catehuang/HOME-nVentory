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

        <h1>HOME nVentory</h1>
        <h2>Login</h2>

        <form method="POST" action="login" class="loginForm">
            <table>
                <tr>
                    <td><label>Email</label></td>
                    <td><input type="text" name="email" value="${email}"></td>
                </tr>
                <tr>
                    <td><label>Password</label></td>
                    <td><input type="password" name="password" value="${password}"></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Login"></td>
                </tr>
                <tr>
                    <td class="small nopadding" ><lable><a href="register" class="underline">Create an account</a></lable></td>
                    <td class="right small nopadding"><lable><a href="#" class="underline">Forgot password?</a></lable></td>
                </tr>

                <c:if test="${message eq 'registered'}">
                    <tr><td colspan="2"><p>Registered successfully.</p></td></tr>
                </c:if>
                <c:if test="${message eq 'empty'}">
                    <tr><td colspan="2"><p>Invalid login. Please enter username and password.</p></td></tr>
                </c:if>
                <c:if test="${message eq 'invalid_login'}">
                    <tr><td colspan="2"><p>Invalid login.</p></td></tr>
                </c:if>
                <c:if test="${message eq 'logout'}">
                    <tr><td colspan="2"><p>You have successfully logged out.</p></td></tr>
                </c:if> 
                <c:if test="${message eq 'deactivated_account'}">
                    <tr><td colspan="2"><p>This account is deactivated. Please contact system administrator to reactivate account.</p></td></tr>
                </c:if> 
            </table>
        </form>     


    </body>
</html>
