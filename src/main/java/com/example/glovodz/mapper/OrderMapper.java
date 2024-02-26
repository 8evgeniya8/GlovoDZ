package com.example.glovodz.mapper;

import com.example.glovodz.dto.OrderDTO;
import com.example.glovodz.models.Order;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDTO orderToOrderDTO(Order order);
    Order orderDTOToOrder(OrderDTO orderDTO);
}

