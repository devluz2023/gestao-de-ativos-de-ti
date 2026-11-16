package br.com.faluz.gestaodeativosdeti.service;

import br.com.faluz.gestaodeativosdeti.model.Computador;
import br.com.faluz.gestaodeativosdeti.repository.ComputadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComputadorService {
    private final ComputadorRepository repository;
    public List<Computador> listar() { return repository.findAll(); }
    public Optional<Computador> buscar(String id) { return repository.findById(id); }
    public Computador salvar(Computador obj) { return repository.save(obj); }
    public void deletar(String id) { repository.deleteById(id); }
}
