package core;

import java.util.Arrays;

import core.models.Account;
import core.models.ParkingLot;
import core.Repositories.AccountRepository;
import core.Repositories.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private ParkingLotRepository plRepository;

    @Autowired
    private AccountRepository aRepository;

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
        plRepository.deleteAll();
        aRepository.deleteAll();

        // save a couple of customers
        plRepository.save(new ParkingLot("ZYXParking", "80027", "Address 1", "$2.00", 100));
        plRepository.save(new ParkingLot("IParking", "80027", "Address 2", "$1.75", 200));

        // save a couple of Accounts
        aRepository.save(new Account("Larry", "Zhang", "larry", "password", "3038070600", "larry.zhang@avoktech.com"));
        aRepository.save(new Account("Yihong", "Zhang", "yihong", "password", "3038813418", "yihong.zhang@avoktech.com"));

//        // fetch all customers
//        System.out.println("Lots found with findAll():");
//        System.out.println("-------------------------------");
//        for (ParkingLot parkinglot : plRepository.findAll()) {
//            System.out.println(parkinglot);
//        }
//        System.out.println();
//
//        // fetch an individual customer
//        System.out.println("Lot found with findByName('ZYXParking'):");
//        System.out.println("--------------------------------");
//        System.out.println(plRepository.findByName("ZYXParking"));
//
//        System.out.println("Lot found with findByZipcode('80027'):");
//        System.out.println("--------------------------------");
//        for (ParkingLot parkinglot : plRepository.findByZipcode("80027")) {
//            System.out.println(parkinglot);
//        }
    }
}
