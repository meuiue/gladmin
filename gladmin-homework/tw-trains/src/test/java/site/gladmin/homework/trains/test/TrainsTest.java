package site.gladmin.homework.trains.test;

import site.gladmin.homework.trains.entity.Param;
import site.gladmin.homework.trains.entity.Trip;
import site.gladmin.homework.trains.core.TrainsDispatch;
import site.gladmin.homework.trains.enums.Operate;
import site.gladmin.homework.trains.enums.Type;
import org.junit.Test;

import java.util.List;


public class TrainsTest {
    @Test
    public void test(){
        /*测试1 - 5*/
        String distance;
        distance = TrainsDispatch.calculateRouteDistance("A-B-C");
        System.out.println("Output #1: " + distance);
        distance = TrainsDispatch.calculateRouteDistance("A-D");
        System.out.println("Output #2: " + distance);
        distance = TrainsDispatch.calculateRouteDistance("A-D-C");
        System.out.println("Output #3: " + distance);
        distance = TrainsDispatch.calculateRouteDistance("A-E-B-C-D");
        System.out.println("Output #4: " + distance);
        distance = TrainsDispatch.calculateRouteDistance("A-E-D");
        System.out.println("Output #5: " + distance);
        /*Test 6*/
        Param param = new Param();
        param.setOp(Operate.LESSANDEQUEL);
        param.setType(Type.STOPS);
        param.setValue(3);
        List<Trip> result;
        result = TrainsDispatch.TripsSearch('C', 'C', param);
        System.out.println("Output #6: " + result.size());

        /*Test 7*/
        param.setOp(Operate.LESS);
        param.setType(Type.STOPS);
        param.setValue(4);
        result = TrainsDispatch.TripsSearch('A', 'C', param);
        System.out.println("Output #7: " + result.size());

        /*Test 8*/
        int dis = TrainsDispatch.tripsDijkstra('A', 'C');
        System.out.println("Output #8: " + dis);
        /*Test 9*/
        dis = TrainsDispatch.tripsDijkstra('B', 'B');
        System.out.println("Output #9: " + dis);

        /*Test 10*/
        param.setOp(Operate.LESS);
        param.setType(Type.DISTANCE);
        param.setValue(30);
        result = TrainsDispatch.TripsSearch('C', 'C', param);
        System.out.println("Output #10: " + result.size());
    }
/*    *//*测试1 - 5*//*
    @Test
    public void calculateRouteDistance(){
        String distance;
        distance = TrainsDispatch.calculateRouteDistance("A-B-C");
        System.out.println("Output #1: " + distance);
        distance = TrainsDispatch.calculateRouteDistance("A-D");
        System.out.println("Output #2: " + distance);
        distance = TrainsDispatch.calculateRouteDistance("A-D-C");
        System.out.println("Output #3: " + distance);
        distance = TrainsDispatch.calculateRouteDistance("A-E-B-C-D");
        System.out.println("Output #4: " + distance);
        distance = TrainsDispatch.calculateRouteDistance("A-E-D");
        System.out.println("Output #5: " + distance);
    }
    *//*测试6 7 10*//*
    @Test
    public void tripsSearch(){
        *//*Test 6*//*
        Param param = new Param();
        param.setOp(Operate.LESSANDEQUEL);
        param.setType(Type.STOPS);
        param.setValue(3);
        List<Trip> result;
        result = TrainsDispatch.TripsSearch('C', 'C', param);
        System.out.println("Output #6: " + result.size());

        *//*Test 7*//*
        param.setOp(Operate.LESS);
        param.setType(Type.STOPS);
        param.setValue(4);
        result = TrainsDispatch.TripsSearch('A', 'C', param);
        System.out.println("Output #7: " + result.size());

        *//*Test 10*//*
        param.setOp(Operate.LESS);
        param.setType(Type.DISTANCE);
        param.setValue(30);
        result = TrainsDispatch.TripsSearch('C', 'C', param);
        System.out.println("Output #10: " + result.size());
    }
    *//*测试8 9*//*
    @Test
    public void shortestTripSearch(){
        *//*Test 8*//*
        int dis = TrainsDispatch.tripsDijkstra('A', 'C');
        System.out.println("Output #8: " + dis);
        *//*Test 9*//*
        dis = TrainsDispatch.tripsDijkstra('B', 'B');
        System.out.println("Output #9: " + dis);
    }*/
}
