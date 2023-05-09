package com.qbb.model;

import java.util.Date;

public class Film {

    private long id;
    private boolean haspromotionTag;
    private String img;
    private String version;
    private String nm;
    private boolean preShow;
    private int sc;
    private boolean globalReleased;
    private int wish;
    private String star;
    private Date rt;
    private String showInfo;
    private int showst;
    private int wishst;
    private String comingTitle;
    private ShowStateButton showStateButton;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setHaspromotionTag(boolean haspromotionTag) {
        this.haspromotionTag = haspromotionTag;
    }

    public boolean getHaspromotionTag() {
        return haspromotionTag;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getNm() {
        return nm;
    }

    public void setPreShow(boolean preShow) {
        this.preShow = preShow;
    }

    public boolean getPreShow() {
        return preShow;
    }

    public void setSc(int sc) {
        this.sc = sc;
    }

    public int getSc() {
        return sc;
    }

    public void setGlobalReleased(boolean globalReleased) {
        this.globalReleased = globalReleased;
    }

    public boolean getGlobalReleased() {
        return globalReleased;
    }

    public void setWish(int wish) {
        this.wish = wish;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", haspromotionTag=" + haspromotionTag +
                ", img='" + img + '\'' +
                ", version='" + version + '\'' +
                ", nm='" + nm + '\'' +
                ", preShow=" + preShow +
                ", sc=" + sc +
                ", globalReleased=" + globalReleased +
                ", wish=" + wish +
                ", star='" + star + '\'' +
                ", rt=" + rt +
                ", showInfo='" + showInfo + '\'' +
                ", showst=" + showst +
                ", wishst=" + wishst +
                ", comingTitle='" + comingTitle + '\'' +
                ", showStateButton=" + showStateButton +
                '}';
    }

    public int getWish() {
        return wish;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getStar() {
        return star;
    }

    public void setRt(Date rt) {
        this.rt = rt;
    }

    public Date getRt() {
        return rt;
    }

    public void setShowInfo(String showInfo) {
        this.showInfo = showInfo;
    }

    public String getShowInfo() {
        return showInfo;
    }

    public void setShowst(int showst) {
        this.showst = showst;
    }

    public int getShowst() {
        return showst;
    }

    public void setWishst(int wishst) {
        this.wishst = wishst;
    }

    public int getWishst() {
        return wishst;
    }

    public void setComingTitle(String comingTitle) {
        this.comingTitle = comingTitle;
    }

    public String getComingTitle() {
        return comingTitle;
    }

    public void setShowStateButton(ShowStateButton showStateButton) {
        this.showStateButton = showStateButton;
    }

    public ShowStateButton getShowStateButton() {
        return showStateButton;
    }
}
