package br.com.faluz.gestaodeativosdeti.repository;

import br.com.faluz.gestaodeativosdeti.model.Utiliza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilizaRepository extends JpaRepository<Utiliza, Long> {
}
