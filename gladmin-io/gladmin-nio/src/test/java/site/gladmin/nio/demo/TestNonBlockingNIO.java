package site.gladmin.nio.demo;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class TestNonBlockingNIO {



    @Test
    public void client() throws IOException {


        //1、获取通道
        SocketChannel socketChannel= SocketChannel.open(new InetSocketAddress("127.0.0.1",9898));

        //非堵塞模式
        socketChannel.configureBlocking(false);


        //2.分配指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //3.读取本地问题件，

        //4
        Scanner scanner = new Scanner(System.in);

        while(scanner.hasNext()){
            String str = scanner.next();
            buffer.put((new Date().toString()+"："+str).getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();

        }

        socketChannel.close();
    }

    @Test
    public void server() throws IOException {


        //1.获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //2.切换非阻塞模式
        serverSocketChannel.configureBlocking(false);

        //3.绑定连接
        serverSocketChannel.bind(new InetSocketAddress(9898));


        //
        Selector selector =  Selector.open();


        //5.选择器监听事件
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

        //6.循环获取选择器上已经准备就绪的事件
        while (selector.select()>0){

            //7.获取当前选择器获取到的key值，
            Iterator<SelectionKey> iterator =   selector.selectedKeys().iterator();

            while (iterator.hasNext()){
                //8.
                SelectionKey selectionKey = iterator.next();

                if (selectionKey.isAcceptable()){
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }
                else if(selectionKey.isReadable()){

                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len =0;
                    while ((len= socketChannel.read(buffer))>0){
                        buffer.flip();
                        System.out.println(new String(buffer.array(),0,len));
                        buffer.clear();
                    }
                }

                iterator.remove();
            }

        }

    }

}
