package com.nnk.springboot.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Date;


@Entity
@Data
@Table(name = "trade")
public class Trade {
    // TODO: Map columns in data table TRADE with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tradeId;
    private String account;
    private String type;
    private double buyQuantity;
    private double sellQuantity;
    private String benchmark;
    private Date tradeDate;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private java.sql.Date creationDate;
    private String revisionName;
    private java.sql.Date revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;

    public Trade(String tradeAccount, String type) {
        this.account = tradeAccount; this.type = type;
    }

    public Trade() {

    }
}
