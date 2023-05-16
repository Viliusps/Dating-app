package com.psap.dating_app.repository;

import com.psap.dating_app.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public List<User> findAllByOrderByIdAsc();

    public User findByEmail(String email);

    public User findById(long id);

    public Boolean existsByEmail(String email);

}