package site.gladmin.nio.demo;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


/**
 */
public class TestBlockingNIO2 {


    @Test
    public void client() throws IOException {
        //1、获取通道
        InetSocketAddress inetSocketAddress= new InetSocketAddress("127.0.0.1",9898);
        SocketChannel socketChannel= SocketChannel.open(inetSocketAddress);

        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"),StandardOpenOption.READ);


        //2.分配指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //3.读取本地问题件，
        while (inChannel.read(buffer)!= -1){
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }

        socketChannel.shutdownOutput();

        int len = 0;
        while ((len= socketChannel.read(buffer))!=-1){
            buffer.flip();
            System.out.println(new String(buffer.array(),0,len));
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


        buffer.put("服务端接收成功！".getBytes());
        buffer.flip();
        socketChannel.write(buffer);

        socketChannel.shutdownOutput();

        outChannel.close();
        socketChannel.close();
        serverSocketChannel.close();
    }


}

