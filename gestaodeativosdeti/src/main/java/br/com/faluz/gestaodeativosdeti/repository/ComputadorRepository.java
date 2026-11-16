package br.com.faluz.gestaodeativosdeti.repository;

import br.com.faluz.gestaodeativosdeti.model.Computador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputadorRepository extends JpaRepository<Computador, String> {
}
