<h4>Write address</h3>
    <form id="orderForm">
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="inputCountry">Country</label>
                <input type="text" class="form-control" id="inputCountry" placeholder="Write country" name="country"
                       value="${user.address.country}">
            </div>
            <div class="form-group col-md-6">
                <label for="inputCity">City</label>
                <input type="text" class="form-control" id="inputCity" placeholder="Write city" name="city"
                       value="${user.address.city}">
            </div>
            <div class="form-group col-md-6">
                <label for="inputStreet">Street</label>
                <input type="text" class="form-control" id="inputStreet" placeholder="Write street" name="street"
                       value="${user.address.street}">
            </div>
            <div class="form-group col-md-6">
                <label for="inputFloor">Floor</label>
                <input type="number" class="form-control" id="inputFloor" placeholder="Write your floor" name="floor"
                       value="${user.address.floor}" min="1">
            </div>
            <div class="form-group col-md-6">
                <label for="inputPost">Post</label>
                <input type="number" class="form-control" id="inputPost" placeholder="Write post number" name="post"
                       value="${user.address.post}" min="1">
            </div>
            <div class="form-group col-md-6">
                <label for="inputPost">House number</label>
                <input type="number" class="form-control" id="inputHouseNumber" placeholder="Write house number"
                       name="houseNumber" value="${user.address.houseNumber}" min="1">
            </div>
            <div class="form-group col-md-6">
                <label for="inputState">Methods of pay</label>
                <select id="inputState" class="form-control">
                    <option selected>Choose method</option>
                    <option>Credit card</option>
                    <option>PayPal</option>
                </select>
            </div>
            <div class="form-group col-md-8">
                <label for="totalPrice">Your total price</label>
                <input type="number" class="form-control" id="totalPrice" readonly value="${basket.getTotalPrice()}">
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="gridCheck">
                <label class="form-check-label" for="gridCheck">
                    Do you want to pickup from post office?
                </label>
            </div>
        </div>

        <button type="button" class="btn btn-primary" id="orderButton">Order</button>
    </form>