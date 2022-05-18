package be.fedei91.kookboek.services;

import be.fedei91.kookboek.domain.Recept;

import java.util.List;
import java.util.Optional;

public interface ReceptService {
    Optional<Recept> findById(long id);
    List<Recept> findAll();
    void create(Recept recept);
    void update(Recept recept);
    void delete(long id);
}
