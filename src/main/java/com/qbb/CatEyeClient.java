package com.qbb;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ejlchina.okhttps.*;
import com.ejlchina.okhttps.fastjson.FastjsonMsgConvertor;
import com.qbb.model.*;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class CatEyeClient {
    public static final COORDINATE COORDINATE = new COORDINATE("34.237752,109.074925");//坐标
    static final String TOKEN = "AgGPIaAMFUWcBOz-4WDDisajG1WdVupouNI4mGRLIJbZzAwAh2bgmtjaJkBac4ZJ784YTlrm7DvkOAAAAAAtGAAAWQ-3lW9loZGT9CY07iQZn2DBdIhvAtT8WQo5DvBb2c1WOK31AvMbqBhpa-5zTg2H";
    static final String COOKIE = "_lxsdk_cuid=187efb60d83c8-0820e17edd68a2-7e57547f-1fa400-187efb60d83c8; ci=42%2C%E8%A5%BF%E5%AE%89; ci.sig=PZGBSjtuxYBxy3RpGtyqcExoliU; uuid_n_v=v1; iuuid=8D64EE90EE0611EDB227EF36682DB7BBA6ED20326E3F431EBF6A15979AD939F0; WEBDFPID=93yv9y49w9375u2x1yw1848277w6v621812uuz071z39795871xxz68x-1998981488832-1683621488099WEOYWAGfd79fef3d01d5e9aadc18ccd4d0c95078855; ci=42%2C%E8%A5%BF%E5%AE%89; ci=42%2C%E8%A5%BF%E5%AE%89; selectci=true; selectci=true; selectci.sig=kdh2HVdzxNAXQp-kYQgChk2a2rw; _lx_utm=utm_source%3Dbing%26utm_medium%3Dorganic; featrues=[object Object]; featrues.sig=KbQquuOrr42L3kMHbtKc319ems8; uid=827044556; _last_page=c_dmLad; latlng=34.31724942269264%2C108.94262313449691%2C1683876547783; _lxsdk=CADB8960EBD511EDB1C0EF0226805F3B0589908ADD1B46BEB437FFC710BDFB48; token=AgGPIaAMFUWcBOz-4WDDisajG1WdVupouNI4mGRLIJbZzAwAh2bgmtjaJkBac4ZJ784YTlrm7DvkOAAAAAAtGAAAWQ-3lW9loZGT9CY07iQZn2DBdIhvAtT8WQo5DvBb2c1WOK31AvMbqBhpa-5zTg2H; _lxsdk_s=1880e7f0049-b16-a5d-69c%7C%7C779; token.sig=BmONIPj9V-21RCuiElX5ahnH59w; user=827044556%2CAgGPIaAMFUWcBOz-4WDDisajG1WdVupouNI4mGRLIJbZzAwAh2bgmtjaJkBac4ZJ784YTlrm7DvkOAAAAAAtGAAAWQ-3lW9loZGT9CY07iQZn2DBdIhvAtT8WQo5DvBb2c1WOK31AvMbqBhpa-5zTg2H";
    public static final HTTP http = HTTP.builder()
            .addMsgConvertor(new FastjsonMsgConvertor())
            .addPreprocessor((Preprocessor.PreChain chain) -> {
                chain.getTask()
                        .addHeader("x-ta", "1")
                        .addHeader("Origin", "https://m.maoyan.com")
                        .addHeader("token", TOKEN)
                        .addHeader("cookie", COOKIE)
                        .addHeader("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1 Edg/113.0.0.0");
                chain.proceed();                // 继续当前的任务
            })
            .build();


    public static final String LOCAL = "local";
    public static final String REMOTE = "remote";
    private List<Film> films;
    private String readMode = LOCAL;

    private JSONObject hot() {
        return http.sync("https://i.maoyan.com/api/mmdb/movie/v3/list/hot.json")
                .addUrlPara("ct", "西安")
                .addUrlPara("ci", "42")
                .addUrlPara("channelId", "4")
                .get().getBody().toBean(JSONObject.class).getJSONObject("data");
    }

    private List<Film> moreComingList(List<Long> list) {
        List<Film> objects = new ArrayList<>();
        int batchSize = 10;  // 每组元素的大小
        int totalSize = list.size();  // 集合总大小
        for (int i = 0; i < totalSize; i += batchSize) {
            int end = Math.min(i + batchSize, totalSize);  // 如果下标超出集合大小处理
            List<Long> ids = list.subList(i, end);  // 从集合中获取子列表
            HttpResult.Body body = http.sync("https://i.maoyan.com/ajax/moreComingList")
                    .addUrlPara("token", TOKEN)
                    .addUrlPara("optimus_uuid", UUID.randomUUID().toString())
                    .addUrlPara("optimus_risk_level", "71")
                    .addUrlPara("optimus_code", "10")
                    .addUrlPara("movieIds", ids.stream().map(Objects::toString).collect(Collectors.joining(",")))
                    .get().getBody().cache();
//            System.out.println("body: " + body.toString());
            JSONObject result = body.toBean(JSONObject.class);
            objects.addAll(JSONArray.parseArray(result.getString("coming"), Film.class));
        }
        return objects;
    }

    public CatEyeClient() {
        init();
    }

    public CatEyeClient(String readMode) {
        this.readMode = readMode;
        init();
    }

    private void init() {
        films = getAllFilms();
    }

    private List<Film> readLocal() {
        System.out.println("读本地文件");
        return JSONArray.parseArray(IO.readContentAsString(new File("data.json")), Film.class);
    }


    private List<Film> getAllFilms() {
        if (readMode.equals(LOCAL)) {
            return readLocal();
        } else
            try {
                //尝试获取新数据 获取失败 就读取本地 data.json 文件
                JSONObject hot = hot();
                List<Film> films = JSONArray.parseArray(hot.getString("hot"), Film.class);
                List<Long> movieIds = hot.getJSONArray("movieIds")
                        .stream().map(o -> Long.parseLong(o.toString()))
                        .collect(Collectors.toList());
                List<Long> existIds = films.stream().map(Film::getId).collect(Collectors.toList());
                List<Long> notExistIds = movieIds.stream().filter(i -> !existIds.contains(i)).collect(Collectors.toList());
                films.addAll(moreComingList(notExistIds));
                System.out.println("网络请求获取");
                return films;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("需要过一下滑块 https://www.maoyan.com/");
            }
        return films;
    }

    public static void main(String[] args) {
    }

    public List<Cinema> cinemas(Long movieId, String showDate) {
        JSONObject object = http.async("https://m.maoyan.com/api/mtrade/mmcs/cinema/v2/select/movie/cinemas.json")
                .addUrlPara("movieId", movieId)
                .addUrlPara("showDate", showDate)
                .addUrlPara("channelId", "4")
                .addUrlPara("limit", "20")
                .addUrlPara("offset", "0")
                .addUrlPara("utm_term", "7.5")
                .addUrlPara("client", "iphone")
                .addUrlPara("channelId", "4")
                .addUrlPara("sort", "distance")
                .addUrlPara("cityId", "42")
                .addUrlPara("ci", "42")
                .addUrlPara("lat", CatEyeClient.COORDINATE.LAT)
                .addUrlPara("lng", CatEyeClient.COORDINATE.LNG)
                .addUrlPara("districtId", "-1")
                .addUrlPara("lineId", "-1")
                .addUrlPara("areaId", "-1")
                .addUrlPara("stationId", "-1")
                .get().getResult().getBody().toBean(JSONObject.class);
        JSONObject data = object.getJSONObject("data");
        return JSONArray.parseArray(data.getString("cinemas"), Cinema.class);
    }

    public List<String> showDays(Long movieId) {
        JSONObject object = http.async("https://m.maoyan.com/api/mtrade/mmcs/show/v1/movie/showdays.json")
                .addUrlPara("movieId", movieId)
                .addUrlPara("channelId", "4")
                .addUrlPara("cityId", "42")
                .get().getResult().getBody().toBean(JSONObject.class);
        JSONArray dates = object.getJSONObject("data").getJSONArray("dates");
        return dates.stream().map(o -> JSONObject.parseObject(JSONObject.toJSONString(o)).getString("date")).collect(Collectors.toList());
    }

    public List<Film> getFilms() {
        return films;
    }

    public String create(String seqNo, List<Seat> seats) {
        JSONObject object = http.sync("https://m.maoyan.com/api/mtrade/createorder/v14/create.json")
                .bodyType("form")
                .addBodyPara("realNameMethod", "1")
                .addBodyPara("channelId", "4")
                .addBodyPara("clientType", "touch")
                .addBodyPara("seqNo", seqNo)
                .addBodyPara("seats", packageJson(seats))
                .addBodyPara("h5Fingerprint", getH5Fingerprint())
                .addBodyPara("location", "{\"latitude\":\"" + COORDINATE.LAT + "\",\"longitude\":\"" + COORDINATE.LNG + "\"}")
                .addBodyPara("deviceInfoByQQ", "{\"location\":{\"latitude\":\"" + COORDINATE.LAT + "\",\"longitude\":\"" + COORDINATE.LNG + "\"},\"identityInfo\":{\"openid\":\"\"}}")
                .post().getBody().toBean(JSONObject.class);
        String orderId = object.getJSONObject("data").getString("id");
        return "https://m.maoyan.com/mtrade/order/buy?%24from=canary&isfromlist=1&token=" + TOKEN + "&orderId=" + orderId + "&ci=42";
    }

    private String getH5Fingerprint() {
        return "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";
    }

    private String packageJson(List<Seat> seats) {
        JSONObject object = new JSONObject();
        object.put("count", seats.size());
        object.put("list", seats);
        return JSONObject.toJSONString(object);
    }

    private static class COORDINATE {
        String LAT;
        String LNG;

        public COORDINATE(String coordinate) {
            String[] arr = coordinate.split(",");
            this.LAT = arr[0];
            this.LNG = arr[1];
        }
    }
}
