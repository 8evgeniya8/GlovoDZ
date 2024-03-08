package com.example.glovodz.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.glovodz.dto.OrderDTO;
import com.example.glovodz.mapper.OrderMapper;
import com.example.glovodz.models.Order;
import com.example.glovodz.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    private Order order;
    private OrderDTO orderDTO;
    private final int orderId = 1;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(orderId);

        orderDTO = new OrderDTO();
        orderDTO.setId(orderId);

        when(orderMapper.orderToOrderDTO(any(Order.class))).thenReturn(orderDTO);
        when(orderMapper.orderDTOToOrder(any(OrderDTO.class))).thenReturn(order);
    }

    @Test
    public void shouldReturnOrderById() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        OrderDTO result = orderService.getOrderById(orderId);

        assertNotNull(result);
        assertEquals(orderId, result.getId());
        verify(orderRepository).findById(orderId);
        verify(orderMapper).orderToOrderDTO(order);
    }

    @Test
    public void shouldReturnNullWhenOrderNotFound() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.empty());

        OrderDTO result = orderService.getOrderById(orderId);

        assertNull(result);
        verify(orderRepository).findById(orderId);
    }

    @Test
    void getAllOrdersTest() {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderDTO> results = orderService.getAllOrders();

        assertFalse(results.isEmpty());
        assertEquals(2, results.size());
        verify(orderRepository).findAll();
        verify(orderMapper, times(orders.size())).orderToOrderDTO(any(Order.class));
    }

    @Test
    void addOrderTest() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO result = orderService.addOrder(orderDTO);

        assertNotNull(result);
        verify(orderRepository).save(any(Order.class));
        verify(orderMapper).orderDTOToOrder(any(OrderDTO.class));
    }

    @Test
    void updateOrderTest() {
        when(orderRepository.existsById(orderId)).thenReturn(true);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO result = orderService.updateOrder(orderId, orderDTO);

        assertNotNull(result);
        assertEquals(orderId, result.getId());
        verify(orderRepository).existsById(orderId);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void deleteOrderTest() {
        when(orderRepository.existsById(orderId)).thenReturn(true);

        boolean result = orderService.delete(orderId);

        assertTrue(result);
        verify(orderRepository).existsById(orderId);
        verify(orderRepository).deleteById(orderId);
    }

    @Test
    void deleteOrderNotFoundTest() {
        when(orderRepository.existsById(orderId)).thenReturn(false);

        boolean result = orderService.delete(orderId);

        assertFalse(result);
        verify(orderRepository).existsById(orderId);
        verify(orderRepository, never()).deleteById(orderId);
    }
}
