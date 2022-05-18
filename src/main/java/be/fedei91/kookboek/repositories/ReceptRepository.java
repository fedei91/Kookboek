package be.fedei91.kookboek.repositories;

import be.fedei91.kookboek.domain.Recept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceptRepository extends JpaRepository<Recept, Long> {
}
