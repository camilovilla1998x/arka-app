package com.arka.arka_app.model.mysql;

import java.math.BigDecimal;

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
@Table(name = "order_items")
@Getter @Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //* RELACIÓN: Muchos unidades pedidas pertenecen a una orden
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    //* RELACIÓN: Muchas unidades pedidas hacen referencia a un producto
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity; //Cantidad de unidades pedidas
    private BigDecimal price; // Precio unitario en el momento de la compra


}
