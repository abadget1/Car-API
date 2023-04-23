package Car.example.Car.API.controller;

import Car.example.Car.API.model.Car;
import Car.example.Car.API.service.CarService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/cars")
public class CarController {
    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping(value = "/api/cars", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Car> getCar(
            @RequestParam(value = "make", required = false) String make,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "year", required = false) int year,
            @RequestParam(value = "price", required = false) int price,
            @RequestParam(value = "color", required = false) String color) {

        System.out.println(make);
        Optional<Car> matchingCar = carService.getCar(make, model, year, price, color);

        if (matchingCar.isPresent()) {
            return ResponseEntity.ok(matchingCar.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/{index}")
    public ResponseEntity<Car> getCar(@PathVariable int index) {
        Optional<Car> car = carService.getCar(index);
        if (car.isPresent()) {
            return ResponseEntity.ok(car.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> addCar(@RequestBody Car car) {
        carService.addCar(car);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{index}").buildAndExpand(carService.getCarIndex(car)).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{index}")
    public ResponseEntity<Void> updateCar(@PathVariable int index, @RequestBody Car car) {
        Optional<Car> existingCar = carService.getCar(index);
        if (existingCar.isPresent()) {
            carService.updateCar(index, car);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{index}")
    public ResponseEntity<Void> deleteCar(@PathVariable int index) {
        Optional<Car> existingCar = carService.getCar(index);
        if (existingCar.isPresent()) {
            carService.deleteCar(index);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
