package com.demo.petstore.domain;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="order_table")
public class Order {
    
    @Id  @GeneratedValue
    Long id;
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }

    @ElementCollection
    List<OrderItem> orderItems;
        public List<OrderItem> getOrderItems() {
            return orderItems;
        }
        public void setOrderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
        }

    @ManyToOne
    Customer customer;
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
        
}
