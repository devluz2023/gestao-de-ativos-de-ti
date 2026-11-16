package br.com.faluz.gestaodeativosdeti.repository;

import br.com.faluz.gestaodeativosdeti.model.ManutencaoCorretiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManutencaoCorretivaRepository extends JpaRepository<ManutencaoCorretiva, Long> {
}
