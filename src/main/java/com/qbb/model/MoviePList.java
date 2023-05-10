package com.qbb.model;

import com.alibaba.fastjson.JSONObject;
import com.ejlchina.okhttps.HTTP;
import com.qbb.CatEyeClient;

import java.util.Date;
import java.util.List;

public class MoviePList {

    private String baseSellPrice;
    private boolean dimHighLight;
    private Date dt;
    private int enterShowSeat;
    private String extraDesc;
    private String extraDescNew;
    private String forbiddenTip;
    private boolean hallTypeHighLight;
    private List<String> labelResource;
    private String lang;
    private boolean languageHighLight;
    private String packShowTag;
    private String preShowTag;
    private int reservedMin;
    private String saleDate;
    private String saleTimeText;
    private String sellPr;
    private String sellPrSuffix;
    private String seqNo;
    private int showClosedSeat;
    private String showTag;
    private boolean showTypeHighLight;
    private String th;
    private boolean theatreSaleTimeTag;
    private int ticketStatus;
    private String ticketStatusContent;
    private String tm;
    private String tp;
    private String vipPrice;
    private String vipPriceName;
    private String vipPriceNameNew;
    private String vipPriceSuffix;
    private String zeroFlag;
    private boolean smallFont;
    private boolean displayOriginalPr;

    public void setBaseSellPrice(String baseSellPrice) {
        this.baseSellPrice = baseSellPrice;
    }

    public String getBaseSellPrice() {
        return baseSellPrice;
    }

    public void setDimHighLight(boolean dimHighLight) {
        this.dimHighLight = dimHighLight;
    }

    public boolean getDimHighLight() {
        return dimHighLight;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Date getDt() {
        return dt;
    }

    public void setEnterShowSeat(int enterShowSeat) {
        this.enterShowSeat = enterShowSeat;
    }

    public int getEnterShowSeat() {
        return enterShowSeat;
    }

    public void setExtraDesc(String extraDesc) {
        this.extraDesc = extraDesc;
    }

    public String getExtraDesc() {
        return extraDesc;
    }

    public void setExtraDescNew(String extraDescNew) {
        this.extraDescNew = extraDescNew;
    }

    public String getExtraDescNew() {
        return extraDescNew;
    }

    public void setForbiddenTip(String forbiddenTip) {
        this.forbiddenTip = forbiddenTip;
    }

    public String getForbiddenTip() {
        return forbiddenTip;
    }

    public void setHallTypeHighLight(boolean hallTypeHighLight) {
        this.hallTypeHighLight = hallTypeHighLight;
    }

    public boolean getHallTypeHighLight() {
        return hallTypeHighLight;
    }

    public void setLabelResource(List<String> labelResource) {
        this.labelResource = labelResource;
    }

    public List<String> getLabelResource() {
        return labelResource;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    public void setLanguageHighLight(boolean languageHighLight) {
        this.languageHighLight = languageHighLight;
    }

    public boolean getLanguageHighLight() {
        return languageHighLight;
    }

    public void setPackShowTag(String packShowTag) {
        this.packShowTag = packShowTag;
    }

    public String getPackShowTag() {
        return packShowTag;
    }

    public void setPreShowTag(String preShowTag) {
        this.preShowTag = preShowTag;
    }

    public String getPreShowTag() {
        return preShowTag;
    }

    public void setReservedMin(int reservedMin) {
        this.reservedMin = reservedMin;
    }

    public int getReservedMin() {
        return reservedMin;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleTimeText(String saleTimeText) {
        this.saleTimeText = saleTimeText;
    }

    public String getSaleTimeText() {
        return saleTimeText;
    }

    public void setSellPr(String sellPr) {
        this.sellPr = sellPr;
    }

    public String getSellPr() {
        return sellPr;
    }

    public void setSellPrSuffix(String sellPrSuffix) {
        this.sellPrSuffix = sellPrSuffix;
    }

    public String getSellPrSuffix() {
        return sellPrSuffix;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setShowClosedSeat(int showClosedSeat) {
        this.showClosedSeat = showClosedSeat;
    }

    public int getShowClosedSeat() {
        return showClosedSeat;
    }

    public void setShowTag(String showTag) {
        this.showTag = showTag;
    }

    public String getShowTag() {
        return showTag;
    }

    public void setShowTypeHighLight(boolean showTypeHighLight) {
        this.showTypeHighLight = showTypeHighLight;
    }

    public boolean getShowTypeHighLight() {
        return showTypeHighLight;
    }

    public void setTh(String th) {
        this.th = th;
    }

    public String getTh() {
        return th;
    }

    public void setTheatreSaleTimeTag(boolean theatreSaleTimeTag) {
        this.theatreSaleTimeTag = theatreSaleTimeTag;
    }

    public boolean getTheatreSaleTimeTag() {
        return theatreSaleTimeTag;
    }

    public void setTicketStatus(int ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public int getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatusContent(String ticketStatusContent) {
        this.ticketStatusContent = ticketStatusContent;
    }

    public String getTicketStatusContent() {
        return ticketStatusContent;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getTm() {
        return tm;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getTp() {
        return tp;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getVipPrice() {
        return vipPrice;
    }

    public void setVipPriceName(String vipPriceName) {
        this.vipPriceName = vipPriceName;
    }

    public String getVipPriceName() {
        return vipPriceName;
    }

    public void setVipPriceNameNew(String vipPriceNameNew) {
        this.vipPriceNameNew = vipPriceNameNew;
    }

    public String getVipPriceNameNew() {
        return vipPriceNameNew;
    }

    public void setVipPriceSuffix(String vipPriceSuffix) {
        this.vipPriceSuffix = vipPriceSuffix;
    }

    public String getVipPriceSuffix() {
        return vipPriceSuffix;
    }

    public void setZeroFlag(String zeroFlag) {
        this.zeroFlag = zeroFlag;
    }

    public String getZeroFlag() {
        return zeroFlag;
    }

    public void setSmallFont(boolean smallFont) {
        this.smallFont = smallFont;
    }

    public boolean getSmallFont() {
        return smallFont;
    }

    public void setDisplayOriginalPr(boolean displayOriginalPr) {
        this.displayOriginalPr = displayOriginalPr;
    }

    public boolean getDisplayOriginalPr() {
        return displayOriginalPr;
    }

    public void getSeats() {
        HTTP http = CatEyeClient.http;
        JSONObject object = http.sync("https://m.maoyan.com/api/mtrade/seat/v8/show/seats.json")
                .bodyType("json")
                .addUrlPara("ts", System.currentTimeMillis() + "")
                .addUrlPara("seqNo", "202305100585574")
                .addUrlPara("fingerprint", "")
                .addUrlPara("userid", "")
                .addUrlPara("ci", "42")
                .addUrlPara("channelId", "4")
                .addUrlPara("version_name", "2.0.0")
                .addHeader("Origin", "https://m.maoyan.com")
                .addHeader("x-ta", "1")
                .addHeader("token", "AgEtIyS_pXSqhR_8DiTTIIDotqkYOb-kh2vlusWLN-wVjz82Hhk6hvIeF-w7EkIhdajR2ZYqus9cCgAAAAAtGAAAKYtjL-1xpeb4eBdkfEemNOodBHWx9tO6GoMDK9W0_4H93EOI1a5XrfWgZLMOpYaf")
                .post().getBody().toBean(JSONObject.class);

    }
}
