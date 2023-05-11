package com.qbb.model;

import java.util.List;

public class Row {


    private String rowId;
    private int rowNum;
    private List<Seat> seats;

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
