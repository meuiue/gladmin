package site.gladmin.nio.demo;


import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 1.通道（Channel）负责连接；
 *
 *      java.nio.channels.Channel接口：
 *          SelectableChannel
 *              SocketChannel
 *              ServerSocketChannel
 *              DatagramChannel
 *
 *              pipe.SinkChannel
 *              pipe.SourceChannel
 *
 *
 * 2.缓冲区（Buffer）：负责数据的存取；
 *
 * 3.选择器（Selector）：
 */
public class TestBlockingNIO {


    @Test
    public void client() throws IOException {

        //1、获取通道
        InetSocketAddress inetSocketAddress= new InetSocketAddress("127.0.0.1",9898);
        SocketChannel socketChannel= SocketChannel.open();
        if (!socketChannel.isConnected())
            socketChannel.connect(inetSocketAddress);


        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"),StandardOpenOption.READ);


        //2.分配指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //3.读取本地问题件，
        while (inChannel.read(buffer)!= -1){
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }

        inChannel.close();
        socketChannel.close();

    }

    @Test
    public void server() throws IOException {


        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"),StandardOpenOption.WRITE,StandardOpenOption.CREATE);

        serverSocketChannel.bind(new InetSocketAddress(9898));

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        SocketChannel socketChannel = serverSocketChannel.accept();

        while (socketChannel.read(buffer)!=-1){
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }

        outChannel.close();
        socketChannel.close();
        serverSocketChannel.close();



    }
}
