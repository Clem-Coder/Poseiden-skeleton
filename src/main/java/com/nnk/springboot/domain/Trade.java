package com.nnk.springboot.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Date;


@Entity
@Getter
@Setter
@Table(name = "trade")
public class Trade {
    // TODO: Map columns in data table TRADE with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id")
    private int tradeId;
    @NotBlank(message = "Account is mandatory")
    private String account;
    @NotBlank(message = "Type is mandatory")
    private String type;
    @Column(name = "buy_quantity")
    @DecimalMin(value = "0.1", message = "Buy quantity have to be higher than 0")
    private double buyQuantity;
    @Column(name = "sell_quantity")
    private double sellQuantity;
    private String benchmark;
    @Column(name = "trade_date")
    private Date tradeDate;
    private String security;
    private String status;
    private String trader;
    private String book;
    @Column(name = "creation_name")
    private String creationName;
    @Column(name = "creation_date")
    private java.sql.Date creationDate;
    @Column(name = "revision_name")
    private String revisionName;
    @Column(name = "revision_date")
    private java.sql.Date revisionDate;
    @Column(name = "deal_name")
    private String dealName;
    @Column(name = "deal_type")
    private String dealType;
    @Column(name = "source_list_id")
    private String sourceListId;
    private String side;

    public Trade(String tradeAccount, String type, double buyQuantity) {
        this.account = tradeAccount; this.type = type; this.buyQuantity = buyQuantity;
    }

    public Trade() {

    }
}
