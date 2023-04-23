package Car.example.Car.API.service;

import Car.example.Car.API.model.Car;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private List<Car> cars;

    public CarService() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream inputStream = getClass().getResourceAsStream("/cars.json");
            cars = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Error reading cars.json file", e);
        }
    }


    public List<Car> getAllCars() {
        return cars;
    }


    public TypeReference<List<Car>> getCarListType() {
        return new TypeReference<>() {
            // This is an empty anonymous inner class
            // that exists solely to provide a type
            // reference for the ObjectMapper
        };
    }
    public Optional<Car> getCar(int index) {
        if (index >= 0 && index < cars.size()) {
            return Optional.of(cars.get(index));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Car> getCar(String make, String model, int year, int price, String color) {
        List<Car> cars = getAllCars();

        Optional<Car> matchingCar = cars.stream()
                .filter(car -> make == null || car.getMake().equalsIgnoreCase(make))
                .filter(car -> model == null || car.getModel().equalsIgnoreCase(model))
                .filter(car -> year == 0 || car.getYear() == year)
                .filter(car -> price == 0 || car.getPrice() == price)
                .filter(car -> color == null || car.getColor().equalsIgnoreCase(color))
                .findFirst();
        return matchingCar;
    }




    public int getCarIndex(Car car) {
        return cars.indexOf(car);
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void updateCar(int index, Car car) {
        cars.set(index, car);
    }

    public void deleteCar(int index) {
        cars.remove(index);
    }
}
