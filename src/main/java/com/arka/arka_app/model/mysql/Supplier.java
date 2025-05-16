package com.arka.arka_app.model.mysql;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "suppliers")
@Getter @Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class Supplier {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) //El nombre del proveedor es obligatorio.
    private String name;

    private String contactEmail;

    private String phone;

    private String address;

    //* Relación ->  Un proveedor puede suministrar varios productos
    @OneToMany(mappedBy = "supplier")
    private List<Product> products;


}
