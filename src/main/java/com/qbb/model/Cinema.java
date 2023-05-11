package com.qbb.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ejlchina.okhttps.HTTP;
import com.qbb.CatEyeClient;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cinema {
    /*{
    "addr":"未央区正则路开元路十字西南角花田里街区东区3F",
    "distance":"5.0km",
    "id":36959,
    "labels":"退,退",
    "lat":34.35081,
    "lng":108.95306,
    "mark":0,
    "name":"花田大戏院",
    "poiId":1922170380,
    "referencePrice":"0",
    "sellPrice":"29.9",
    "shopId":1922170380,
    "showTimes":"近期场次: 22:10 | 23:10"
}*/
    private String addr;
    private String distance;
    private int id;
    private String labels;
    private double lat;
    private double lng;
    private int mark;
    private String name;
    private long poiId;
    private String referencePrice;
    private String sellPrice;
    private long shopId;
    private String showTimes;

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddr() {
        return addr;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDistance() {
        return distance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setLabels(String labels) {
        String pattern = "\"name\":\\s*\"([^\"]+)\"";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(labels);
        StringBuilder sb = new StringBuilder();
        while (m.find()) {
            sb.append(m.group(1)).append(",");
        }
        this.labels = sb.toString().replaceAll(",$", "");
    }


    public String getLabels() {
        return labels;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLat() {
        return lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLng() {
        return lng;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getMark() {
        return mark;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPoiId(long poiId) {
        this.poiId = poiId;
    }

    public long getPoiId() {
        return poiId;
    }

    public void setReferencePrice(String referencePrice) {
        this.referencePrice = referencePrice;
    }

    public String getReferencePrice() {
        return referencePrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShowTimes(String showTimes) {
        this.showTimes = showTimes;
    }

    public String getShowTimes() {
        return showTimes;
    }

    public List<Movie> movies;

    public List<Movie> show() {
        if (movies == null) {
            HTTP http = CatEyeClient.http;
            JSONObject object = http.async("https://m.maoyan.com/mtrade/cinema/cinema/shows.json")
                    .addUrlPara("cinemaId", this.id)
                    .addUrlPara("ci", "42")
                    .addUrlPara("channelId", "4")
                    .get().getResult().getBody().toBean(JSONObject.class);
            JSONObject data = object.getJSONObject("data");
            this.movies = JSONArray.parseArray(data.getString("movies"), Movie.class);
        }
        return movies;
    }

    public List<MoviePList> showTimes(String date, long movieId) {
        Movie movie = show().stream().filter(m -> m.getId() == movieId).findFirst().orElse(null);
        if (movie != null) {
            List<MovieShow> shows = movie.getShows();
            MovieShow movieShow = shows.stream().filter(show -> show.getShowDate().equalsIgnoreCase(date)).findFirst().orElse(null);
            if (movieShow != null) {
                return movieShow.getPlist();
            }
        }
        return null;
    }
}
