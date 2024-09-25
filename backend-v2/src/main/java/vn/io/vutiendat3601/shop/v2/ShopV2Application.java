package vn.io.vutiendat3601.shop.v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ShopV2Application {
	public static void main(String[] args) {
		SpringApplication.run(ShopV2Application.class, args);
	}
}
