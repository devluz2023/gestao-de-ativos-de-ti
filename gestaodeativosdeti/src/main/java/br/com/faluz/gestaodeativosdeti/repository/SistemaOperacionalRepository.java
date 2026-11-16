package br.com.faluz.gestaodeativosdeti.repository;

import br.com.faluz.gestaodeativosdeti.model.SistemaOperacional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemaOperacionalRepository extends JpaRepository<SistemaOperacional, Long> {
}
