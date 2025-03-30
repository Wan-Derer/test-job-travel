package ru.demo.testjobtravel.model;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "products")
public record Product(
    @Id
    @Column("prd_id")
    Long id,

    @Column("prd_name")
    String name,

    @Column("prd_description")
    String description,

    @Column("prd_price")
    BigDecimal price
) {

}
