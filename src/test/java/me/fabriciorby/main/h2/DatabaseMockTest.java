package me.fabriciorby.main.h2;

import me.fabriciorby.main.data.Person;
import me.fabriciorby.main.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresentAnd;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.when;

@DataJpaTest
public class DatabaseMockTest {

    @MockBean
    PersonRepository personRepository;

    @Test
    void personShouldBeInserted() {
        when(personRepository.findTop1ByNameAndAge("Fabrício", 27))
                .thenReturn(Optional.of(new Person("Fabrício", 27)));
        personRepository.save(new Person("Fabrício", 27));
        Optional<Person> personOptional = personRepository.findTop1ByNameAndAge("Fabrício", 27);
        assertThat(personOptional, isPresentAnd(hasProperty("name", equalTo("Fabrício"))));
    }

}
