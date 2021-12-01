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
        <div>
            <h3>Menu</h3>
            <table>
                <tr><td>&#10016;</td><td><a href="inventory">Inventory</a></td></tr>
                <tr><td>&#10016;</td><td><a href="admin">Admin</a></td></tr>
                <tr><td>&#10016;</td><td><a href="login?logout">Logout</a></td></tr>
            </table>
        </div>

        <div>
            <h3>Manage Users</h3>
            <table>
                <tr>
                    <th>Username</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>

                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.username}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>
                            <form method="get">
                                <input type="submit" value="Edit">
                                <input type="hidden" name="action" value="edit">
                                <input type="hidden" name="key" value="${user.username}">
                            </form>
                        </td>
                        <td>
                            <form method="post">
                                <input type="submit" value="Delete">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="key" value="${user.username}">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <c:if test="${message eq 'admins_cannot_delete_themselves'}">
                <p>Admin users cannot delete themselves</p>
            </c:if>
            <c:if test="${message eq 'added'}">
                <p>A user has been successfully added.</p>
            </c:if>  
            <c:if test="${message eq 'updated'}">
                <p>A user has been successfully updated.</p>
            </c:if>
            <c:if test="${message eq 'deleted'}">
                <p>A user has been successfully deleted.</p>
            </c:if>      
        </div>


        <c:if test="${mode eq 'add'}">
            <div>
                <h3>Add User</h3>            
                <form method="POST" action="admin">
                    <table>
                        <tr>
                            <td><label>Username: </label></td>
                            <td><input type="text" name="username" value="${user.username}"> </td>
                        </tr>
                        <tr>
                            <td><label>Password: </label></td>
                            <td><input type="password" name="password" value="${user.password}"> </td>
                        </tr>
                        <tr>
                            <td><label>Email: </label></td>
                            <td><input type="text" name="email" value="${user.email}"> </td>
                        </tr>
                        <tr>
                            <td><label>First Name: </label></td>
                            <td><input type="text" name="firstname" value="${user.firstName}"> </td>
                        </tr>
                        <tr>
                            <td><label>Last Name: </label></td>
                            <td><input type="text" name="lastname" value="${user.lastName}"> </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="submit" value="Save">
                                <input type="hidden" name="action" value="add">
                            </td>
                            <td>
                                <a href="admin" class="btn">Clear</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </c:if>

        <c:if test="${mode eq 'edit'}">
            <div>
                <h3>Edit User</h3> 
                <form method="POST" action="admin">
                    <table>
                        <tr>
                            <td><label>Username: </label></td>
                            <td><input type="text" name="username" value="${selectedUser.username}"> </td>
                        </tr>
                        <tr>
                            <td><label>Password: </label></td>
                            <td><input type="password" name="password" value="${selectedUser.password}"> </td>
                        </tr>
                        <tr>
                            <td><label>Email: </label></td>
                            <td><input type="text" name="email" value="${selectedUser.email}"> </td>
                        </tr>
                        <tr>
                            <td><label>First Name </label></td>
                            <td><input type="text" name="firstname" value="${selectedUser.firstName}"> </td>
                        </tr>
                        <tr>
                            <td><label>Last Name </label></td>
                            <td><input type="text" name="lastname" value="${selectedUser.lastName}"> </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="submit" value="Update">
                                <input type="hidden" name="action" value="update">
                            </td>
                            <td>
                                <a href="admin" class="btn">Cancel</a>
                            </td>
                        </tr>
                    </table>
                </form>                
            </div>
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
    </body>
</html>
