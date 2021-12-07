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
        <div class="nav">
            <ul>
                <li><a href="#" class="welcome">Hello ${user.firstName} ${user.lastName}</a></li>
                <li><a href="inventory">Inventory</a></li>
                <li><a href="account">Account</a></li>
                <li><a href="admin">Admin</a></li>
                <li><a href="login?logout">Logout</a></li>
            </ul>
        </div>
                
        <h1>Manage Users</h1>   
        <table class="border">
                <tr class="border">
                    <th>Email</th>
                    <th>Password</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Role</th>
                    <th>Active</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>

                <c:forEach items="${all_users}" var="stored_user">
                    <tr>
                        <td>${stored_user.email}</td>
                        <td>${stored_user.password}</td>
                        <td>${stored_user.firstName}</td>
                        <td>${stored_user.lastName}</td>
                        <td>${stored_user.role.getRoleName()}</td>
                        <td>${stored_user.active}</td>
                        <td>
                            <form method="get">
                                <input type="submit" value="Edit">
                                <input type="hidden" name="action" value="edit">
                                <input type="hidden" name="key" value="${stored_user.email}">
                            </form>
                        </td>
                        <td>
                            <form method="post">
                                <input type="submit" value="Delete">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="key" value="${stored_user.email}">
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
        
            <c:choose>
               <c:when test="${display eq 'edit_page'}">
                <h1>Edit User</h1> 
                <form method="POST" action="admin">
                    <table>
                        <tr>
                            <td><label>Email </label></td>
                            <td><input type="text" name="email" value="${selected_user.email}"> </td>
                        </tr>
                        <tr>
                            <td><label>Password </label></td>
                            <td><input type="password" name="password" value="${selected_user.password}"> </td>
                        </tr>
                        <tr>
                            <td><label>First Name </label></td>
                            <td><input type="text" name="firstname" value="${selected_user.firstName}"> </td>
                        </tr>
                        <tr>
                            <td><label>Last Name </label></td>
                            <td><input type="text" name="lastname" value="${selected_user.lastName}"> </td>
                        </tr>
                        
                        <tr>
                            <td><label>Role</label></td>
                            <td>
                                <select name="role">
                                    <c:forEach items="${roleList}" var="r">
                                        <option value="${r.getRoleId()}"
                                                <c:if test="${r.equals(selected_user.role)}">selected</c:if>
                                                >${r.getRoleName()}</option>
                                    </c:forEach>
                            </td>
                        </tr>
                        <tr>
                            <td><label>Active</label></td>
                            <td>
                            <input type="checkbox" name="active" value="${selected_user.active}"
                                   <c:if test="${selected_user.active}">checked</c:if>> 
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <input type="submit" value="Update">
                                <input type="hidden" name="action" value="update">
                            </td>
                        </tr>
                        <tr><a href="admin" class="btn">Cancel</a></tr>
                    </table>
                </form> 
               </c:when>
               
                <c:otherwise>
                <h1>Add User</h1>            
                <form method="POST" action="admin" class="tableForm">
                    <table>
                        <tr>
                            <td><label>Email</label></td>
                            <td><input type="text" name="email" value="${add_user.email}"></td>
                        </tr>

                        <tr>
                            <td><label>Password</label></td>
                            <td><input type="password" name="password" value="${add_user.password}"></td>
                        </tr>
                       
                        <tr>
                            <td><label>First Name</label></td>
                            <td><input type="text" name="firstname" value="${add_user.firstName}"></td>
                        </tr>
                        
                        <tr>
                            <td><label>Last Name </label></td>
                            <td><input type="text" name="lastname" value="${add_user.lastName}"></td>
                        </tr>
                        
                        <tr>
                            <td><label>Role</label></td>
                            <td>
                                <select name="role">
                                    <c:forEach items="${roleList}" var="r">
                                        <option value="${r.getRoleId()}"
                                                <c:if test="${r.equals(add_user.role)}">selected</c:if>
                                                >${r.getRoleName()}</option>
                                    </c:forEach>
                            </td>
                        </tr>
                        <tr>
                            <td><label>Active</label></td>
                            <td>
                            <input type="checkbox" name="active" value="${add_user.active}"
                                   <c:if test="${add_user.active}">checked</c:if>> 
                            </td>
                        </tr>
                        
                        <tr>
                            <td colspan="2">
                                <input type="submit" value="Add">
                                <input type="hidden" name="action" value="add">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <a href="admin" class="small center underline">Clear</a>
                            </td>
                        </tr>
                    </table>
                </form>
                </c:otherwise>
        </c:choose>  
        
        <c:choose>
            <c:when test="${message eq 'email_exists'}">
                <p>Error. Email exists.</p>
            </c:when>
            <c:when test="${message eq 'empty_string'}">
                <p>Error. Please fill all fields.</p>
            </c:when>
            <c:when test="${message eq 'password_20'}">
                <p>Error. The maximum length for password is 20.</p>
            </c:when>    
            <c:when test="${message eq 'email_40'}">
                <p>Error. The maximum length for email is 40.</p>
            </c:when> 
            <c:when test="${message eq 'lastname_20'}">
                <p>Error. The maximum length for lastname is 20.</p>
            </c:when> 
            <c:when test="${message eq 'firstname_20'}">
                <p>Error. The maximum length for firstname is 20.</p>
            </c:when> 
            <c:when test="${message eq 'failed_from_DB'}">
                <p>Error. Failed to add user.</p>
            </c:when> 
        </c:choose>
    </body>
</html>
