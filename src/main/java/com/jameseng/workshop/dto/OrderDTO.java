package com.jameseng.workshop.dto;

import com.jameseng.workshop.entities.Order;
import com.jameseng.workshop.entities.OrderItem;
import com.jameseng.workshop.entities.Payment;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class OrderDTO {

    private Long id;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant moment;

    private Integer orderStatus;

    private UserDTO client;

    private Set<OrderItemDTO> items = new HashSet<>();

    private Double total;

    private Payment payment;

    public OrderDTO() {

    }

    public OrderDTO(Long id, Instant moment, Integer orderStatus, UserDTO client, Set<OrderItemDTO> items, Double total, Payment payment) {
        this.id = id;
        this.moment = moment;
        this.orderStatus = orderStatus;
        this.client = client;
        this.items = items;
        this.total = total;
        this.payment = payment;
    }

    public OrderDTO(Order order) {
        id = order.getId();
        moment = order.getMoment();
        orderStatus = order.getOrderStatus().getCode();
        client = new UserDTO(order.getClient());
        total = order.getTotal();
        payment = order.getPayment();
    }

    public OrderDTO(Order order, Set<OrderItem> orderItem) {
        this(order);
        //setItems(orderItemDto.stream().map(oi -> new OrderItemDTO(oi)).collect(Collectors.toList()));
        //setItems(orderItem.stream().map(x -> new OrderItemDTO(x)).collect(Collectors.toList()););
        //this.items.stream().map(oi -> new OrderItemDTO(oi)).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UserDTO getClient() {
        return client;
    }

    public void setClient(UserDTO client) {
        this.client = client;
    }

    public Set<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(Set<OrderItemDTO> items) {
        this.items = items;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
