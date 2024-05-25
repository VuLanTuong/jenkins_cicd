package com.example.order_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;
    @Column(name = "username")
    private String username;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    List<OrderDetail> listOrderDetail;

    public Order(LocalDateTime createdDate, List<OrderDetail> listOrderDetail) {
        this.createdDate = createdDate;
        this.listOrderDetail = listOrderDetail;
    }
}
