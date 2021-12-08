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
                <li><a href="login?logout">Logout</a></li>
            </ul>
        </div>

        <h1>Manage Categories</h1>

        <div class="inline">
            <table class="border">
                <tr class="border">
                    <th>ID</th>
                    <th>Name</th>
                    <th>Edit</th>
                </tr>
                <c:forEach items="${categoryList}" var="c">
                    <tr>
                        <td>${c.getCategoryId()}</td>
                        <td>${c.getCategoryName()}</td>
                        <td>
                            <form>
                                <input type="submit" value="EDIT">
                                <input type="hidden" name="action" value="edit">
                                <input type="hidden" name="edit_id" value="${c.getCategoryId()}">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table> 
        </div>  
        <div class="inline">
            <c:choose>
                <c:when test="${display eq 'edit_page'}">
                    <form method="POST" action="category">
                        <table class="border">
                            <tr class="border center">
                                <th colspan="2">Edit Category</th>
                            </tr>
                            <tr>
                                <td>Category ID</td>
                                <td><label>${selected_category.categoryId}</label></td>
                            </tr>
                            <tr>
                                <td>Category Name</td>
                                <td><input type="text" name="categoryName" value="${selected_category.categoryName}"></td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input type="submit" value="UPDATE">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="update_id" value="${selected_category.categoryId}">
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" class="center small">
                                    <a href="category" class="btn blue">CANCEL</a>
                                </td>
                            </tr>
                        </table>
                    </form>
                </c:when>
                <c:otherwise>
                    <form method="POST">
                        <table class="border">
                            <tr class="border">
                                <th colspan="2" class="center">Add Category</th>
                            </tr>
                            <tr>
                                <td>Category Name</td>
                                <td><input type="text" name="categoryName" value="${category.categoryName}"></td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input type="submit" value="ADD">
                                    <input type="hidden" name="action" value="add">
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" class="center small">
                                    <a href="category" class="btn blue">CANCEL</a>
                                </td>
                            </tr>
                        </table>
                    </form>
                </c:otherwise>
            </c:choose>
            <c:if test="${message eq 'empty'}">
                <p>Error. Please enter all fields.</p>
            </c:if>
            <c:if test="${message eq 'added'}">
                <p>A category has been successfully added.</p>
            </c:if>  
            <c:if test="${message eq 'updated'}">
                <p>A category has been successfully updated.</p>
            </c:if>  
        </div> 
    </body>
</html>
