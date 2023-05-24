package com.psap.dating_app.repository;

import com.psap.dating_app.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public List<User> findAllByOrderByIdAsc();

    public User findByEmail(String email);

    public User findById(long id);

    public Boolean existsByEmail(String email);

    @Query(
        value = "SELECT u.* FROM users u WHERE u.id IN (SELECT c.first FROM couples c WHERE c.second=:id AND (c.status='DISLIKES' OR c.status='UNMATCHED')) UNION SELECT u.* FROM users u WHERE u.id IN (SELECT c.second FROM couples c WHERE c.first=:id AND (c.status='DISLIKES' OR c.status='UNMATCHED'))",
        nativeQuery = true
    )
    public List<User> getUnmatchesAndDislikes(long id);




}