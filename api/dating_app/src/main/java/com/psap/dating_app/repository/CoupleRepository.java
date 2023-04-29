package com.psap.dating_app.repository;

import com.psap.dating_app.model.Couple;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CoupleRepository extends JpaRepository<Couple, Long> {
    public List<Couple> findAllByOrderByIdAsc();

        @Query(
        value = "SELECT * FROM couples c WHERE c.first=:id OR c.second=:id",
            nativeQuery = true
        )
    public Couple findCoupleByUser(long id);
}