package com.psap.dating_app.service;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.psap.dating_app.model.Couple;
import com.psap.dating_app.repository.CoupleRepository;

@AllArgsConstructor
@Service
public class CoupleService {
    private final CoupleRepository coupleRepository;

    public List<Couple> getAllCouples() {
        return coupleRepository.findAllByOrderByIdAsc();
    }

    public Couple getCoupleById(long id) {
        return coupleRepository.findById(id).get();
    }

    public Couple createCouple(Couple couple) {
        Couple newCouple = new Couple();
        newCouple.setId(couple.getId());
        newCouple.setDate(couple.getDate());
        newCouple.setFirst(couple.getFirst());
        newCouple.setSecond(couple.getSecond());
        newCouple.setWeightDiff(couple.getWeightDiff());
        return coupleRepository.save(newCouple);
    }

    public boolean existsCouple(long id) {
        return coupleRepository.existsById(id);
    }

    public Couple updateCouple(Long id, Couple couple) {
        Couple coupleFromDb = coupleRepository.findById(id).get();
        coupleFromDb.setDate(couple.getDate());
        coupleFromDb.setFirst(couple.getFirst());
        coupleFromDb.setSecond(couple.getSecond());
        coupleFromDb.setWeightDiff(couple.getWeightDiff());
        return coupleRepository.save(coupleFromDb);
    }

    public void deleteCouple(Long id) {
        coupleRepository.deleteById(id);
    }

    public Couple getCoupleByUserId(long id) {
        return coupleRepository.findCurrentCoupleByUserId(id);
    }
}
