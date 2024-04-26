package com.ua.hlibkorobov.java_practical_test_assignment.service;

import com.ua.hlibkorobov.java_practical_test_assignment.domain.User;
import com.ua.hlibkorobov.java_practical_test_assignment.dto.UserDto;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    User saveUser(User user);

    void deleteUser(long id);

    List<User> findUsersByRangeOfDate(LocalDate fromDate, LocalDate toDate);

    User updateUser(Long userId, UserDto userDto);
}
