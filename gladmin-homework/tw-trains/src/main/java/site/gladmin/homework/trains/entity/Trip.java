package site.gladmin.homework.trains.entity;

import lombok.Data;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 线路，包括途径站点/距离/停靠次数信息
 */
@Data
public class Trip {

    private int stops;
    private int distance;
    private LinkedList<String> stations = new LinkedList<>();

    public void clone(Trip dst){
        dst.stops = this.stops;
        dst.distance = this.distance;
        for(Iterator iter = this.stations.iterator(); iter.hasNext();)
            dst.stations.add((String) iter.next());
    }

}
