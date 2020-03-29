package site.gladmin.homework.trains.core;

import site.gladmin.homework.trains.entity.Param;
import site.gladmin.homework.trains.entity.Road;
import site.gladmin.homework.trains.entity.Trip;
import site.gladmin.homework.trains.constant.SystemConstant;

import java.util.*;

/**
 * 调度类，提供所有调度方法
 */
public class TrainsDispatch {

    /**
     * 计算给定route的距离
     */
    public static String calculateRouteDistance(String routeStr){
        int distance = 0;
        String result = null;
        if (routeStr != null) {
            //System.out.println("入参为：" +routeStr);
            /*按照给定格式解析出路线中的站*/
            String[] stations = routeStr.split("-");
            for (int i = 0; i < stations.length - 1; i++) {
                String roadName = stations[i].concat(stations[i + 1]);
                if (Graph.roads.containsKey(roadName)){
                    Road road = Graph.roads.get(roadName);
                    distance += road.getDistance();
                } else {
                    //System.out.println("不存在该路线：" + roadName);
                    return SystemConstant.NO_SUCH_ROUTE;
                }
            }
            return String.valueOf(distance);
        } else {
            //System.out.println("入参为空！");
            return SystemConstant.NO_SUCH_ROUTE;
        }
    }


    public static List<Trip> TripsSearch(char sStation, char eStation, Param param){
        List<Trip> trips = new ArrayList<>();
        Trip trip = new Trip();
        search(sStation, eStation, trips, trip, param);
        return trips;
    }

    //深度遍历搜索
    private static void search(char sStation, char eStation, List<Trip> trips, Trip trip, Param param){
        List<Road> roads = getRoadsFromGraph(sStation);
        //boolean flag = false;
        if (roads.size() == 0) {
            return;
        }
        /*遍历*/
        for (Road road : roads) {
            int stops = trip.getStops() + 1;
            int distance = trip.getDistance() + Graph.roads.get(road.getRoadName()).getDistance();
            if (whetherSatisfyCondition(param, stops, distance)){
                //flag = true;
                trip.setStops(stops);
                trip.setDistance(distance);
                trip.getStations().add(String.valueOf(sStation));
                if (road.geteStation() == eStation){
                    if (whetherTheTrip(param, stops, distance)) {
                        /*已成功找到一条路线*/
                        Trip tripTemp = new Trip();
                        trip.clone(tripTemp);
                        tripTemp.getStations().add(String.valueOf(eStation));
                        trips.add(tripTemp);
                    }
                    /*递归*/
                    search(road.geteStation(), eStation, trips, trip, param);
                }else {
                    /*递归*/
                    search(road.geteStation(), eStation, trips, trip, param);
                }
                trip.setStops(stops -1);
                trip.setDistance(distance - Graph.roads.get(road.getRoadName()).getDistance());
                trip.getStations().removeLast();
            }
        }
        return;
    }

    /**
     * 条件判断
     */
    private static boolean whetherSatisfyCondition(Param param, int stops, int distance){
        switch (param.getOp()){
            case LESS:
                switch (param.getType()){
                    case STOPS:
                        return stops < param.getValue();
                    case DISTANCE:
                        return distance < param.getValue();
                    default:
                        //System.out.println("参数错误！");
                        return false;
                }
            case EQUEL:
            case LESSANDEQUEL:
                switch (param.getType()){
                    case STOPS:
                        return stops <= param.getValue();
                    case DISTANCE:
                        return distance <= param.getValue();
                    default:
                        //System.out.println("参数错误！");
                        return false;
                }
            default:
                //System.out.println("参数错误！");
                return false;
        }
    }

    /**
     * 识别是否找到了符合条件的Trip
     */
    private static boolean whetherTheTrip(Param param, int stops, int distance){
        switch (param.getOp()){
            case LESS:
                switch (param.getType()){
                    case STOPS:
                        return stops < param.getValue();
                    case DISTANCE:
                        return distance < param.getValue();
                    default:
                        //System.out.println("参数错误！");
                        return false;
                }
            case EQUEL:
                switch (param.getType()){
                    case STOPS:
                        return stops == param.getValue();
                    case DISTANCE:
                        return distance == param.getValue();
                    default:
                        //System.out.println("参数错误！");
                        return false;
                }
            case LESSANDEQUEL:
                switch (param.getType()){
                    case STOPS:
                        return stops <= param.getValue();
                    case DISTANCE:
                        return distance <= param.getValue();
                    default:
                        //System.out.println("参数错误！");
                        return false;
                }
            default:
                //System.out.println("参数错误！");
                return false;
        }
    }

    /**
     * 获取所有以sStation为起点的火车
     */
    private static List<Road> getRoadsFromGraph(char sStation){
        List<Road> result = new ArrayList<>();
        for (Map.Entry<String, Road> entry : Graph.roads.entrySet()) {
            if (entry.getValue().getsStation() == sStation){
                result.add(entry.getValue());
            }
        }
        return result;
    }

