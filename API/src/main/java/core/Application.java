package core;

import java.util.Arrays;

import core.Repositories.ParkingLotRepository;
import core.models.ParkingLot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private ParkingLotRepository repository;

    public static void main(String[] args) {

        System.out.println("Starting up");
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Starting up");
        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll();

        // save a couple of customers
        repository.save(new ParkingLot("ZYXParking", "80027", "Address 1", 100));
        repository.save(new ParkingLot("IParking", "80027", "Address 2", 200));

        // fetch all customers
        System.out.println("Lots found with findAll():");
        System.out.println("-------------------------------");
        for (ParkingLot parkinglot : repository.findAll()) {
            System.out.println(parkinglot);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Lot found with findByName('ZYXParking'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByName("ZYXParking"));

        System.out.println("Lot found with findByZipcode('80027'):");
        System.out.println("--------------------------------");
        for (ParkingLot parkinglot : repository.findByZipcode("80027")) {
            System.out.println(parkinglot);
        }
    }
}
