<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="styles/photos.css">
    <title>Information</title>
</head>

<body>
<!-- HEAD -->
<nav class="navbar navbar-expand-sm bg-dark navbar-dark text-center" style="height: 30px">
    <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item active">
                <a class="nav-link" href="#">My account <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">My cart</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Checkout</a>
            </li>
        </ul>
        <div class="my-lg-0">
            <ul class="navbar-nav mr-automt-lg-0">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Language
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="#">English</a>
                        <a class="dropdown-item" href="#">Russia</a>
                        <a class="dropdown-item" href="#">Ukraine</a>
                    </div>
                </li>
                <li class="nav-item" data-toggle="modal" data-target="#modal-center">
                    <a class="nav-link" href="#">Sign in <span class="sr-only">(current)</span></a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item">
                <a class="nav-link" href="../index.html">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item activate">
                <a class="nav-link" href="./items.html">Shop page</a>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
            <button type="button" class="btn btn-outline-success my-2 my-sm-0">
                <a href="./cart.html">Cart <span class="badge">4</span></a>
            </button>
        </form>
    </div>
</nav>
<br>
<!-- BODY -->
<div class="container">
    <div class="row">
        <div class="col-sm-4">
            <div>
                <img src="../images/items/laptop1.jpg" class="card-img-top" id="info-photo"
                     alt=">Apple MacBook Pro 15' Retina 2019 Space Gray">
            </div>
        </div>
        <div class="col-sm-8">
            <h2>Apple MacBook Pro 15" Retina 2019 Space Gray</h2>
            <h5>Description</h5>
            <p>Some text..</p>
            <br>
            <form class="form-inline">
                <label for="exampleInputEmail1">Quantity</label>
                <div class="form-group mx-sm-3 mb-2">
                    <input type="number" class="form-control" name="quantity" id="quantity" min="1">
                </div>
                <button type="submit" class="btn btn-success mb-2">Buy</button>
            </form>
        </div>
    </div>
</div>
<!-- ADDITION FORMS -->
<div class="modal fade" id="modal-center" tabindex="-1" role="dialog" aria-labelledby="modal-center-title"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modal-center-title">Sign in</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <div class="modal-body">
                <form action="signin" method="post" name="signIn">
                    <div class="form-group">
                        <label for="inputEmail">Email address</label>
                        <input type="email" class="form-control" id="inputEmail" aria-describedby="emailHelp"
                               placeholder="Enter email">
                        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone
                            else.</small>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword">Password</label>
                        <input type="password" class="form-control" id="inputPassword" placeholder="Password">
                    </div>
                    <button type="submit" class="btn btn-primary">Sign in</button>
                </form>
            </div>

            <div class="modal-footer">
                <a class="nav-link" href="./signup.html">
                    <button type="button" class="btn btn-primary">Sign up</button>
                </a>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
<script src="../scripts/jQueryValidation.js"></script>
<script type="text/javascript" src="../scripts/jQuerySignInController.js"></script>
</body>

</html>
