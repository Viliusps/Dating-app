package com.psap.dating_app.repository;

import com.psap.dating_app.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

/*     @Query(
        value = "SELECT u.* FROM couples AS c INNER JOIN users AS u ON (c.first = u.id OR c.second = u.id) WHERE (c.first = :id OR c.second = :id) AND u.id != :id AND c.status = 'RECOMMENDED' ORDER BY c.weight_diff ASC",
        nativeQuery = true
    )
    public List<User> getRecommendations(@Param("id") long id); */

    @Query(
        value = "SELECT u.* FROM couples AS c INNER JOIN users AS u ON (c.second = u.id) WHERE c.first = :id AND u.id != :id AND c.status = 'RECOMMENDED' ORDER BY c.weight_diff ASC",
        nativeQuery = true
    )
    public List<User> getRecommendations(@Param("id") long id); 



/*     @Query(
    value = "SELECT u.* FROM (SELECT DISTINCT ON (u.id) u.id as user_id, c.weight_diff FROM couples AS c INNER JOIN users AS u ON (c.first = u.id OR c.second = u.id) WHERE (c.first = :id OR c.second = :id) AND u.id != :id AND c.status = 'RECOMMENDED' ORDER BY u.id, c.weight_diff ASC) as sub INNER JOIN users AS u ON u.id = sub.user_id ORDER BY sub.weight_diff ASC",
    nativeQuery = true
)
public List<User> getRecommendations(@Param("id") long id); */





}