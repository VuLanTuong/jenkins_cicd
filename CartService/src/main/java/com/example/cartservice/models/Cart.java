package com.example.cartservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private long cartId;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<CartItem> listCartItem;

    public Cart(LocalDateTime createdDate, List<CartItem> listCartDetail) {
        this.createdDate = createdDate;
        this.listCartItem = listCartDetail;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", username=" + username +
                ", createdDate=" + createdDate +
                '}';
    }
}
