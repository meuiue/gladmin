package site.gladmin.homework.trains.core;

import site.gladmin.homework.trains.entity.Road;
import site.gladmin.homework.trains.constant.SystemConstant;
import site.gladmin.homework.trains.util.FileUtil;

import java.util.HashMap;
import java.util.List;

/**
 * 根据输入生成地图
 */
public class Graph {
    public static HashMap<String, Road> roads = new HashMap<>();
    static {
        List<String> lineList = FileUtil.readFileByLine(SystemConstant.INPUT_FILE_PATH);
        /*题目给定规则*/
        if (lineList.size() > 0) {
            //System.out.println("正确读取输入文件！");
            String[] roadList = lineList.get(0).split(", ");
            for (String roadStr : roadList) {
                Road road = new Road(roadStr);
                roads.put(road.getRoadName(), road);
            }
            //System.out.println("图加载成功！");
        }
    }
}
