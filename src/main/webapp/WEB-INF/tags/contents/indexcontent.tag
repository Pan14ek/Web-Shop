<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="interface"/>
<div class="container">
    <c:if test="${lowPriority}">
        <div class="alert alert-danger" role="alert">
            You have a low priority.
        </div>
        <c:remove var="lowPriority"/>
    </c:if>
    <div id="information-slide" class="carousel slide" data-ride="carousel">
        <!-- Indicators -->
        <ul class="carousel-indicators">
            <li data-target="#information-slide" data-slide-to="0" class="active"></li>
            <li data-target="#information-slide" data-slide-to="1"></li>
            <li data-target="#information-slide" data-slide-to="2"></li>
        </ul>
        <!-- The slideshow -->
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="./images/carousel/photo1.jpg" class="d-block w-100" id="carousel-photo">
            </div>
            <div class="carousel-item">
                <img src="./images/carousel/photo2.jpg" class="d-block w-100" id="carousel-photo">
            </div>
            <div class="carousel-item">
                <img src="./images/carousel/photo1.jpg" class="d-block w-100" id="carousel-photo">
            </div>
        </div>
        <!-- Left and right controls -->
        <a class="carousel-control-prev" href="#information-slide" data-slide="prev">
            <span class="carousel-control-prev-icon"></span>
        </a>
        <a class="carousel-control-next" href="#information-slide" data-slide="next">
            <span class="carousel-control-next-icon"></span>
        </a>
    </div>
    <div class="history">
        <h4><fmt:message key="lastViewedItems"/></h4>
        <div class="card-deck">
            <div class="card">
                <img src="./images/items/laptop1.jpg" class="card-img-top" alt="...">
                <div class="card-body">
                    <h6 class="card-title">Apple MacBook Pro 15" Retina 2019 Space Gray</h6>
                </div>
                <div class="card-footer">
                    <a href="#" class="card-link">
                        <p class="text-center">More info</p>
                    </a>
                </div>
            </div>
            <div class="card">
                <img src="./images/items/laptop1.jpg" class="card-img-top" alt="...">
                <div class="card-body">
                    <h6 class="card-title">Apple MacBook Pro 15" Retina 2019 Space Gray</h6>
                </div>
                <div class="card-footer">
                    <a href="#" class="card-link">
                        <p class="text-center">More info</p>
                    </a>
                </div>
            </div>
            <div class="card">
                <img src="./images/items/laptop1.jpg" class="card-img-top" alt="...">
                <div class="card-body">
                    <h6 class="card-title">Apple MacBook Pro 15" Retina 2019 Space Gray</h6>
                </div>
                <div class="card-footer">
                    <a href="#" class="card-link">
                        <p class="text-center">More info</p>
                    </a>
                </div>
            </div>
            <div class="card">
                <img src="./images/items/laptop1.jpg" class="card-img-top" alt="...">
                <div class="card-body">
                    <h6 class="card-title">Apple MacBook Pro 15" Retina 2019 Space Gray</h6>
                </div>
                <div class="card-footer">
                    <a href="#" class="card-link">
                        <p class="text-center">More info</p>
                    </a>
                </div>
            </div>
            <div class="card">
                <img src="./images/items/laptop1.jpg" class="card-img-top" alt="...">
                <div class="card-body">
                    <h6 class="card-title">Apple MacBook Pro 15" Retina 2019 Space Gray</h6>
                </div>
                <div class="card-footer">
                    <a href="#" class="card-link">
                        <p class="text-center">More info</p>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>