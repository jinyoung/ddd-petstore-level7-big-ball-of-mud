package com.demo.petstore.domain;

import javax.persistence.Embeddable;

//import java.util.List;

//import javax.persistence.OneToMany;


@Embeddable
public class OrderItem {

    String productId;

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
