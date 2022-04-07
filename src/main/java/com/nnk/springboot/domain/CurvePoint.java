package com.nnk.springboot.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "curve_point")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "curve_id")
    @Min(value = 0, message = "Curve ID have to be higher or equals to 0 ")
    private int curveId;
    @Column(name = "as_of_date")
    private Date asOfDate;
    @DecimalMin(value = "0.1", message = "Term have to be higher than 0")
    private double term;
    @DecimalMin(value = "0.1", message = "Value have to be higher than 0")
    private double value;
    @Column(name = "creation_date")
    private Date creationDate;

    public CurvePoint(int curveId, double term, double value) {
        this.curveId = curveId; this.term = term; this.value = value;
    }

    public CurvePoint() {
    }
}
