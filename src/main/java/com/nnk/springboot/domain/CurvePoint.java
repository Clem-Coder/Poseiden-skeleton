package com.nnk.springboot.domain;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Table(name = "curvepoint")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int curveId;
    private Date asOfDate;
    private double term;
    private double value;
    private Date creationDate;

    public CurvePoint(int i, double v, double v1) {
    }

    public CurvePoint() {

    }
}
