package com.example.glovodz.service;

import com.example.glovodz.dto.OrderDTO;
import com.example.glovodz.dto.models.Order;
import com.example.glovodz.mapper.OrderMapper;
import com.example.glovodz.repository.jdbc.OrderJDBCRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class OrderServiceImpl implements OrderService {

    private final OrderJDBCRepository orderRepository;
    private final OrderMapper orderMapper;
    @Override
    public OrderDTO getOrderById(int id) {
        var order = orderRepository.getOrderById(id);
        return orderMapper.orderToOrderDTO(order);
    }
    @Override
    public List<OrderDTO> getAllOrders() {
        var orders = orderRepository.getAllOrders();
        return orders.stream()
                .map(orderMapper::orderToOrderDTO)
                .collect(Collectors.toList());
    }
    @Override
    public OrderDTO addOrder(OrderDTO orderDTO) {
        Order order = orderMapper.orderDTOToOrder(orderDTO);
        order = orderRepository.addOrder(order);
        return orderMapper.orderToOrderDTO(order);
    }

    @Override
    public OrderDTO updateOrder(int id, OrderDTO orderDTO) {
        Order order = orderMapper.orderDTOToOrder(orderDTO);
        order.setId(id);
        orderRepository.updateOrder(order);
        return orderDTO;
    }
    @Override
    public boolean delete(Integer id) {
        boolean exists = orderRepository.existsById(id);
        if (exists) {
            orderRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
