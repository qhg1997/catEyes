package com.qbb.model;

public class Seat {

    private String columnId;
    private String rowId;
    private String seatNo;
    private int seatStatus;
    private String seatType;
    private String sectionId;

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getRowId() {
        return rowId;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatStatus(int seatStatus) {
        this.seatStatus = seatStatus;
    }

    public int getSeatStatus() {
        return seatStatus;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public String getNo() {

        return this.rowId.isEmpty() || this.columnId.isEmpty() ? "" : String.format("%02d", Integer.valueOf(this.getRowId())) + ":" + String.format("%02d", Integer.valueOf(this.getColumnId()));
    }
}
