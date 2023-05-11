package com.qbb;


import cn.hutool.core.lang.ConsoleTable;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.Table;
import com.alibaba.fastjson.JSONObject;
import com.qbb.model.*;

import java.util.*;

public class Main {
    CatEyeClient catEyeClient = new CatEyeClient(CatEyeClient.REMOTE);
    Scanner scanner = new Scanner(System.in);

    void showInfo() {
        System.out.println("请选择影片：");
        List<Film> films = catEyeClient.getFilms();
        int index = 1;
        for (Film film : films) {
            StringJoiner joiner = new StringJoiner("\t |\t");
            joiner.add(index + "");
            joiner.add(film.getId() + "");
            joiner.add(film.getNm() + (film.getPreShow() ? "(预售)" : ""));
            System.out.println(joiner);
            index++;
        }


        int choice;
        while (true) {
            System.out.print("请输入序号选择电影:");
            choice = scanner.nextInt();

            // 验证输入序号的合法性
            if (choice >= 1 && choice <= films.size()) {
                break;  // 输入合法，退出循环
            }

            System.out.println("未找到对应的电影，请重新选择序号。");
        }

        // 根据用户选择的序号来处理对应的电影
        Film chosenFilm = films.get(choice - 1);
        System.out.println("您选择的电影是: " + chosenFilm.getNm());


//        CatEyeClient client = new CatEyeClient();  // 创建 CatEyeClient 对象


        // 让用户选择一个电影，并获取其ID（这里假设用户选择了第一个电影）
        Long movieId = chosenFilm.getId();

        // 获取电影的放映日期列表
        List<String> dates = catEyeClient.showDays(movieId);

        // 让用户选择一个放映日期，并获取其值（这里假设用户选择了第一个放映日期）
        String showDate = dates.get(0);
        // 获取电影的影院信息
        List<Cinema> cinemas = catEyeClient.cinemas(movieId, showDate);
        System.out.println("以下是电影: '" + chosenFilm.getNm() + "' 的场次信息: ");

        // 遍历影院列表
        int count = 1;
        for (Cinema cinema : cinemas) {
            System.out.println("场次序号：" + count);
            System.out.println("影院名称：" + cinema.getName());
            System.out.println("影院地址：" + cinema.getAddr());
            count++;

            // 获取电影的场次信息
            List<MoviePList> moviePLists = cinema.showTimes(showDate, movieId);

            for (MoviePList moviePList : moviePLists) {

                System.out.println("放映时间：" + moviePList.getTm());
                System.out.println("影厅：" + moviePList.getTh());
                System.out.println("票价：" + moviePList.getVipPrice());
                System.out.println();
                // 其他场次信息的输出...

            }
            MoviePList moviePList = moviePLists.get(0);
            SeatRegion seats = moviePList.getSeats();
//            System.out.println(JSONObject.toJSONString(seats));
            System.out.println(seats.getRegionName() + " \t □未售 \t ■已售 \t ▧不可售");
            HashMap<String, String> map = new HashMap<>();
            for (Row row : seats.getRows()) {
                for (Seat seat : row.getSeats()) {
                    map.put(seat.getNo(), seat.getSeatNo());
                    if (seat.getSeatNo().isEmpty()) {// 不是座位
                        System.out.print("\t\t");
                        continue;
                    }
                    if (seat.getSeatStatus() == 1) { //可售
                        System.out.print("\t\t□");
                    } else if (seat.getSeatStatus() == 3) { //已售
                        System.out.print("\t\t■");
                    } else if (seat.getSeatStatus() == 4) { //不可收
                        System.out.print("\t\t▧");
                    } else {
                        System.out.print("\t\t");
                    }
                }
                System.out.println();
                for (Seat seat : row.getSeats()) {
                    if (seat.getSeatNo().isEmpty()) {
                        System.out.print("\t\t");
                        continue;
                    }
                    System.out.print("\t  " + seat.getNo());

                }
                System.out.println();
            }
            System.out.print("请输入座位号(多个;分隔):");
            String selectedSeats = scanner.next();
            System.out.println();
            System.out.println("您选的是:");
            for (String key : selectedSeats.split(";")) {
                System.out.println(map.get(key));
            }
            break;

        }

    }

    public static void main(String[] args) {
        Main main = new Main();
        main.showInfo();

    }
}
