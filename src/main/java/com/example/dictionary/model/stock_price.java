package com.example.dictionary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Component
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock_price",uniqueConstraints = @UniqueConstraint(columnNames = {"symbol","date"}))
public class stock_price {


    @Id
    @GeneratedValue
    @Column
    private Long id;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "date")
    private Date date;
    @Column
    private BigDecimal open;
    @Column
    private BigDecimal high;
    @Column
    private BigDecimal low;
    @Column
    private BigDecimal close;
    @Column
    private int volume;


}
