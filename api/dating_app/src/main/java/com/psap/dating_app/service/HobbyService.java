package com.psap.dating_app.service;
import java.util.List;

import com.psap.dating_app.model.Hobby;
import com.psap.dating_app.repository.HobbyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class HobbyService {
    private final HobbyRepository hobbyRepository;

    public List<Hobby> getAllHobbies() {
        return hobbyRepository.findAllByOrderByIdAsc();
    }

    public Hobby getHobbyById(long id) {
        return hobbyRepository.findById(id).get();
    }

    public Hobby createHobby(Hobby hobby) {
        Hobby newHobby = new Hobby();
        newHobby.setId(hobby.getId());
        newHobby.setName(hobby.getName());
        return hobbyRepository.save(newHobby);
    }

    public boolean existsHobby(long id) {
        return hobbyRepository.existsById(id);
    }

    public Hobby updateHobby(Long id, Hobby hobby) {
        Hobby hobbyFromDb = hobbyRepository.findById(id).get();
        hobbyFromDb.setName(hobby.getName());
        return hobbyRepository.save(hobbyFromDb);
    }

    public void deleteHobby(Long id) {
        hobbyRepository.deleteById(id);
    }
}
