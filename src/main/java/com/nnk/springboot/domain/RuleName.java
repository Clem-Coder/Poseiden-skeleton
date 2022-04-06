package com.nnk.springboot.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "rule_name")
public class RuleName {
    // TODO: Map columns in data table RULENAME with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Description is mandatory")
    private String description;
    @NotBlank(message = "JSON is mandatory")
    private String json;
    @NotBlank(message = "Template is mandatory")
    private String template;
    @Column(name = "sql_str")
    @NotBlank(message = "SQL is mandatory")
    private String sqlStr;
    @Column(name = "sql_part")
    @NotBlank(message = "SQL part is mandatory")
    private String sqlPart;

    public RuleName(String ruleName, String description, String json, String template, String sql, String sqlPart) {
        this.name = ruleName; this.description = description; this.json = json; this.template=template; this.sqlStr = sql; this.sqlPart =sqlPart;
    }

    public RuleName() {

    }
}
