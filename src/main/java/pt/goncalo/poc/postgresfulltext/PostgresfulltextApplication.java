package pt.goncalo.poc.postgresfulltext;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.PostgreSQLDialect;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pt.goncalo.poc.postgresfulltext.topic.TopicRepository;

import java.util.List;

@SpringBootApplication
public class PostgresfulltextApplication {

    private final TopicRepository topicRepository;

    public PostgresfulltextApplication(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(PostgresfulltextApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(TopicRepository repository, List<Demos.DemoRun> demos) {
        return (args) -> {


            demos
                    .stream()
                    .sorted().forEach(Demos.DemoRun::executeAndPrintReport);
        };

    }

    /**
     * Custom Dialect to register functions to enable PostgreSQL full text search on hibernate.
     * <p>
     * This adds support for the following functions:
     * - tsvector_query
     */



}
