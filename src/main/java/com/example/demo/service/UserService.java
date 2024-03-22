package com.example.demo.service;

import com.example.demo.controller.request.UserRequest;
import com.example.demo.controller.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.CustomException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("There is no such user", HttpStatus.NOT_FOUND));
        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("There is no such user", HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll(Pageable pageable) {
        try {
            return userRepository.findAll(pageable).map(userMapper::toDto).toList();
        } catch (Exception ex) {
            throw new CustomException("Error during finding all users", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public UserResponse save(UserRequest userDto) {
        try {
            User entity = userMapper.toEntity(userDto);
            return userMapper.toDto(userRepository.save(entity));
        } catch (Exception ex) {
            throw new CustomException("Error during saving user", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void update(Long id, UserRequest userDto) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new CustomException("There is no such user", HttpStatus.NOT_FOUND));
            User mappedUser = userMapper.toEntity(userDto);
            updateUser(user, mappedUser);
            userRepository.save(user);
        } catch (Exception ex) {
            throw new CustomException("Error during updating user", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception ex) {
            throw new CustomException("Error during updating user", HttpStatus.BAD_REQUEST);
        }
    }

    private void updateUser(User user, User mappedUser) {
        user.setName(mappedUser.getName());
        user.setSurname(mappedUser.getSurname());
        user.setBirthDate(mappedUser.getBirthDate());
        user.setUsername(mappedUser.getUsername());
        user.setPassword(mappedUser.getPassword());
        user.setRoles(mappedUser.getRoles());
    }

}
