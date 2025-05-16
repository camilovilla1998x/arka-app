package com.arka.arka_app.model.mysql;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "carts")
@Getter @Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //* Relación -> Un cliente tiene un carrito.
    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    private Customer customer;

    // Relación con los productos del carrito (muchos a muchos con cantidad → usamos tabla intermedia CartItem) REVISAR
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> items = new HashSet<>(); //Usamos Set para evitar elementos duplicados
    //En el carrito obviamente no debemos tener el mismo producto repetido varias veces (como dos CartItem con el mismo product_id), si el usuario agrega el mismo producto aumenta la cantidad.

}
