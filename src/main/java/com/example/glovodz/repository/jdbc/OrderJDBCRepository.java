package com.example.glovodz.repository.jdbc;

import com.example.glovodz.dto.OrderDTO;
import com.example.glovodz.dto.models.Order;
import com.example.glovodz.mapper.OrderRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderJDBCRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ?";
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM orders";
    private static final String DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE id = ?";
    private static final String INSERT_ORDER = "INSERT INTO orders (date, cost) VALUES (?, ?)";
    private static final String UPDATE_ORDER = "UPDATE orders SET date = ?, cost = ? WHERE id = ?";
    private static final String COUNT_ORDER_BY_ID = "SELECT COUNT(*) FROM orders WHERE id = ?";

    public Order getOrderById(int id) {
        return jdbcTemplate.queryForObject(SELECT_ORDER_BY_ID, new Object[]{id}, new OrderRowMapper());
    }

    public List<Order> getAllOrders() {
        return jdbcTemplate.query(SELECT_ALL_ORDERS, new OrderRowMapper());
    }

    public Order addOrder(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_ORDER, new String[] {"id"});
                    ps.setDate(1, new java.sql.Date(order.getDate().getTime()));
                    ps.setDouble(2, order.getCost());
                    return ps;
                }, keyHolder);
        order.setId(keyHolder.getKey().intValue());
        return order;
    }

    public void updateOrder(Order order) {
        jdbcTemplate.update(UPDATE_ORDER, order.getDate(), order.getCost(), order.getId());
    }

    public boolean existsById(int id) {
        Integer count = jdbcTemplate.queryForObject(
                COUNT_ORDER_BY_ID,
                new Object[]{id},
                Integer.class
        );
        return count != null && count > 0;
    }

    public void deleteById(int id) {
        jdbcTemplate.update(DELETE_ORDER_BY_ID, id);
    }
}
