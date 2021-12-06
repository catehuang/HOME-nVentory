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
        <div class="nav">
            <ul>
                <li><a href="#" class="welcome">Hello ${user.firstName} ${user.lastName}</a></li>
                <li><a href="inventory">Inventory</a></li>
                <li><a href="account">Account</a></li>
                <li><a href="admin">Admin</a></li>
                <li><a href="login?logout">Logout</a></li>
            </ul>
        </div>
        <h1>Your Account Information</h1>
        <div>
            <form method="POST" action="account">
                <table class="tableForm">
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
                                    <option value="${r.getRoleId()}"
                                            <c:if test="${r.equals(user.role)}">selected</c:if>
                                            >${r.getRoleName()}</option>
                                </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td rowspan="2">Active</td>
                        <td>
                            <input type="checkbox" name="active" value="${user.active}"
                                   <c:if test="${user.active}">checked</c:if>> 
                            </td>
                        </tr>
                        <tr><td><input type="submit" value="Update"></td></tr>
                    </table>
                </form>
            <c:if test="${message eq 'updated'}">
                <p>Account information has been successfully updated.</p>
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
            <c:if test="${message eq 'email_40'}">
                <p>Error. The maximum length for email is 40.</p>
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
    </body>
</html>
