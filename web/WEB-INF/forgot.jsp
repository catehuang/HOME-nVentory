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
        <h1>Forgot Password</h1>

        <form method="POST" action="forgot">
            <table>
                <tr>
                    <th colspan="2" class="left">
                        Send me a link to reset password
                    </th>
                </tr>                
                <tr>
                    <td>Email Address:</td>
                    <td><input type='text' name='email' value="${email}"></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type='submit' value='Submit'>
                    </td>
                </tr>

                <tr>
                    <td colspan="2" class="center small">
                        <a href="login" class="btn blue">Back To Login</a>
                    </td>
                </tr>
            </table>
        </form>

        

    <c:if test="${message eq 'show'}">
        <p>If the address you entered is valid, you will receive an email very soon. Please check your email for your password.</p>
    </c:if>
</body>
</html>
