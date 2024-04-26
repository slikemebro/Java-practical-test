package com.ua.hlibkorobov.java_practical_test_assignment.service.impl;

import com.ua.hlibkorobov.java_practical_test_assignment.domain.User;
import com.ua.hlibkorobov.java_practical_test_assignment.dto.UserDto;
import com.ua.hlibkorobov.java_practical_test_assignment.exception.InvalidDateException;
import com.ua.hlibkorobov.java_practical_test_assignment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserServiceImplTest {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImplTest(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void saveValidUserTest() {
        User user = new User();

        user.setEmail("gkorobov9@gmail.com");
        user.setFirstName("Hlib");
        user.setLastName("Korobov");
        user.setBirthDate(LocalDate.of(2004, 1, 10));

        assertEquals(user, userService.saveUser(user));
    }

    @Test
    void saveUserInvalidEmailTest() {
        User user = new User();

        user.setEmail("username.@domain.com");
        user.setFirstName("Hlib");
        user.setLastName("Korobov");
        user.setBirthDate(LocalDate.of(2004, 1, 10));

        assertThrows(TransactionSystemException.class, () -> userService.saveUser(user));

        user.setEmail(".user.name@domain.com");
        assertThrows(TransactionSystemException.class, () -> userService.saveUser(user));

        user.setEmail("user-name@domain.com.");
        assertThrows(TransactionSystemException.class, () -> userService.saveUser(user));

        user.setEmail("username@.com");
        assertThrows(TransactionSystemException.class, () -> userService.saveUser(user));
    }

    @Test
    void saveUserInvalidBirthDayTest() {
        User user = new User();

        user.setEmail("gkorobov9@gmail.com");
        user.setFirstName("Hlib");
        user.setLastName("Korobov");
        user.setBirthDate(LocalDate.now());

        assertThrows(InvalidDateException.class, () -> userService.saveUser(user));

        user.setBirthDate(null);
        assertThrows(InvalidDateException.class, () -> userService.saveUser(user));

        user.setBirthDate(LocalDate.now().minusYears(1));
        assertThrows(InvalidDateException.class, () -> userService.saveUser(user));
    }

    @Test
    void saveUserWithOutRequiredFieldsTest() {
        User user = new User();

        user.setEmail("gkorobov9@gmail.com");
        user.setFirstName(null);
        user.setLastName("Korobov");
        user.setBirthDate(LocalDate.of(2004, 1, 10));

        assertThrows(TransactionSystemException.class, () -> userService.saveUser(user));

        user.setFirstName("Hlib");
        user.setBirthDate(null);
        assertThrows(InvalidDateException.class, () -> userService.saveUser(user));

        user.setBirthDate(LocalDate.of(2004, 1, 10));
        user.setLastName(null);
        assertThrows(TransactionSystemException.class, () -> userService.saveUser(user));

        user.setLastName("Korobov");
        user.setEmail(null);
        assertThrows(TransactionSystemException.class, () -> userService.saveUser(user));
    }

    @Test
    void updateUserTest() {
        User user = new User();

        user.setEmail("gkorobov9@gmail.com");
        user.setFirstName("Hlib");
        user.setLastName("Korobov");
        user.setBirthDate(LocalDate.of(2004, 1, 10));

        assertEquals(user, userService.saveUser(user));

        UserDto updatedUser = new UserDto();

        updatedUser.setEmail("gkorobov10@gmail.com");
        updatedUser.setFirstName("Makar");
        updatedUser.setLastName("Schevchenko");
        updatedUser.setBirthDate(LocalDate.of(2005, 2, 2));

        assertNotEquals(updatedUser, userService.updateUser(user.getId(), updatedUser));
    }

    @Test
    void deleteUserTest() {
        User user = new User();

        user.setEmail("gkorobov9@gmail.com");
        user.setFirstName("Hlib");
        user.setLastName("Korobov");
        user.setBirthDate(LocalDate.of(2004, 1, 10));

        assertEquals(user, userService.saveUser(user));

        userService.deleteUser(user.getId());

        assertEquals(0, userRepository.findAll().size());
    }

    @Test
    void findUsersByRange() {
        User user = new User();

        user.setEmail("gkorobov9@gmail.com");
        user.setFirstName("Hlib");
        user.setLastName("Korobov");
        user.setBirthDate(LocalDate.of(2004, 1, 10));

        assertEquals(user, userService.saveUser(user));

        assertEquals(1, userService.findUsersByRangeOfDate(
                LocalDate.of(2003, 1, 10),
                LocalDate.of(2005, 1, 10)).size());

        User userSecond = new User();

        userSecond.setEmail("gkorobov9@gmail.com");
        userSecond.setFirstName("Hlib");
        userSecond.setLastName("Korobov");
        userSecond.setBirthDate(LocalDate.of(2002, 1, 10));

        User userThird = new User();

        userThird.setEmail("gkorobov9@gmail.com");
        userThird.setFirstName("Hlib");
        userThird.setLastName("Korobov");
        userThird.setBirthDate(LocalDate.of(2004, 3, 10));

        assertEquals(userSecond, userService.saveUser(userSecond));
        assertEquals(userThird, userService.saveUser(userThird));

        assertEquals(2, userService.findUsersByRangeOfDate(
                LocalDate.of(2003, 1, 10),
                LocalDate.of(2005, 1, 10)).size());
    }

    @Test
    void updateSomeFieldsForUserTest() {
        User user = new User();

        user.setEmail("gkorobov9@gmail.com");
        user.setFirstName("Hlib");
        user.setLastName("Korobov");
        user.setBirthDate(LocalDate.of(2004, 1, 10));

        assertEquals(user, userService.saveUser(user));

        UserDto userDto = new UserDto();

        userDto.setFirstName("Alex");

        assertEquals(userDto.getFirstName(),
                userService.updateUser(user.getId(), userDto).getFirstName());

        userDto.setEmail("Invalid email");

        assertThrows(TransactionSystemException.class,
                () -> userService.updateUser(user.getId(), userDto));
    }
}