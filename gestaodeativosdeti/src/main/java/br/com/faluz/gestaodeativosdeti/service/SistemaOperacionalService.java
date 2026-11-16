package br.com.faluz.gestaodeativosdeti.service;

import br.com.faluz.gestaodeativosdeti.model.SistemaOperacional;
import br.com.faluz.gestaodeativosdeti.repository.SistemaOperacionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SistemaOperacionalService {
    private final SistemaOperacionalRepository repository;
    public List<SistemaOperacional> listar() { return repository.findAll(); }
    public Optional<SistemaOperacional> buscar(Long id) { return repository.findById(id); }
    public SistemaOperacional salvar(SistemaOperacional obj) { return repository.save(obj); }
    public void deletar(Long id) { repository.deleteById(id); }
}
