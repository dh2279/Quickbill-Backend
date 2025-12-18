package com.bill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuickBillApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickBillApplication.class, args);
		System.err.println("App is started...");
	}

}
