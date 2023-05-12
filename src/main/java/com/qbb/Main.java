package com.qbb;

import com.alibaba.fastjson.JSONObject;
import com.qbb.model.*;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;

public class Main {
    CatEyeClient catEyeClient = new CatEyeClient(CatEyeClient.REMOTE);
    Scanner scanner = new Scanner(System.in);

    void showInfo() {
        System.out.println("请选择影片: ");
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
            System.out.println();
            System.out.print("请输入序号选择电影: ");
            choice = scanner.nextInt();

            // 验证输入序号的合法性
            if (choice >= 1 && choice <= films.size()) {
                break;  // 输入合法，退出循环
            }

            System.out.println("未找到对应的电影，请重新选择序号。");
        }

        // 根据用户选择的序号来处理对应的电影
        Film chosenFilm = films.get(choice - 1);
        System.out.println();
        System.out.println("您选择的电影是: " + chosenFilm.getNm());
        System.out.println();


        // 获取电影的放映日期列表
        Long movieId = chosenFilm.getId();
        List<String> dates = catEyeClient.showDays(movieId);

        System.out.println("以下是电影: '" + chosenFilm.getNm() + "' 的放映日期列表: ");
        System.out.println();
        int dateIndex = 1;
        for (String date : dates) {
            System.out.println(dateIndex + ". " + date);
            dateIndex++;
        }

        int dateChoice;
        while (true) {
            System.out.println();
            System.out.print("请输入序号选择放映日期: ");
            dateChoice = scanner.nextInt();

            // 验证输入序号的合法性
            if (dateChoice >= 1 && dateChoice <= dates.size()) {
                break;  // 输入合法，退出循环
            }

            System.out.println("未找到对应的放映日期，请重新选择序号。");
        }

        // 根据用户选择的序号来处理对应的放映日期
        String chosenDate = dates.get(dateChoice - 1);
        System.out.println();
        System.out.print("您选择的放映日期是: " + chosenDate);


        // 输出对应电影的影院名称和地址
        System.out.println("以下是电影: '" + chosenFilm.getNm() + "' 在日期: '" + chosenDate + "' 的影院信息: ");
        System.out.println();
        List<Cinema> cinemas = catEyeClient.cinemas(movieId, chosenDate);
        //System.out.println(JSONObject.toJSONString(cinemas));
        int cinemaIndex = 1;
        for (Cinema cinema : cinemas) {
            if (cinemaIndex <= 9) {
                System.out.println(cinemaIndex + ". 影院名称: " + cinema.getName());
            } else {
                System.out.println(cinemaIndex + ".影院名称: " + cinema.getName());
            }
            System.out.println("   影院地址: " + cinema.getAddr());
            System.out.println("   " + cinema.getShowTimes());
            System.out.println("   " + cinema.getLabels());
            System.out.println("   距离: " + cinema.getDistance());
            System.out.println();
            cinemaIndex++;
        }

        int cinemaChoice;
        while (true) {
            System.out.print("请输入序号选择影院: ");
            cinemaChoice = scanner.nextInt();

            // 验证输入序号的合法性
            if (cinemaChoice >= 1 && cinemaChoice <= cinemas.size()) {
                break;  // 输入合法，退出循环
            }

            System.out.println("未找到对应的影院，请重新选择序号。");
        }

        // 根据用户选择的序号来处理对应的影院
        Cinema chosenCinema = cinemas.get(cinemaChoice - 1);
        System.out.println();
        System.out.println("您选择的影院是: " + chosenCinema.getName());

        // 获取电影在选定影院的场次信息
        List<MoviePList> moviePLists = chosenCinema.showTimes(chosenDate, movieId);
        System.out.println("以下是电影: '" + chosenFilm.getNm() + "' 在影院: '" + chosenCinema.getName() + "' 的场次信息: ");
        System.out.println();
        int showIndex = 1;
        for (MoviePList moviePList : moviePLists) {
            if (showIndex <= 9) {
                System.out.println(showIndex + ". 放映时间: " + moviePList.getTm());

            } else {
                System.out.println(showIndex + ".放映时间: " + moviePList.getTm());

            }
            System.out.println("   影厅: " + moviePList.getTh());
            System.out.println("   语言版本: " + moviePList.getLang() + moviePList.getTp());
            System.out.println("   票价: " + moviePList.getVipPrice());
            System.out.println();
            showIndex++;
        }

        int showChoice;
        while (true) {

            System.out.print("请输入序号选择场次: ");
            showChoice = scanner.nextInt();

            // 验证输入序号的合法性
            if (showChoice >= 1 && showChoice <= moviePLists.size()) {
                break;  // 输入合法，退出循环
            }

            System.out.println("未找到对应的场次，请重新选择序号。");
        }

