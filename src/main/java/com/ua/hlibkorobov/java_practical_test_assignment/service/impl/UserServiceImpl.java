package com.ua.hlibkorobov.java_practical_test_assignment.service.impl;

import com.ua.hlibkorobov.java_practical_test_assignment.domain.User;
import com.ua.hlibkorobov.java_practical_test_assignment.dto.UserDto;
import com.ua.hlibkorobov.java_practical_test_assignment.exception.InvalidRangeOfDatesException;
import com.ua.hlibkorobov.java_practical_test_assignment.exception.UserNotFoundException;
import com.ua.hlibkorobov.java_practical_test_assignment.exception.UserWasNotFoundException;
import com.ua.hlibkorobov.java_practical_test_assignment.repository.UserRepository;
import com.ua.hlibkorobov.java_practical_test_assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of UserService interface providing CRUD operations for users.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Saves a new user to the database.
     *
     * @param user The user object to be saved.
     * @return The saved user object.
     */
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Updates an existing user with the provided user data.
     *
     * @param updatedUser The updated user object.
     * @param id The ID of the user to be updated.
     * @return The updated user object.
     * @throws UserWasNotFoundException if the user with the provided ID is not found.
     */
    @Override
    public User updateUser(User updatedUser, long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = updateFieldsForUser(updatedUser, optionalUser.get());

            return userRepository.save(user);
        } else {
            throw new UserWasNotFoundException("User wasn't found");
        }
    }

    private User updateFieldsForUser(User updatedUser, User user) {
        user.setAddress(updatedUser.getAddress());
        user.setEmail(updatedUser.getEmail());
        user.setBirthDate(updatedUser.getBirthDate());
        user.setLastName(updatedUser.getLastName());
        user.setFirstName(updatedUser.getFirstName());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        return user;
    }

    /**
     * Deletes a user from the database.
     *
     * @param id The ID of the user to be deleted.
     * @throws UserWasNotFoundException if the user with the provided ID is not found.
     */
    @Override
    public void deleteUser(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
        } else {
            throw new UserWasNotFoundException("User wasn't found");
        }
    }

    /**
     * Finds users within a specified range of birthdays.
     *
     * @param fromDate The start date of the range.
     * @param toDate The end date of the range.
     * @return A list of users born within the specified range of dates.
     * @throws InvalidRangeOfDatesException if the provided date range is invalid.
     */
    @Override
    public List<User> findUsersByRangeOfDate(LocalDate fromDate, LocalDate toDate) {
        if (fromDate.isBefore(toDate)) {
            return userRepository.findByBirthDateBetween(fromDate, toDate);
        } else {
            throw new InvalidRangeOfDatesException("Invalid range of dates.");
        }
    }

    /**
     * Updates an existing user with the provided data in UserDto.
     *
     * @param userId The ID of the user to be updated.
     * @param userDto The DTO object containing updated user data.
     * @return The updated user object.
     * @throws UserNotFoundException if the user with the provided ID is not found.
     */
    @Override
    public User updateUser(Long userId, UserDto userDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (userDto.getEmail() != null) {
            existingUser.setEmail(userDto.getEmail());
        }
        if (userDto.getFirstName() != null) {
            existingUser.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            existingUser.setLastName(userDto.getLastName());
        }
        if (userDto.getBirthDate() != null) {
            existingUser.setBirthDate(userDto.getBirthDate());
        }
        if (userDto.getAddress() != null) {
            existingUser.setAddress(userDto.getAddress());
        }
        if (userDto.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(userDto.getPhoneNumber());
        }

        return userRepository.save(existingUser);
    }
}
