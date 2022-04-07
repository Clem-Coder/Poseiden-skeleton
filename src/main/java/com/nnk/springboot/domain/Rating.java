package com.nnk.springboot.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "rating")
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "moodys_rating")
    @NotBlank(message = "Moody's rating is mandatory")
    private String moodysRating;
    @Column(name = "sandP_rating")
    @NotBlank(message = "SandP rating is mandatory")
    private String sandPRating;
    @Column(name = "fitch_rating")
    @NotBlank(message = "Fitch rating is mandatory")
    private String fitchRating;
    @Column(name = "order_number")
    @Min(value = 0, message = "Order Number have to be higher or equals to 0 ")
    private int orderNumber;

    public Rating(String moodysRating, String sandPRating, String fitchRating, int orderNumber) {
        this.moodysRating = moodysRating; this .sandPRating = sandPRating; this.fitchRating = fitchRating; this.orderNumber = orderNumber;
    }

    public Rating() {

    }
}
