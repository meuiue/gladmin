package site.gladmin.homework.trains.entity;

import lombok.Data;

/**
 * 车次，即输入中存在的路劲
 */
@Data
public class Road {
    /*路名*/
    private String roadName;
    /*起点*/
    private char sStation;
    /*终点*/
    private char eStation;
    /*距离*/
    private int distance;

    public Road() {
    }

    public Road(String abbr){
        /*基于题目要求解析*/
        this.sStation = abbr.charAt(0);
        this.eStation = abbr.charAt(1);
        this.distance = abbr.charAt(2) - '0';
        this.roadName = abbr.substring(0,2);
    }

}