        // 根据用户选择的序号来处理对应的场次
        MoviePList chosenShow = moviePLists.get(showChoice - 1);
        System.out.println();
        System.out.println("您选择的场次信息如下: ");
        System.out.println();
        System.out.println("放映时间: " + chosenShow.getTm());
        System.out.println("影厅: " + chosenShow.getTh());
        System.out.println("票价: " + chosenShow.getVipPrice());
        System.out.println();

        // 输出座位信息
        SeatRegion seats = chosenShow.getSeats();
        System.out.println(JSONObject.toJSONString(seats));
        System.out.println("座位图如下: ");

        // 添加顶部横线
        int lineWidth = seats.getRows().get(0).getSeats().size() * 8 + seats.getRegionName().length() + 24;
        String horizontalLine = StringUtils.repeat("-", lineWidth);
        System.out.println(horizontalLine);

        System.out.println(seats.getRegionName() + " \t □未售 \t ■已售 \t ▧不可售");
        HashMap<String, Seat> map = new HashMap<>();
        for (Row row : seats.getRows()) {
            for (Seat seat : row.getSeats()) {
                map.put(seat.getNo(), seat);
                if (seat.getSeatNo().isEmpty()) { // 不是座位
                    System.out.print("\t\t");
                    continue;
                }
                if (seat.getSeatStatus() == 1) { // 可售
                    System.out.print("\t\t" + (seat.getSeatType().equalsIgnoreCase("L") ? "[" : "") + "□" + (seat.getSeatType().equalsIgnoreCase("R") ? "]" : ""));
                } else if (seat.getSeatStatus() == 3) { // 已售
                    System.out.print("\t\t" + (seat.getSeatType().equalsIgnoreCase("L") ? "[" : "") + "■" + (seat.getSeatType().equalsIgnoreCase("R") ? "]" : ""));
                } else if (seat.getSeatStatus() == 4) { // 不可售
                    System.out.print("\t\t" + (seat.getSeatType().equalsIgnoreCase("L") ? "[" : "") + "▧" + (seat.getSeatType().equalsIgnoreCase("R") ? "]" : ""));
                } else {
                    System.out.print("\t\t");
                }
            }
            System.out.println();
            for (Seat seat : row.getSeats()) {
                if (seat.getSeatNo().isEmpty() || seat.getSeatStatus() <= 0) {
                    System.out.print("\t\t");
                    continue;
                }
                System.out.print("\t  " + seat.getNo());
            }
            System.out.println();
        }
        // 添加底部横线
        System.out.println(horizontalLine);


        // 选择座位
        /*System.out.print("请输入座位号(如需多个座位请用' ; '分隔): ");
        String selectedSeats = scanner.next();
        System.out.println("您选择的座位如下: ");
        List<Seat> selectSeats = new ArrayList<>();
        for (String key : selectedSeats.split(";")) {
            Seat seat = map.get(key);// 获取座位状态
            if (seat == null) {
                System.out.println("座位号" + key + "未找到,请检查!");
                return;
            }
            int seatStatus = seat.getSeatStatus();
            if (seatStatus != 1) {
                System.out.println("座位号" + key + "已售或不可出售");
                return;
            }
            selectSeats.add(seat);
        }*/

        // 选择座位
        boolean seatSelected = false;
        List<Seat> selectSeats = new ArrayList<>();

        while (!seatSelected) {
            System.out.print("请输入座位号(如需多个座位请用' ; '分隔): ");
            String selectedSeats = scanner.next();
            System.out.println("您选择的座位如下: ");
            selectSeats.clear(); // 清空上次选择的座位

            for (String key : selectedSeats.split(";")) {
                Seat seat = map.get(key);// 获取座位状态
                if (seat == null) {
                    System.out.println("座位号" + key + "未找到,请检查!");
                    seatSelected = false; // 座位未选择成功，继续循环输入
                    break;
                }
                int seatStatus = seat.getSeatStatus();
                if (seatStatus != 1) {
                    System.out.println("座位号" + key + "已售或不可出售");
                    seatSelected = false; // 座位未选择成功，继续循环输入
                    break;
                }
                selectSeats.add(seat);
                seatSelected = true; // 座位选择成功，跳出循环
            }
        }

        System.out.println("您选择了以下座位号,正在下单:");
        selectSeats.forEach(seat -> System.out.println(seat.getSeatNo()));
        String link = catEyeClient.create(chosenShow.getSeqNo(), selectSeats);
        // 获取当前系统桌面，并打开默认浏览器并访问指定网页
        System.out.println("正在打开浏览器或点击链接去支付: " + link);
        try {
            Desktop.getDesktop().browse(new URI(link));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.showInfo();
    }
}

