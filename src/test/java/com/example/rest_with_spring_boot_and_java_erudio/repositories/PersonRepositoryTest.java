package com.example.rest_with_spring_boot_and_java_erudio.repositories;
import com.example.rest_with_spring_boot_and_java_erudio.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    PersonRepository repository;

    private Person person0;

    @BeforeEach
    public void setup(){
        person0 = new Person("Leandro", "Costa","leandro@erudio.com..br", "Minas Gerais", "Male");

    }

        @DisplayName("Given Person Object When Save then Return Saved Person")
        @Test
        void testGivenPersonObject_WhenSave_thenReturnSavedPerson() {

            // Given / Arrange
            // When / Act
            Person savedPerson = repository.save(person0);

            // Then / Assert
            assertNotNull(savedPerson);
            assertTrue(savedPerson.getId() > 0);
        }

    @DisplayName("Given Person List When findAll then Return Person List")
    @Test
    void testGivenPersonList_WhenFindAll_thenReturnPersonList() {

        // Given / Arrange
        Person person1 = new Person("Leonardo", "Costa","leonardo@erudio.com..br", "Minas Gerais", "Male");

        repository.save(person0);
        repository.save(person1);
        // When / Act
        List<Person> personList = repository.findAll();
        // Then / Assert
        assertNotNull(personList);
        assertEquals(2, personList.size());
    }


    @DisplayName("Given Person Object When Find by ID then Return Person Object")
    @Test
    void testGivenPersonObject_WhenFindByID_thenReturnPersonObject() {

        // Given / Arrange
        repository.save(person0);

        // When / Act
        Person savedPerson = repository.findById(person0.getId()).get();

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(person0.getId(), savedPerson.getId());
    }

    @DisplayName("Given Person Object When Find by Email then Return Person Object")
    @Test
    void testGivenPersonObject_WhenFindByEmail_thenReturnPersonObject() {

        // Given / Arrange
        repository.save(person0);

        // When / Act
        Person savedPerson = repository.findByEmail(person0.getEmail()).get();

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(person0.getId(), savedPerson.getId());
    }

    @DisplayName("Given Person Object When Update Person then Return Update Person Object")
    @Test
    void testGivenPersonObject_WhenUpdatePerson_thenReturnUpdatePersonObject() {

        // Given / Arrange
        repository.save(person0);

        // When / Act
        Person savedPerson = repository.findById(person0.getId()).get();
        savedPerson.setFirstName("Leonardo");
        savedPerson.setEmail("leonardo@erudio.com.br");

        Person updatedPerson = repository.save(savedPerson);

        // Then / Assert
        assertNotNull(updatedPerson);
        assertEquals("Leonardo", updatedPerson.getFirstName());
        assertEquals("leonardo@erudio.com.br", updatedPerson.getEmail());
    }


    @DisplayName("Given Person Object When Delete then Remove Person ")
    @Test
    void testGivenPersonObject_WhenDelete_thenRemovePerson() {

        // Given / Arrange
        repository.save(person0);

        // When / Act
        repository.deleteById(person0.getId());

        Optional<Person> personOptional = repository.findById(person0.getId());

        // Then / Assert
        assertTrue(personOptional.isEmpty());
    }

    @DisplayName("Given FirstName and LastName When Find JPQL then Return Person Object")
    @Test
    void testGivenFirstNameAndLastName_WhenFindJPQL_thenReturnPersonObject() {

        // Given / Arrange
        repository.save(person0);

        String firstName = "Leandro";
        String lastName = "Costa";

        // When / Act
        Person savedPerson = repository.findByJPQL(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }

    @DisplayName("Given FirstName and LastName When Find JPQL Named Parameters then Return Person Object")
    @Test
    void testGivenFirstNameAndLastName_WhenFindJPQLNamedParameters_thenReturnPersonObject() {

        // Given / Arrange
        repository.save(person0);

        String firstName = "Leandro";
        String lastName = "Costa";

        // When / Act
        Person savedPerson = repository.findByJPQLNamedParameters(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }

    @DisplayName("Given FirstName and LastName When Find Native SQL then Return Person Object")
    @Test
    void testGivenFirstNameAndLastName_WhenFindByNativeSQL_thenReturnPersonObject() {

        // Given / Arrange
        repository.save(person0);

        String firstName = "Leandro";
        String lastName = "Costa";

        // When / Act
        Person savedPerson = repository.findByNativeSQL(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }

    @DisplayName("Given FirstName and LastName When Find Native SQL With Parameters then Return Person Object")
    @Test
    void testGivenFirstNameAndLastName_WhenFindByNativeSQLWithParameters_thenReturnPersonObject() {

        // Given / Arrange
        repository.save(person0);

        String firstName = "Leandro";
        String lastName = "Costa";

        // When / Act
        Person savedPerson = repository.findByNativeSQLWithNamedParameters(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }
}
