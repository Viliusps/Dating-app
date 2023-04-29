package com.psap.dating_app.service;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.psap.dating_app.model.User;
import com.psap.dating_app.repository.UserRepository;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAllByOrderByIdAsc();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).get();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public boolean existsUser(long id) {
        return userRepository.existsById(id);
    }

    public User updateUser(Long id, User user) {
        User userFromDb = userRepository.findById(id).get();
        userFromDb.setBirthDate(user.getBirthDate());
        userFromDb.setBlockEnd(user.getBlockEnd());
        userFromDb.setBlocked(user.getBlocked());
        userFromDb.setDescription(user.getDescription());
        userFromDb.setEmail(user.getEmail());
        userFromDb.setGender(user.getGender());
        userFromDb.setHeight(user.getHeight());
        userFromDb.setLoveLanguage(user.getLoveLanguage());
        userFromDb.setMatchPurpose(user.getMatchPurpose());
        userFromDb.setName(user.getName());
        userFromDb.setPassword(user.getPassword());
        userFromDb.setPersonalityType(user.getPersonalityType());
        userFromDb.setPhone(user.getPhone());
        userFromDb.setPicture(user.getPicture());
        userFromDb.setPoints(user.getPoints());
        userFromDb.setRadius(user.getRadius());
        userFromDb.setRole(user.getRole());
        userFromDb.setSearchGender(user.getSearchGender());
        userFromDb.setStarSign(user.getStarSign());
        userFromDb.setSurname(user.getSurname());
        return userRepository.save(userFromDb);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
