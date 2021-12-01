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
            <h3>Menu</h3>
            <div>
                <table class="menu">
                    <tr><td><a href="inventory">Inventory</a></td></tr>
                    <tr><td><a href="profile">Account</a></td></tr>
                    <tr><td><a href="admin">Admin</a></td></tr>
                    <tr><td><a href="login?logout">Logout</a></td></tr>
                </table>
            </div>
            <h1>Your Profile</h1>
            <form method="POST" action="profile">
                <table>
                    <tr>
                        <td>Email</td>
                        <td><input type="text" name="email" value="${user.email}"></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="password" value="${user.password}"></td>
                    </tr>
                    <tr>
                        <td>First Name</td>
                        <td><input type="text" name="firstname" value="${user.firstName}"></td>
                    </tr>
                    <tr>
                        <td>Last Name</td>
                        <td><input type="text" name="lastname" value="${user.lastName}"></td>
                    </tr>
                    <tr>
                        <td>Role</td>
                        <td>
                            <select name="role">
                                <c:forEach items="${roleList}" var="r">
                                    <option value="${r.getRoleName()}"
                                    <c:if test="${r.equals(user.role)}">selected</c:if>
                                    >${r.getRoleName()}</option>
                                </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td rowspan="2">Active</td>
                        <td>
                            <input type="checkbox" name="active"
                                   <c:if test="${user.active}">checked</c:if>> 
                        </td>
                    </tr>
                    <tr><td><input type="submit" value="Update"></td></tr>
                </table>
            </form>
        </div>

    </body>
</html>
