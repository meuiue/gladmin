package site.gladmin.nio.demo;

import com.sun.media.sound.StandardMidiFileReader;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 获取通道的三种方式
 *
 * 1.Java针对通道类提供了 getChannel（）方法
 *      本地IO：
 *      FileInputStream/FileOutputStream
 *      RandomAccessFile
 *
 *      网络IO
 *      Socket
 *      ServerSocket
 *      DatagramSocket
 *
 * 2.JDK1.7中的NIO.2 提供了通道静态方法 Open（）
 * 3.JDK1.7中NIO.2的Files工具类的newByteChannel（）
 *
 */
public class TestChannel {

    @Test
    public void test3() throws IOException {

        FileChannel inChannel =   FileChannel.open(Paths.get("e:/1.mp4"),StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("e:/2.mp4"),StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE);

        inChannel.transferTo(0,inChannel.size(),outChannel);

        inChannel.close();
        outChannel.close();

    }


    @Test
    public void test2() throws IOException {

        long start = System.currentTimeMillis();

        FileChannel inChannel =   FileChannel.open(Paths.get("e:/1.mp4"),StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("e:/2.mp4"),StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE_NEW);

        MappedByteBuffer inMappedBuf=  inChannel.map(FileChannel.MapMode.READ_ONLY,0,inChannel.size());
        MappedByteBuffer outMappedBuf= outChannel.map(FileChannel.MapMode.READ_WRITE,0,inChannel.size());

        byte[] dst =new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.put(dst);

        inChannel.close();
        outChannel.close();


        long end = System.currentTimeMillis();
        System.out.println("耗费时间为："+(end-start));
    }

    @Test
    public void test1() throws IOException {
        long start = System.currentTimeMillis();
        FileInputStream fileInputStream =new FileInputStream("1.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("2.jpg");


        FileChannel inChannel = fileInputStream.getChannel();
        FileChannel outChannel =fileOutputStream.getChannel();

        ByteBuffer buffer =ByteBuffer.allocate(1024);
        while (inChannel.read(buffer)!=-1){

            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }

        outChannel.close();
        inChannel.close();
        fileOutputStream.close();
        fileInputStream.close();


        long end = System.currentTimeMillis();
        System.out.println("耗费时间为："+(end-start));

    }

}
