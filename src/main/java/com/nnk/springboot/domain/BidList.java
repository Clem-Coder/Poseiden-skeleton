package com.nnk.springboot.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "bid_list")
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_list_id")
    private int bidListId;
    @NotBlank(message = "Account is mandatory")
    private String account;
    @NotBlank(message = "type is mandatory")
    private String type;
    @Column(name = "bid_quantity")
    @DecimalMin(value = "0.1", message = "Bid quantity have to be higher than 0")
    private double bidQuantity;
    @Column(name = "ask_quantity")
    private double askQuantity;
    private double bid;
    private double ask;
    private String benchmark;
    @Column(name = "bid_list_date")
    private Date bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    @Column(name = "creation_name")
    private String creationName;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "revision_name")
    private String revisionName;
    @Column(name = "revision_date")
    private Date revisionDate;
    @Column(name = "deal_name")
    private String dealName;
    @Column(name = "deal_type")
    private String dealType;
    @Column(name = "source_list_id")
    private String sourceListId;
    private String side;

    public BidList(String account, String type, double bidQuantity) {
        this.account = account; this.type = type; this.bidQuantity = bidQuantity;
    }

    public BidList() {

    }
}
