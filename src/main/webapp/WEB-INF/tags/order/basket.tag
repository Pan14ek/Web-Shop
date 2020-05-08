<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table">
    <thead class="thead-dark">
    <tr>
        <th scope="col">Title</th>
        <th scope="col">Price</th>
        <th scope="col">Quantity</th>
        <th scope="col">Total</th>
        <th scope="col">Operation</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${basket.items}" var="basketItem">
        <tr class="element">
            <td>${basketItem.key.title}</td>
            <td id="price${basketItem.key.id}">${basketItem.key.price}</td>
            <td>
                <div class="quantity-block">
                    <input type="button" class="btn btn-success btn-sm" id="quantity-minus${basketItem.key.id}"
                           onclick="minusQuantity('${basketItem.key.id}','quantity-num${basketItem.key.id}','price${basketItem.key.id}','total-price${basketItem.key.id}')"
                           value="-"/>
                    <input type="number" id="quantity-num${basketItem.key.id}" value="${basketItem.value}" min="1"
                           step="1" size="4"
                           oninput="calculateTotalPrice('${basketItem.key.id}','price${basketItem.key.id}','quantity-num${basketItem.key.id}','total-price${basketItem.key.id}')"/>
                    <input type="button" class="btn btn-success btn-sm" id="quantity-plus${basketItem.key.id}"
                           onclick="plusQuantity('${basketItem.key.id}','quantity-num${basketItem.key.id}','price${basketItem.key.id}','total-price${basketItem.key.id}')"
                           value="+"/>
                </div>
            </td>
            <td>
                <div id="total-div">
                    <input type="number" readonly id="total-price${basketItem.key.id}"
                           value="${basketItem.key.price*basketItem.value}" min="1" step="1" size="4"/>
                </div>
            </td>
            <td>
                <button id="cancelButton" type="button" class="cancelButton" value="${basketItem.key.id}">Cancel
                </button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<button type="submit" class="btn btn-success" id="checkout">Proceed to checkout</button>