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
                    <c:if test="${user.role.getRoleId() eq 1}">
                    <li><a href="admin">Manage Users</a></li>
                    <li><a href="category">Manage Categories</a></li>
                    </c:if>
                    <c:if test="${user.role.getRoleId() != 2}">
                    <li><a href="search">Search Items</a></li>
                    <li><a href="report">Reporting</a></li>
                    </c:if>
                <li><a href="login?logout">Logout</a></li>
            </ul>
        </div>
        <h1>Your Account Information</h1>
        <div>
            <form method="POST" action="account">
                <table class="tableForm">
                    <tr>
                        <td><label>Email</label></td>
                        <td><input type="text" name="email" value="${user.email}"></td>
                    </tr>
                    <tr>
                        <td><label>Password</label></td>
                        <td><input type="password" name="password" value="${user.password}"></td>
                    </tr>
                    <tr>
                        <td><label>First Name</label></td>
                        <td><input type="text" name="firstname" value="${user.firstName}"></td>
                    </tr>
                    <tr>
                        <td><label>Last Name</label></td>
                        <td><input type="text" name="lastname" value="${user.lastName}"></td>
                    </tr>
                    <tr>
                        <td><label>Role</label></td>
                        <td><input type="text" name="role" value="${user.role.getRoleName()}" readonly="readonly"></td>
                    </tr>
                    <tr>
                        <td><label>Active</label></td>
                        <td>
                            <input type="checkbox" name="active" value="${user.active}"
                                   <c:if test="${user.active}">checked</c:if>>
                            </td>
                        </tr>
                        <tr><td colspan="2"><input type="submit" value="UPDATE"></td></tr>
                        <tr>
                            <td colspan="2" class="center small">
                                <a href="account" class="btn blue">CANCEL</a>
                            </td>
                        </tr>
                    </table>
                    <p class="white">Caution: You will be automatically logged out once deactivated</p>
                    <p class="white">Deactivated users cannot login system</p>
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
