package be.fedei91.kookboek.services;

import be.fedei91.kookboek.domain.Recept;
import be.fedei91.kookboek.exceptions.ReceptNietGevondenException;
import be.fedei91.kookboek.repositories.ReceptRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
class DefaultReceptService implements ReceptService {
    private final ReceptRepository receptRepository;

    DefaultReceptService(ReceptRepository receptRepository) {
        this.receptRepository = receptRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Recept> findById(long id) {
        return receptRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recept> findAll() {
        return receptRepository.findAll();
    }

    @Override
    public void create(Recept recept) {
        receptRepository.save(recept);
    }

    @Override
    public void update(Recept recept) {
        receptRepository.save(recept);
    }

    @Override
    public void delete(long id) {
        try {
            receptRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ReceptNietGevondenException();
        }
    }
}
