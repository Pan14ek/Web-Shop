package com.test.makieiev.webshop.model.order;

import com.test.makieiev.webshop.model.item.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class Basket {

    private final Map<Product, Integer> items;

    public Basket() {
        items = new HashMap<>();
    }

    public void add(Product product, int amount) {
        items.put(product, amount);
    }

    public void update(long id, int amount) {
        getProduct(id).ifPresent(product -> items.replace(product, amount));
    }

    public void remove(long id) {
        items.keySet().stream()
                .filter(electronic -> Objects.equals(electronic.getId(), id))
                .findFirst()
                .ifPresent(product -> items.remove(product));
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public long getTotalItems() {
        return items.size();
    }

    public double getTotalPrice() {
        AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);
        items.forEach((electronic, amount) -> totalPrice
                .updateAndGet(price -> price + electronic.getPrice().doubleValue() * amount));
        return totalPrice.get();
    }

    public boolean containsId(long id) {
        return items.keySet()
                .stream()
                .map(Product::getId)
                .anyMatch(itemId -> Objects.equals(itemId, id));
    }

    public boolean contains(Product product) {
        return items.containsKey(product);
    }

    private Optional<Product> getProduct(long id) {
        return items.keySet()
                .stream()
                .filter(product -> Objects.equals(product.getId(), id))
                .findFirst();
    }

}