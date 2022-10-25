package me.fabriciorby.main.h2;

import me.fabriciorby.main.data.Person;
import me.fabriciorby.main.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresentAnd;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

@SpringBootTest
public class DatabaseInMemoryTest {

    @Autowired
    PersonRepository personRepository;

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:h2:mem:db;DB_CLOSE_ON_EXIT=FALSE");
        registry.add("spring.datasource.username", () -> "sa");
        registry.add("spring.datasource.password", () -> "password");
    }

    @Test
    void personShouldBeInserted() {
        personRepository.save(new Person("Fabrício", 27));
        Optional<Person> personOptional = personRepository.findTop1ByNameAndAge("Fabrício", 27);
        assertThat(personOptional, isPresent());
        assertThat(personOptional, isPresentAnd(hasProperty("name", equalTo("Fabrício"))));
    }

}
