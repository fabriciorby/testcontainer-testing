package me.fabriciorby.main.repository;

import me.fabriciorby.main.data.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findTop1ByNameAndAge(String name, int age);

}
