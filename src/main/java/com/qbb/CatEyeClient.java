package com.qbb;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ejlchina.okhttps.OkHttps;
import com.qbb.model.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class CatEyeClient {
    List<Film> films;

    public JSONObject hot() {
        return OkHttps.sync("https://i.maoyan.com/api/mmdb/movie/v3/list/hot.json")
                .addUrlPara("ct", "西安")
                .addUrlPara("ci", "42")
                .addUrlPara("channelId", "4")
                .addHeader("cookie", "_lxsdk_cuid=187efb60d83c8-0820e17edd68a2-7e57547f-1fa400-187efb60d83c8; ci=42%2C%E8%A5%BF%E5%AE%89; ci.sig=PZGBSjtuxYBxy3RpGtyqcExoliU; uuid_n_v=v1; iuuid=8D64EE90EE0611EDB227EF36682DB7BBA6ED20326E3F431EBF6A15979AD939F0; latlng=34.31747279553577%2C108.94261863239772%2C1683601804282; _lx_utm=utm_source%3Dbing%26utm_medium%3Dorganic; _lxsdk=CADB8960EBD511EDB1C0EF0226805F3B0589908ADD1B46BEB437FFC710BDFB48; _lxsdk_s=187fe750299-c83-855-626%7C%7C49; featrues=[object Object]; featrues.sig=KbQquuOrr42L3kMHbtKc319ems8")
                .addHeader("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1 Edg/113.0.0.0")
                .get().getBody().toBean(JSONObject.class).getJSONObject("data");
    }

    public List<Film> moreComingList(List<Long> list) {
        List<Film> objects = new ArrayList<>();
        int batchSize = 10;  // 每组元素的大小
        int totalSize = list.size();  // 集合总大小
        for (int i = 0; i < totalSize; i += batchSize) {
            int end = Math.min(i + batchSize, totalSize);  // 如果下标超出集合大小处理
            List<Long> ids = list.subList(i, end);  // 从集合中获取子列表
            JSONObject result = OkHttps.sync("https://i.maoyan.com/ajax/moreComingList")
                    .addUrlPara("token", "")
                    .addUrlPara("optimus_uuid", UUID.randomUUID().toString())
                    .addUrlPara("optimus_risk_level", "71")
                    .addUrlPara("optimus_code", "10")
                    .addUrlPara("movieIds", ids.stream().map(Objects::toString).collect(Collectors.joining(",")))
                    .addHeader("cookie", "_lxsdk_cuid=187efb60d83c8-0820e17edd68a2-7e57547f-1fa400-187efb60d83c8; ci=42%2C%E8%A5%BF%E5%AE%89; ci.sig=PZGBSjtuxYBxy3RpGtyqcExoliU; uuid_n_v=v1; iuuid=8D64EE90EE0611EDB227EF36682DB7BBA6ED20326E3F431EBF6A15979AD939F0; latlng=34.31747279553577%2C108.94261863239772%2C1683601804282; _lx_utm=utm_source%3Dbing%26utm_medium%3Dorganic; _lxsdk=CADB8960EBD511EDB1C0EF0226805F3B0589908ADD1B46BEB437FFC710BDFB48; _lxsdk_s=187fe750299-c83-855-626%7C%7C49; featrues=[object Object]; featrues.sig=KbQquuOrr42L3kMHbtKc319ems8")
                    .addHeader("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1 Edg/113.0.0.0")
                    .get().getBody().toBean(JSONObject.class);
            objects.addAll(JSONArray.parseArray(result.getString("coming"), Film.class));
        }
        return objects;
    }

    public CatEyeClient() {
        init();
    }

    private void init() {
        films = getAllFilms();
    }

    private List<Film> getAllFilms() {
        JSONObject hot = hot();
        List<Film> films = JSONArray.parseArray(hot.getString("hot"), Film.class);
        List<Long> movieIds = hot.getJSONArray("movieIds")
                .stream().map(o -> Long.parseLong(o.toString()))
                .collect(Collectors.toList());
        List<Long> existIds = films.stream().map(Film::getId).collect(Collectors.toList());
        List<Long> notExistIds = movieIds.stream().filter(i -> !existIds.contains(i)).collect(Collectors.toList());
        films.addAll(moreComingList(notExistIds));
        return films;
    }

    public static void main(String[] args) {
        CatEyeClient client = new CatEyeClient();
        System.out.println(client.getAllFilms());
    }
}
