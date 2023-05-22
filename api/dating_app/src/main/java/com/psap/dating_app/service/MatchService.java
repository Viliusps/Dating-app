package com.psap.dating_app.service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import com.psap.dating_app.model.Couple;
import com.psap.dating_app.repository.CoupleRepository;


@AllArgsConstructor
@Service
public class MatchService {
    private final CoupleRepository coupleRepository;



    public Couple getMatch(long id) {
        Couple couple = coupleRepository.getMatch(id);
        return couple;
    }

    public Couple getUndecidedCouple(long id) {
        List<Couple> couples = coupleRepository.findCouplesByUser(id);
        if (couples.isEmpty()) return null;
        int i = 0;
        Couple current = couples.get(i++);
        while (current.getStatus().toString() != "UNDECIDED" && i < couples.size()) {

            current = couples.get(i);
            i++;
        }

        return current.getStatus().toString() == "UNDECIDED" ? current : null;
    }
}
