package br.com.faluz.gestaodeativosdeti.service;

import br.com.faluz.gestaodeativosdeti.model.Aquisicao;
import br.com.faluz.gestaodeativosdeti.repository.AquisicaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AquisicaoService {
    private final AquisicaoRepository repository;
    public List<Aquisicao> listar() { return repository.findAll(); }
    public Optional<Aquisicao> buscar(String id) { return repository.findById(id); }
    public Aquisicao salvar(Aquisicao obj) { return repository.save(obj); }
    public void deletar(String id) { repository.deleteById(id); }
}
