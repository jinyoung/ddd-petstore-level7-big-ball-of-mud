package com.demo.petstore.infra;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.petstore.domain.CustomerRepository;
import com.demo.petstore.domain.Mileage;
import com.demo.petstore.domain.MileageRepository;
import com.demo.petstore.domain.Order;
import com.demo.petstore.domain.OrderRepository;

@RestController
public class PetstoreService {


    @Autowired
	OrderRepository orderRepository;

    @Autowired
    MileageRepository mileageRepository;

    @Autowired
    CustomerRepository customerRepository;


	@RequestMapping(method = RequestMethod.PUT, path="pet-order")
    @Transactional
	public Order petOrder(@RequestBody Order order, @RequestHeader("userId") String userId){

        customerRepository.findById(userId).ifPresentOrElse(customer->{
            order.setCustomer(customer);
            orderRepository.save(order);

            // Calculate the total amount of the order
            double totalAmount = order.getOrderItems().stream()
            .mapToDouble(item -> item.getPrice() * item.getQty())
            .sum();

            // Calculate 1% of the total amount for mileage
            double mileagePoints = totalAmount * 0.01;

            // Set the mileage points
            Mileage mileage = new Mileage();
            mileage.setCustomer(order.getCustomer());
            mileage.setAmount(mileagePoints);
            mileageRepository.save(mileage);
    
        
        }, ()->{throw new RuntimeException("No such userId" + userId);});

        return order;

	}

    
}
