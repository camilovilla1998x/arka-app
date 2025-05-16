package com.arka.arka_app.model.mysql;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_items")
@Getter @Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el carrito
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // Relación con el producto
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

}
