package com.qbb.model;

import java.util.List;

public class Movie {

    private String chiefBonus;
    private String desc;
    private int dur;
    private boolean globalReleased;
    private long id;
    private String img;
    private List<String> labelResource;
    private String nm;
    private int preferential;
    private String preferentialTag;
    private String sc;
    private int showCount;
    private List<MovieShow> shows;
    private long wish;

    public void setChiefBonus(String chiefBonus) {
        this.chiefBonus = chiefBonus;
    }

    public String getChiefBonus() {
        return chiefBonus;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDur(int dur) {
        this.dur = dur;
    }

    public int getDur() {
        return dur;
    }

    public void setGlobalReleased(boolean globalReleased) {
        this.globalReleased = globalReleased;
    }

    public boolean getGlobalReleased() {
        return globalReleased;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setLabelResource(List<String> labelResource) {
        this.labelResource = labelResource;
    }

    public List<String> getLabelResource() {
        return labelResource;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getNm() {
        return nm;
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

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getSc() {
        return sc;
    }

    public void setShowCount(int showCount) {
        this.showCount = showCount;
    }

    public int getShowCount() {
        return showCount;
    }

    public void setShows(List<MovieShow> shows) {
        this.shows = shows;
    }

    public List<MovieShow> getShows() {
        return shows;
    }

    public void setWish(long wish) {
        this.wish = wish;
    }

    public long getWish() {
        return wish;
    }

}
