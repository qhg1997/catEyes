package com.qbb.model;

import java.sql.Date;
import java.util.List;

public class MovieShow {

    private int hasShow;
    private List<String> labelResource;
    private List<MoviePList> plist;
    private List<String> preInfo;
    private int preferential;
    private String preferentialTag;
    private String showDate;

    public void setHasShow(int hasShow) {
        this.hasShow = hasShow;
    }

    public int getHasShow() {
        return hasShow;
    }

    public void setLabelResource(List<String> labelResource) {
        this.labelResource = labelResource;
    }

    public List<String> getLabelResource() {
        return labelResource;
    }

    public void setPlist(List<MoviePList> plist) {
        this.plist = plist;
    }

    public List<MoviePList> getPlist() {
        return plist;
    }

    public void setPreInfo(List<String> preInfo) {
        this.preInfo = preInfo;
    }

    public List<String> getPreInfo() {
        return preInfo;
    }

    public void setPreferential(int preferential) {
        this.preferential = preferential;
    }

    public int getPreferential() {
        return preferential;
    }

    public void setPreferentialTag(String preferentialTag) {
        this.preferentialTag = preferentialTag;
    }

    public String getPreferentialTag() {
        return preferentialTag;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getShowDate() {
        return showDate;
    }

}
