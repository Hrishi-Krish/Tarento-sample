package com.tarento.customrepository.controller;

import com.tarento.customrepository.dto.OrdersDto;
import com.tarento.customrepository.model.Orders;
import com.tarento.customrepository.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/get")
    public List<Orders> getAllOrders() {
        return ordersService.findAllOrders();
    }

    @GetMapping("/get/{id}")
    public Orders getOrdersById(@PathVariable long id) {
        return ordersService.findOrdersById(id);
    }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrders(@RequestBody OrdersDto ordersDto) {
        LocalDateTime orderDate = ordersDto.getOrderDate();
        String status = ordersDto.getStatus();
        ordersService.createOrders(orderDate, status);
    }

    @PatchMapping("/patch/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOrders(@PathVariable long id, @RequestBody OrdersDto ordersDto) {
        LocalDateTime orderDate = ordersDto.getOrderDate();
        String status = ordersDto.getStatus();
        ordersService.updateOrders(id, orderDate, status);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrders(@PathVariable long id) {
        ordersService.deleteOrders(id);
    }
}
