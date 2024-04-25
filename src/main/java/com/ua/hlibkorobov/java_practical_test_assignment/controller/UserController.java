package com.ua.hlibkorobov.java_practical_test_assignment.controller;

import com.ua.hlibkorobov.java_practical_test_assignment.domain.User;
import com.ua.hlibkorobov.java_practical_test_assignment.dto.UserDto;
import com.ua.hlibkorobov.java_practical_test_assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@Log4j2
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        log.info("save user: {}", user);
        return new ResponseEntity<>(userService.saveUser(user), OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateSomeFields(@RequestBody UserDto userDto, @PathVariable long id) {
        log.info("update some fields: {}", userDto);
        return new ResponseEntity<>(userService.updateUser(id, userDto), OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable long id) {
        log.info("update User: {}", user);
        return new ResponseEntity<>(userService.updateUser(user, id), OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        log.info("delete User by id: {}", id);
        userService.deleteUser(id);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/{from}/{to}")
    public ResponseEntity<List<User>> getUsersByPeriod(@PathVariable LocalDate from, @PathVariable LocalDate to) {
        log.info("find users from - {} to - {}", from, to);
        return new ResponseEntity<>(userService.findUsersByRangeOfDate(from, to), OK);
    }
}
