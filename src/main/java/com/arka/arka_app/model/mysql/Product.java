package com.arka.arka_app.model.mysql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "products")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    private String brand;

    @CreationTimestamp
    @Column(updatable = false) //evita que alguien actualice el campo createdAt manualmente
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    //* Relación -> Muchos productos pueden pertenecer a una categoría.
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    //* Relación -> Muchos productos pueden tener un mismo proveedor
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    //* Relación con ítems de carritos que contienen este producto
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();


}
