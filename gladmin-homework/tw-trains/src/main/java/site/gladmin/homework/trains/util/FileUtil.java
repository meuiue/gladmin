package site.gladmin.homework.trains.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件读取类
 */
public class FileUtil {
    public static List<String> readFileByLine(String fileName) {
        ArrayList<String> arrayList = new ArrayList<>();
        File file = null;
        InputStreamReader inputReader = null;
        BufferedReader bf = null;
        //System.out.println(System.getProperty("user.dir"));
        try {
            file = new File(fileName);
            if (file.exists()) {
                inputReader = new InputStreamReader(new FileInputStream(file));
                bf = new BufferedReader(inputReader);
                // 按行读取字符串
                String str = null;
                while ((str = bf.readLine()) != null) {
                    arrayList.add(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputReader != null) {
                try {
                    inputReader.close();
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }
            }
            if (bf != null) {
                try {
                    bf.close();
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }
            }
        }
        return arrayList;
    }
}
