package com.qbb.model;

import java.util.List;

public class SeatRegion {
    private boolean canSell;
    private int columnSize;
    private boolean forbid;
    private String regionId;
    private String regionName;
    private int rowSize;
    private List<Row> rows;

    public void setCanSell(boolean canSell) {
        this.canSell = canSell;
    }

    public boolean getCanSell() {
        return canSell;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setForbid(boolean forbid) {
        this.forbid = forbid;
    }

    public boolean getForbid() {
        return forbid;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    public int getRowSize() {
        return rowSize;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public List<Row> getRows() {
        return rows;
    }
}
