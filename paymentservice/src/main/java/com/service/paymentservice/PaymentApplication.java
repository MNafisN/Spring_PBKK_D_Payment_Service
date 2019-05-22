package com.service.paymentservice;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EntityScan (
		basePackageClasses = {Application.class, Jsr310Converters.class}
)
@SpringBootApplication
@EnableMongoAuditing
public class PaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
	}

}
