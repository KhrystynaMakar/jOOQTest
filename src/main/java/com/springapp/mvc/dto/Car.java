package com.springapp.mvc.dto;

import java.sql.Date;

public class Car {
    private Long id;
    private String manufactor;
    private String model;
    private String color;
    private Date createDate;
    private Integer doorQuantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufactor() {
        return manufactor;
    }

    public void setManufactor(String manufactor) {
        this.manufactor = manufactor;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getDoorQuantity() {
        return doorQuantity;
    }

    public void setDoorQuantity(Integer doorQuantity) {
        this.doorQuantity = doorQuantity;
    }
}
