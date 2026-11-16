package br.com.faluz.gestaodeativosdeti.service;

import br.com.faluz.gestaodeativosdeti.model.ManutencaoCorretiva;
import br.com.faluz.gestaodeativosdeti.repository.ManutencaoCorretivaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManutencaoCorretivaService {
    private final ManutencaoCorretivaRepository repository;
    public List<ManutencaoCorretiva> listar() { return repository.findAll(); }
    public Optional<ManutencaoCorretiva> buscar(Long id) { return repository.findById(id); }
    public ManutencaoCorretiva salvar(ManutencaoCorretiva obj) { return repository.save(obj); }
    public void deletar(Long id) { repository.deleteById(id); }
}
