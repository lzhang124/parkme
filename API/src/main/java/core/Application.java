package core;

import java.util.Arrays;

import core.repositories.LotRepository;
import core.repositories.AccountRepository;
import core.repositories.data.LotHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private LotRepository lotRepo;
    @Autowired
    private LotHistoryRepository lotHistoryRepo;
    @Autowired
    private RawHistoryRepository rawHistoryRepo;
    @Autowired
    MongoTemplate template;

    public static void main(String[] args) {

        System.out.println("Starting up");
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        // clear the database
//        lotRepo.deleteAll();
//        accountRepo.deleteAll();
//        lotHistoryRepo.deleteAll();
//        rawHistoryRepo.deleteAll();
    }
}
