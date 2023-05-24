package com.psap.dating_app.repository;

import com.psap.dating_app.model.Couple;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import java.util.Date;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CoupleRepository extends JpaRepository<Couple, Long> {
    public List<Couple> findAllByOrderByIdAsc();

    @Query(
        value = "SELECT * FROM couples c WHERE c.first=:id OR c.second=:id",
            nativeQuery = true
        )
    public List<Couple> findCouplesByUser(long id);

    @Query(
        value = "SELECT * FROM couples c WHERE (c.first=:id OR c.second=:id) AND c.status = 'UNDECIDED'",
            nativeQuery = true
        )
    public List<Couple> getUndecidedCouples(long id);

    @Query(
        value = "SELECT * FROM couples c WHERE (c.first=:id OR c.second=:id) AND (c.status='DISLIKES' OR c.status='UNMATCHED')",
            nativeQuery = true
        )
    public List<Couple> getUnmatchesAndDislikes(long id);

    @Query(
        value = "SELECT * FROM couples c WHERE (c.first=:id OR c.second=:id) AND c.status = 'LIKES'",
            nativeQuery = true
        )
    public Couple findCurrentCoupleByUserId(long id);

    @Query(
        value = "SELECT * FROM couples",
            nativeQuery = true
        )
    public List<Couple> getAllCouples();

    @Query(
        value = "INSERT INTO couples (date, weight_diff, first, second, status, chat) VALUES (:date, :weight_diff, :first, :second, :status, :chat)",
            nativeQuery = true
        )
    public List<Couple> updateRecommendations(Date date, float weight_diff, int first, int second, String status, int chat );

    @Query(
        value = "SELECT * FROM couples c WHERE (c.first=:id OR c.second=:id) AND c.status = 'LIKES'",
            nativeQuery = true
        )
    public Couple getMatch(long id);

    @Query(
        value = "DELETE FROM couples c WHERE c.status = 'RECOMMENDED'",
            nativeQuery = true
        )
    public int deleteAllRecommendations(long id);

    @Query(
    value = "SELECT * FROM couples WHERE (first = :id OR second = :id) AND status = 'RECOMMENDED' AND weight_diff = (SELECT MAX(weight_diff) FROM couples WHERE (first = :id OR second = :id) AND status = 'RECOMMENDED')",
    nativeQuery = true
    )
    public Couple getRecommendation(long id);

    @Query(
        value = "SELECT * FROM couples WHERE (first = :id OR second = :id) AND status = 'RECOMMENDED'",
        nativeQuery = true
    )
    public List<Couple> getRecommendations(@Param("id") long id);
    

    @Modifying
    @Query(
    value = "UPDATE couples SET status = 'DISLIKES' WHERE id = :id",
    nativeQuery = true
    )
    @Transactional
    int setDislike(@Param("id") Long id);

    @Modifying
    @Query(
        value = "UPDATE couples SET status = 'LIKES' WHERE id = :id",
        nativeQuery = true
        )
    @Transactional
    int setLike(@Param("id") Long id);

    
}