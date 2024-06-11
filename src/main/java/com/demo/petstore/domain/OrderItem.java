package com.demo.petstore.domain;

import javax.persistence.Embeddable;

//import java.util.List;

//import javax.persistence.OneToMany;


@Embeddable
public class OrderItem {

    String productId;

    Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    Integer qty;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    

}
