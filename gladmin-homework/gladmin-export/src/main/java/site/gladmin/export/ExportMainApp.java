package site.gladmin.export;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;



@SpringBootApplication
public class ExportMainApp {


    public static void main(String[] args) {

        SpringApplication.run(ExportMainApp.class,args);
    }
}
