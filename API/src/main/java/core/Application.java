package core;

import java.util.Arrays;

import core.models.Lot;
import core.repositories.LotRepository;
import core.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeospatialIndex;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private LotRepository lotRepo;
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
        lotRepo.deleteAll();
        accountRepo.deleteAll();

        // save a couple of customers
        template.indexOps(Lot.class).ensureIndex( new GeospatialIndex("location") );
        lotRepo.save(new Lot("Larry's House", "Residential", "2210 Tamarron Lane, Lafayette, CO 80026", -105.120372, 40.001930, "$1.75", 2));
        lotRepo.save(new Lot("Tia's House", "Residential", "332 Morning Star Lane, Lafayette, CO 80026", -105.123108, 40.001248, "$1.50", 3));
        lotRepo.save(new Lot("Indian Peaks Golf Course", "Parking Lot", "2300 Indian Peaks Trail, Lafayette, CO 80026", -105.123891, 40.002670, "$2.15", 50));
    }
}
