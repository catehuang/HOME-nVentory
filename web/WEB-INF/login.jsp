<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styles.css">
        <title>Home Inventory</title>
    </head>
    <body>
        <h1>Home Inventory</h1>
        <h3>Login</h3>
        <form method="POST" action="login">
            <table>
                <tr>
                    <td><label>User name: </label></td>
                    <td><input type="text" name="username" value="${username}"></td>
                </tr>
                <tr>
                    <td><lable>Password: </lable></td>
                    <td><input type="password" name="password" value="${password}"></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Login"></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <c:if test="${message eq 'empty'}">
                            <p> Invalid login. Please enter username and password.</p>
                        </c:if>
                        <c:if test="${message eq 'invalid_login'}">
                            <p> Invalid login.</p>
                            </c:if>
                        <c:if test="${message eq 'logout'}">
                            <p>You have successfully logged out.</p>
                        </c:if>                        
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
