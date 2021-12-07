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

        <h1>Home Inventory</h1>

        <table class="tableItem">
            <tr>
                <th>Category</th>
                <th>Name</th>
                <th>Price</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            <c:forEach items="${items}" var="item">
                <tr>
                    <td>${item.category.getCategoryName()}</th>
                    <td>${item.itemName}</th>
                    <td>&#36;${item.price}</th>
                    <td>
                        <form method="get">
                            <input type="submit" value="Edit">
                            <input type="hidden" name="action" value="retrieve">
                            <input type="hidden" name="itemID" value="${item.itemId}">
                        </form>
                    </td>
                    <td>
                        <form method="post">
                            <input type="submit" value="Delete">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="itemID" value="${item.itemId}">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${message eq 'deleted'}">
            <p>Successfully deleted item from inventory.</p>
        </c:if>   
        <c:if test="${message eq 'not_belong_to_you'}">
            <p>Cannot delete an item which does not belong to you.</p>
        </c:if>
        <c:if test="${message eq 'added'}">
            <p>An item has been successfully added.</p>
        </c:if>  
        <c:if test="${message eq 'updated'}">
            <p>An item has been successfully updated.</p>
        </c:if>  

        <c:choose>
            <c:when test="${display eq 'edit_page'}">
                <form method="POST" action="inventory">
                    <h2>Edit Item</h2>
                    <table class="tableForm">
                        <tr>
                            <td><label>Category</label></td>
                            <td>
                                <select name="category">
                                    <c:forEach items="${categoryList}" var="c">
                                        <option value=${c.getCategoryId()}
                                                <c:if test="${c.equals(item.category)}">selected</c:if>
                                                >${c.getCategoryName()}</option>
                                    </c:forEach>
                                </select>                        
                            </td>
                        </tr>
                        <tr>
                            <td><label>Item Name: </label></td>
                            <td><input type="text" name="itemName" value="${item.itemName}"></td>
                        </tr>
                        <tr>
                            <td rowspan="2"><label>Price: </label></td>
                            <td><input type="text" name="price" value="${item.price}"></td>
                        </tr>
                        <tr>
                            <td>
                                <input type="submit" value="Save">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="itemID" value="${item.itemId}">
                            </td>
                        
                        </tr>
                    </table>
                </form>  
            </c:when>
            <c:otherwise>
                <form method="POST" action="inventory">
                    <h1>Add Item</h1>
                    <table class="tableForm">
                        <tr>
                            <td><label>Category</label></td>
                            <td>
                                <select name="category">
                                    <c:forEach items="${categoryList}" var="c">
                                        <option value=${c.getCategoryId()}
                                                <c:if test="${c.equals(item.category)}">selected</c:if>
                                                >${c.getCategoryName()}</option>
                                    </c:forEach>
                                </select>                        
                            </td>
                        </tr>
                        <tr>
                            <td><label>Item Name </label></td>
                            <td><input type="text" name="itemName" value="${item.itemName}"></td>
                        </tr>
                        <tr>
                            <td><label>Price </label></td>
                            <td><input type="text" name="price" value="${item.price}"></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <input type="submit" value="Save">
                                <input type="hidden" name="action" value="add">
                            </td>

                        </tr>
                    </table>
                </form>  
            </c:otherwise>
        </c:choose>
        <c:if test="${message eq 'invalid_input'}">
            <p>Error. Please enter all fields.</p> 
            <p>Hint: the range of price is 0 to 9999.</p>
        </c:if>   

    </body>
</html>
