package me.fabriciorby.main.h2;

import me.fabriciorby.main.data.Person;
import me.fabriciorby.main.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresentAnd;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DatabaseMockTest {

    @MockBean
    PersonRepository personRepository;

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:h2:mem:db;DB_CLOSE_ON_EXIT=FALSE");
        registry.add("spring.datasource.username", () -> "sa");
        registry.add("spring.datasource.password", () -> "password");
    }

    @Test
    void personShouldBeInserted() {
        when(personRepository.findTop1ByNameAndAge("Fabrício", 27))
                .thenReturn(Optional.of(new Person("Fabrício", 27)));
        personRepository.save(new Person("Fabrício", 27));
        Optional<Person> personOptional = personRepository.findTop1ByNameAndAge("Fabrício", 27);
        assertThat(personOptional, isPresentAnd(hasProperty("name", equalTo("Fabrício"))));
    }

}
