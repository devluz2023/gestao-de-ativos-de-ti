package br.com.faluz.gestaodeativosdeti.repository;

import br.com.faluz.gestaodeativosdeti.model.Aquisicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AquisicaoRepository extends JpaRepository<Aquisicao, String> {
}
