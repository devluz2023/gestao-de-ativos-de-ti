package br.com.faluz.gestaodeativosdeti.service;

import br.com.faluz.gestaodeativosdeti.model.Problema;
import br.com.faluz.gestaodeativosdeti.repository.ProblemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProblemaService {
    private final ProblemaRepository repository;
    public List<Problema> listar() { return repository.findAll(); }
    public Optional<Problema> buscar(Long id) { return repository.findById(id); }
    public Problema salvar(Problema obj) { return repository.save(obj); }
    public void deletar(Long id) { repository.deleteById(id); }
}
