package com.example.glovodz.service;

import com.example.glovodz.dto.OrderDTO;
import com.example.glovodz.mapper.OrderMapper;
import com.example.glovodz.models.Order;
import com.example.glovodz.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDTO getOrderById(int id) {
        return orderRepository.findById(id)
                .map(orderMapper::orderToOrderDTO)
                .orElse(null);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return ((List<Order>) orderRepository.findAll())
                .stream()
                .map(orderMapper::orderToOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO addOrder(OrderDTO orderDTO) {
        Order order = orderMapper.orderDTOToOrder(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.orderToOrderDTO(order);
    }

    @Override
    public OrderDTO updateOrder(int id, OrderDTO orderDTO) {
        if (orderRepository.existsById(id)) {
            Order order = orderMapper.orderDTOToOrder(orderDTO);
            order.setId(id);
            order = orderRepository.save(order);
            return orderMapper.orderToOrderDTO(order);
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
