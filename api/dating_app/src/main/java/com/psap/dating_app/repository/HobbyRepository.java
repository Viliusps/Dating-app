package com.psap.dating_app.repository;

import com.psap.dating_app.model.Hobby;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Long> {
    public List<Hobby> findAllByOrderByIdAsc();
}