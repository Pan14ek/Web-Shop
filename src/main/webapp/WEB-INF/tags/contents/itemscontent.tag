<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${lowPriority}">
    <div class="alert alert-danger" role="alert">
        You have a low priority.
    </div>
    <c:remove var="lowPriority"/>
</c:if>
<h2>All electronics</h2>
<br>
<div>
    <c:if test="${emptyItems}">
        <h1>Item did not found</h1>
    </c:if>
    <div class="card-columns">
        <c:forEach var="item" items="${items}">
            <div class="card">
                <img src="/image?link=${item.imageLink}" class="card-img-top" alt="${item.title}">
                <div class="card-body">
                    <h6 class="card-title">${item.title}</h6>
                    <p class="card-text">Description : ${item.description}</p>
                    <p class="card-text">Producer : ${item.producer.title}</p>
                    <p class="card-text">Category : ${item.category.title}</p>
                    <p class="card-text">Price : ${item.price} $</p>
                </div>
                <div class="card-footer">
                    <button id="itemButton" type="button" class="itemButton" value="${item.id}">Add to basket</button>
                </div>
            </div>
        </c:forEach>
    </div>
</div>