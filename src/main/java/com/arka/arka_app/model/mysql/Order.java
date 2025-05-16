package com.arka.arka_app.model.mysql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
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
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp // Fecha automática cuando se crea
    private LocalDateTime createdAt;

    //* Estado del pedido: PENDING, SHIPPED, DELIVERED, CANCELLED, etc.
    private String status;

    private BigDecimal totalAmount;

    //* Relación N:1 -> Muchas unidades pedidas pertenecen a un cliente
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    //* Relación 1:N -> Una orden tiene muchas unidades pedidas
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) //CascadeALL para que cualquier operación que ocurra en order se aplique en orderitem
    private List<OrderItem> items;                                                  //ej: Si guardas un nuevo pedido, se guardan también sus ítems. - Si actualizas el pedido, los ítems también se actualizan., etc
                                                                                    //orphanRemoval ->  si se elimina un OrderItem de la lista, ese ítem se eliminará automáticamente de la base de datos. 
                                                                                    //Sirve para evitar que queden ítems “huérfanos” sin un pedido asociado.
}   
