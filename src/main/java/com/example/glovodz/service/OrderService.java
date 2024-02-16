package com.example.glovodz.service;

import com.example.glovodz.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO getOrderById(int id);
    List<OrderDTO> getAllOrders();
    OrderDTO addOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(int id, OrderDTO orderDTO);
    boolean delete(Integer orderId);
}
