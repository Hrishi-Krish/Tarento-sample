package com.tarento.customrepository.service;

import com.tarento.customrepository.model.Orders;
import com.tarento.customrepository.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    public List<Orders> findAllOrders() {
        return ordersRepository.findAll();
    }

    public Orders findOrdersById(long id) {
        Optional<Orders> order = ordersRepository.findById(id);
        if (order.isEmpty()) {
            throw new RuntimeException("Order with id " + id + " not found");
        }
        return order.get();
    }

    public void createOrders(LocalDateTime orderDate, String status) {
        Orders order = new Orders();
        order.setOrderDate(orderDate);
        order.setStatus(status);

        ordersRepository.save(order);
    }

    public void updateOrders(long id, LocalDateTime orderDate, String status) {
        try {
            Orders orders = findOrdersById(id);
            orders.setOrderDate(orderDate);
            orders.setStatus(status);

            ordersRepository.save(orders);
        } catch (Exception e) {
            throw new RuntimeException("Order with id " + id + " not found");
        }
    }

    public void deleteOrders(long id) {
        ordersRepository.deleteById(id);
    }
}
