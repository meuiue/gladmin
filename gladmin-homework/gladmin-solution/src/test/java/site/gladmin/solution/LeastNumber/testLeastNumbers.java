package site.gladmin.solution.LeastNumber;

import org.junit.Test;

import java.util.*;

public class testLeastNumbers {


    @Test
    public void testLeastNumbers(){
//        int[] arr = {4,5,1,6,2,7,3,8,12,2,3};
        int arrLength= 800, k=40;
        int[] arr = new int[arrLength];
        Random random =new Random();

        for (int i =0; i<arrLength;i++) {
            arr[i] = random.nextInt(arrLength);
        }


        System.out.println(Arrays.toString(arr));
        System.out.println("---------------------------------------------");

        // 最后一个参数表示我们要找的是下标为k-1的数
        System.out.println( Arrays.toString(getLeastNumbers(arr, k)) );

    }


    public int[] getLeastNumbers(int[] arr, int k) {
        if (k == 0 || arr.length == 0) {
            return new int[0];
        }
        // TreeMap的key是数字, value是该数字的个数。
        // cnt表示当前map总共存了多少个数字。
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int cnt = 0;
        for (int num: arr) {
            // 1. 遍历数组，若当前map中的数字个数小于k，则map中当前数字对应个数+1
            if (cnt < k) {
                map.put(num, map.getOrDefault(num, 0) + 1);
                cnt++;
                continue;
            }
            // 2. 否则，取出map中最大的Key（即最大的数字), 判断当前数字与map中最大数字的大小关系：
            //    若当前数字比map中最大的数字还大，就直接忽略；
            //    若当前数字比map中最大的数字小，则将当前数字加入map中，并将map中的最大数字的个数-1。
            Map.Entry<Integer, Integer> entry = map.lastEntry();
            if (entry.getKey() > num) {
                map.put(num, map.getOrDefault(num, 0) + 1);
                if (entry.getValue() == 1) {
                    map.pollLastEntry();
                } else {
                    map.put(entry.getKey(), entry.getValue() - 1);
                }
            }

        }


        // 根据给定的数组创建一棵树
        TreeNode root = ConstructTree.constructTree(map);
        // 将刚刚创建的树打印出来
        TreeOperation.show(root);



        // 最后返回map中的元素
        int[] res = new int[k];
        int idx = 0;
        for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
            int freq = entry.getValue();
            while (freq-- > 0) {
                res[idx++] = entry.getKey();
            }
        }
        return res;
    }
}
