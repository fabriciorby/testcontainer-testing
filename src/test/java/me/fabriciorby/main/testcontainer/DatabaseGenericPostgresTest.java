package me.fabriciorby.main.testcontainer;

import me.fabriciorby.main.data.Person;
import me.fabriciorby.main.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresentAnd;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

@SpringBootTest
@Testcontainers
public class DatabaseGenericPostgresTest {

    @Container
    static GenericContainer<?> genericContainer =
            new GenericContainer<>(DockerImageName.parse("postgres:9.6.12"))
                        .withEnv("POSTGRES_DB", "test")
                        .withEnv("POSTGRES_USER", "test")
                        .withEnv("POSTGRES_PASSWORD", "test")
                        .withExposedPorts(5432);

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:postgresql://" + genericContainer.getHost() + ":" + genericContainer.getMappedPort(5432) + "/" + "test");
        registry.add("spring.datasource.username", () -> "test");
        registry.add("spring.datasource.password", () -> "test");
    }

    @Autowired
    PersonRepository personRepository;

    @Test
    void personShouldBeInserted() {
        personRepository.save(new Person("Fabrício", 27));
        Optional<Person> personOptional = personRepository.findTop1ByNameAndAge("Fabrício", 27);
        assertThat(personOptional, isPresent());
        assertThat(personOptional, isPresentAnd(hasProperty("name", equalTo("Fabrício"))));
    }

}
