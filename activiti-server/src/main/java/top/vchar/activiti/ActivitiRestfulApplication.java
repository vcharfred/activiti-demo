package top.vchar.activiti;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p> 启动类 </p>
 *
 * @author vchar fred
 * @create_date 2024/5/16
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ActivitiRestfulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiRestfulApplication.class);
    }
}
