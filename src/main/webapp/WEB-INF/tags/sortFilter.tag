<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="itemInfo" tagdir="/WEB-INF/tags/items.operation" %>
<form action="operate" method="get" name="itemOperate" novalidate>
    <div class="col-sm-4">
        <itemInfo:itemsAmount/>
    </div>
    <div class="col-sm-4">
        <c:if test="${errors['page']}">
        <h5>Write correct page</h1>
            </c:if>
            <itemInfo:pagination/>
    </div>
    <div class="col-sm-4">
        <itemInfo:searchInfo/>
    </div>
    <div class="col-sm-4">
        <c:if test="${errors['sortParameter']}">
        <h5>Write correct sort parameter</h1>
            </c:if>
            <label for="sortCourses">Sort by</label>
    </div>
    <div class="col-sm-4">
        <itemInfo:sortInformation/>
    </div>
    <div class="col-sm-4">
        <c:if test="${errors['category']}">
        <h5>Write correct category</h1>
            </c:if>
            <label for="filter">Item categories</label>
    </div>
    <div class="col-sm-4">
        <itemInfo:categoriesInfo/>
    </div>
    <div class="col-sm-4">
        <c:if test="${errors['producers']}">
        <h5>Write correct producers</h1>
            </c:if>
            <label for="filter">Producers</label>
    </div>
    <div class="col-sm-4">
        <itemInfo:producersInfo/>
    </div>
    <div class="col-sm-2">
        <c:if test="${errors['price']}">
        <h5>Write positive number</h1>
            </c:if>
            <label for="filter">Price</label>
    </div>
    <div class="col-sm-4">
        <itemInfo:pricesInfo/>
    </div>
    <br>
    <div class="col-sm-4">
        <button type="submit" id="changeInformation" class="btn btn-primary btn-sm">Apply choice</button>
    </div>
</form>