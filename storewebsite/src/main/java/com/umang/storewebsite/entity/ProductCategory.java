package com.umang.storewebsite.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class ProductCategory {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "category")
    private Set<Product> products;

}
