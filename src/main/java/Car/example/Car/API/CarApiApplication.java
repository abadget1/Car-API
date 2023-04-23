package Car.example.Car.API;

import Car.example.Car.API.controller.CarController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CarApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarApiApplication.class, args);
	}

}
