package com.qbb;

import com.qbb.model.Film;

import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

public class Main {
    CatEyeClient catEyeClient = new CatEyeClient();

    void showInfo() {
        System.out.println("请选择影片：");
        List<Film> films = catEyeClient.getFilms();
        films.stream().map(
                f->{
                    StringJoiner joiner = new StringJoiner("\t|\t");
                    joiner.add(f.getId()+"");
                    joiner.add(f.getNm());
                    return joiner;
                }
        ).forEach(System.out::println);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.showInfo();
    }
}
