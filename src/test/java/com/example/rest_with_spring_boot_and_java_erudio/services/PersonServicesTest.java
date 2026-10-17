package com.example.rest_with_spring_boot_and_java_erudio.services;

import com.example.rest_with_spring_boot_and_java_erudio.exceptions.ResourceNotFoundException;
import com.example.rest_with_spring_boot_and_java_erudio.model.Person;
import com.example.rest_with_spring_boot_and_java_erudio.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Executable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServicesTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonServices services;

    private Person person0;

    @BeforeEach
    public void setup(){
        person0 = new Person("Leandro", "Costa","leandro@erudio.com..br", "Minas Gerais", "Male");

    }

    //test[System Under Test]_[Condition or State Change]_[Expected Result]
        @DisplayName("Given Person Object When Save Person then Return Person Object")
        @Test
        void testGivenPersonObject_WhenSavePerson_thenReturnPersonObject() {
            // Given / Arrange
            given(repository.findByEmail(anyString())).willReturn(Optional.empty());
            given(repository.save(person0)).willReturn(person0);
            // When / Act
            Person savedPerson = services.create(person0);
            // Then / Assert
            assertNotNull(savedPerson);
            assertEquals("Leandro", savedPerson.getFirstName());
        }

        @DisplayName("Given Existing Email When Save Person then Throws Exception")
        @Test
        void testGivenExistingEmail_WhenSavePerson_thenThrowsException() {
            // Given / Arrange
            given(repository.findByEmail(anyString())).willReturn(Optional.of(person0));
            // When / Act
            assertThrows(ResourceNotFoundException.class, () -> {
                        services.create(person0);
            });
            // Then / Assert
            verify(repository, never()).save(any(Person.class));
        }

        @DisplayName("Given Person List When Find All Persons then Return Person List")
        @Test
        void testGivenPersonList_WhenFindAllPersons_thenReturnPersonList() {
            // Given / Arrange
            Person person1 = new Person("Leonardo", "Costa","leonardo@erudio.com.br", "Minas Gerais", "Male");

            given(repository.findAll()).willReturn(List.of(person0, person1));
            // When / Act
            List<Person> personList = services.findAll();
            // Then / Assert
            assertNotNull(personList);
            assertEquals(2, personList.size());
        }

        @DisplayName("Given Person Empty List When Find All Persons then Return Empty Person List")
        @Test
        void testGivenEmptyPersonList_WhenFindAllPersons_thenReturnEmptyPersonList() {
            // Given / Arrange
            given(repository.findAll()).willReturn(Collections.emptyList());
            // When / Act
            List<Person> personList = services.findAll();
            // Then / Assert
            assertTrue(personList.isEmpty());
            assertEquals(0, personList.size());
        }

        @DisplayName("Given Person Id When FindById then Return Person Object")
        @Test
        void testGivenPersonId_WhenFindById_thenReturnPersonObject() {
            // Given / Arrange
            given(repository.findById(anyLong())).willReturn(Optional.of(person0));
            // When / Act
            Person savedPerson = services.findById(1L);
            // Then / Assert
            assertNotNull(savedPerson);
            assertEquals("Leandro", savedPerson.getFirstName());
        }

        @DisplayName("Given Person Object When Update Person then Return Update Person Object")
        @Test
        void testGivenPersonObject_WhenUpdatePerson_thenReturnUpdatePersonObject() {
            // Given / Arrange
            person0.setId(1L);
            given(repository.findById(anyLong())).willReturn(Optional.of(person0));

            person0.setFirstName("Leonardo");
            person0.setEmail("leonardo@erudio.com.br");

            given(repository.save(person0)).willReturn(person0);
            // When / Act
            Person updatePerson = services.update(person0);
            // Then / Assert
            assertNotNull(updatePerson);
            assertEquals("Leonardo", updatePerson.getFirstName());
        }


        @DisplayName("Given Person ID When Delete Person then do nothing")
        @Test
        void testGivenPersonID_WhenDeletePerson_thenDoNothing() {
            // Given / Arrange
            person0.setId(1L);
            given(repository.findById(anyLong())).willReturn(Optional.of(person0));

            // When / Act
            services.delete(person0.getId());
            // Then / Assert
            verify(repository, times(1)).delete(person0);
        }

}
