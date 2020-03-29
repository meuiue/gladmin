package site.gladmin.nio.demo;

import org.junit.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class TestBuffer {

    @Test
    public void test2(){
       ByteBuffer buffer= ByteBuffer.allocateDirect(1024);

        System.out.println(buffer.isDirect());
    }

    @Test
    public void test1(){

        String str ="abcde";
        ByteBuffer buffer = ByteBuffer.allocate(1024);


        System.out.println(buffer.position());
        System.out.println(buffer.capacity());
        System.out.println(buffer.limit());

        buffer.put(str.getBytes());

        System.out.println("--------------put-------------");
        System.out.println(buffer.position());
        System.out.println(buffer.capacity());
        System.out.println(buffer.limit());


        buffer.flip();
        System.out.println("--------------read-------------");
        System.out.println(buffer.position());
        System.out.println(buffer.capacity());
        System.out.println(buffer.limit());

        System.out.println("--------------get-------------");
        byte[] dst =new byte[buffer.limit()];
        buffer.get(dst);
        System.out.println(new String(dst,0,dst.length));

        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());


        System.out.println("--------------rewind-------------");
        buffer.rewind();
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        System.out.println("--------------clear-------------");
        buffer.clear();
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println((char)buffer.get());


    }
}