    /**
     * Dijkstra 计算最短路径
     */
    public static int tripsDijkstra(char sStation, char eStation) {
        int shortestDis = Integer.MAX_VALUE;
        String midStation = null;
        /* *
        * 始发站和终点站一样时，计算所有节点到其他节点的最短路径，选出路径最短的中间站点
        * 例如 B - C - B, B - C的最短路径加上C - B的最短路径，即为B - B最短路径
        * */
        if (sStation == eStation) {
            HashMap<String, HashMap<String, Trip>> shortestTripsOfAll = new HashMap<>();
            HashMap<String, Trip> shortestTrips = null;
            Set<String> stationSet = new HashSet<>();
            /*获取所有站点*/
            for (Map.Entry<String, Road> entry : Graph.roads.entrySet()) {
                stationSet.add(String.valueOf(entry.getValue().getsStation()));
                stationSet.add(String.valueOf(entry.getValue().geteStation()));
            }
            /*计算所有站点到其他站点的最短路径*/
            for (String station : stationSet) {
                shortestTrips = dijkstra(station.charAt(0));
                shortestTripsOfAll.put(station, shortestTrips);
            }
            shortestTrips = shortestTripsOfAll.get(String.valueOf(sStation));
            stationSet.remove(String.valueOf(sStation));
            HashMap<String, Trip> shortestTripsTemp = null;
            int distance = 0 ;
            /*找出最短的中间站点*/
            for (String station : stationSet) {
                String key1 = String.valueOf(sStation).concat(station);
                String key2 = station.concat(String.valueOf(sStation));
                shortestTripsTemp = shortestTripsOfAll.get(station);
                if (shortestTrips.get(key1).getDistance() == Integer.MAX_VALUE
                || shortestTripsTemp.get(key2).getDistance() == Integer.MAX_VALUE){
                    continue;
                }
                distance = shortestTrips.get(key1).getDistance() + shortestTripsTemp.get(key2).getDistance();
                if (distance < shortestDis){
                    shortestDis = distance;
                    midStation = station;
                }
            }
        } else {
            /*选出sStation - eStation的最短路由*/
            HashMap<String, Trip> shortestTrips = dijkstra(sStation);
            String key = String.valueOf(sStation).concat(String.valueOf(eStation));
            shortestDis = shortestTrips.get(key).getDistance();
        }
        return shortestDis;
    }
    

    private static HashMap<String, Trip> dijkstra(char sStation){
        Set<String> srcSet = new HashSet<>();
        Set<String> destSet = new HashSet<>();
        HashMap<String, Trip> shortestTrips = new HashMap<>();
        init(sStation, srcSet, shortestTrips);
        destSet.add(String.valueOf(sStation));
        srcSet.remove(String.valueOf(sStation));
        searchShortest(String.valueOf(sStation), srcSet, destSet, shortestTrips);
        return shortestTrips;
    }

    /**
     * 递归方法
     */
    private static void searchShortest(String sStation, Set<String> srcSet, Set<String> destSet, HashMap<String, Trip> shortestTrips){
        if (srcSet.isEmpty()){
            return;
        }
        String shortestStation = getShortestTrip(shortestTrips, destSet);
        if (shortestStation == null) {
            //System.out.println("发生错误！");
            return;
        }
        String shortestTripKey = sStation.concat(shortestStation);
        Trip shortestTrip = shortestTrips.get(shortestTripKey);
        srcSet.remove(shortestStation);
        destSet.add(shortestStation);
        List<Road> roads = getRoadsFromGraph(shortestStation.charAt(0));
        for (Road road : roads) {
            char eStation = road.geteStation();
            if (sStation.equals(String.valueOf(eStation))){
                //System.out.println("不计算起始站和终点站相同的情况！");
                continue;
            }
            String key = sStation.concat(String.valueOf(eStation));
            Trip trip = shortestTrips.get(key);
            if (trip.getDistance() > shortestTrip.getDistance() + road.getDistance()){
                trip.setDistance(shortestTrip.getDistance() + road.getDistance());
                trip.setStops(trip.getStops() + 1);
                trip.getStations().addLast(shortestStation);
            }
        }
        searchShortest(sStation, srcSet, destSet, shortestTrips);
    }
    /**
     * 获取当前最短路径
     */
    private static String getShortestTrip(HashMap<String, Trip> shortestTrips, Set<String> destSet){
        int dis = Integer.MAX_VALUE;
        String result = null;
        for (Map.Entry<String, Trip> entry : shortestTrips.entrySet()) {
            String eStation = entry.getKey().substring(1, 2);
            if (entry.getValue().getDistance() < dis
                    && (!destSet.contains(eStation))){
                dis = entry.getValue().getDistance();
                result = eStation;
            }
        }
        return result;
    }

    /**
     * 获取所有站点并初始化最短路由
     */
    private static void init(char sStation, Set<String> srcSet, HashMap<String, Trip> shortestTrips){
        /*获取所有节点存入集合*/
        for (Map.Entry<String, Road> entry : Graph.roads.entrySet()) {
            srcSet.add(String.valueOf(entry.getValue().getsStation()));
            srcSet.add(String.valueOf(entry.getValue().geteStation()));
        }
        /*初始化站点sStation到其他所有站点的最短路由*/
        for (String s : srcSet){
            if (s.equals(String.valueOf(sStation)))
                continue;
            String key = String.valueOf(sStation).concat(s);
            Trip trip = new Trip();
            trip.getStations().add(String.valueOf(sStation));
            //trip.getStations().add(s);
            int distance = Graph.roads.containsKey(key) ? Graph.roads.get(key).getDistance() : Integer.MAX_VALUE;
            trip.setDistance(distance);
            shortestTrips.put(key, trip);
        }
    }
}
