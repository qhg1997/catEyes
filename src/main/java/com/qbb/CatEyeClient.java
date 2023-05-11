package com.qbb;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ejlchina.okhttps.*;
import com.ejlchina.okhttps.fastjson.FastjsonMsgConvertor;
import com.qbb.model.Cinema;
import com.qbb.model.Film;
import com.qbb.model.Movie;
import com.qbb.model.MoviePList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class CatEyeClient {
    public static final HTTP http = HTTP.builder()
            .addMsgConvertor(new FastjsonMsgConvertor())
            .addPreprocessor((Preprocessor.PreChain chain) -> {
                chain.getTask()
                        .addHeader("cookie", "_lxsdk_cuid=187fa7ec200c8-0d7c665f56865a-4c657b58-186a00-187fa7ec201c8; WEBDFPID=x2u1zwz0xz72599015xuwux71u21u028812uz2u798y979585wu1wv85-1998894848645-1683534847759QKMAMKOfd79fef3d01d5e9aadc18ccd4d0c95077420; token=AgEjJDwqpCdWfeKXSKzjpXMVMCW6PVi4iVxd9ewEOkhN5IWU1aNqCYY2CnLjaxpoZOTqrleFyuuc-wAAAAAtGAAA1tVd0ppuaJk_zgkhz2GuqFZP8WIQ3sFJY3LMydO00s89IYo6c1MlZsBv_DTHmSLk; _lxsdk=0A429750ED7B11EDB1C0EF0226805F3BE6B929DAE004438DA622D81AA03CC431; Hm_lvt_703e94591e87be68cc8da0da7cbd0be2=1683534824,1683536969; _lx_utm=utm_source%3Dbing%26utm_medium%3Dorganic")
                        .addHeader("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1 Edg/113.0.0.0");
                chain.proceed();                // 继续当前的任务
            })
            .build();


    public static final COORDINATE COORDINATE = new COORDINATE("34.237752,109.074925");//坐标
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
            JSONObject result = http.sync("https://i.maoyan.com/ajax/moreComingList")
                    .addUrlPara("token", "")
                    .addUrlPara("optimus_uuid", UUID.randomUUID().toString())
                    .addUrlPara("optimus_risk_level", "71")
                    .addUrlPara("optimus_code", "10")
                    .addUrlPara("movieIds", ids.stream().map(Objects::toString).collect(Collectors.joining(",")))
                    .get().getBody().toBean(JSONObject.class);
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
                System.out.println("需要过一下滑块 https://www.maoyan.com/");
                e.printStackTrace();
                //返回 data.json 文件
                return readLocal();
            }
    }

    public static void main(String[] args) {
        CatEyeClient client = new CatEyeClient();
        System.out.println(client.getFilms());  // 获取热门电影
        Long movieId = client.getFilms().get(0).getId();
        List<String> dates = client.showDays(movieId);
        System.out.println(dates);
        String showDate = dates.get(0);
        List<Cinema> cinemas = client.cinemas(movieId, showDate);
//        List<Movie> movies = cinemas.get(0).show();
        System.out.println(JSONObject.toJSONString(cinemas));
        List<MoviePList> moviePLists = cinemas.get(0).showTimes(showDate, movieId);
        System.out.println(JSONObject.toJSONString(moviePLists));
//        moviePLists.get(0).getSeats();
    }

    public List<Cinema> cinemas(Long movieId, String showDate) {
        JSONObject object = http.async("https://m.maoyan.com/api/mtrade/mmcs/cinema/v2/select/movie/cinemas.json")
                .addUrlPara("movieId", movieId)
                .addUrlPara("movieId", showDate)
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
