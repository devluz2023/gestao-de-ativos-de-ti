package br.com.faluz.gestaodeativosdeti.service;

import br.com.faluz.gestaodeativosdeti.model.Utiliza;
import br.com.faluz.gestaodeativosdeti.repository.UtilizaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilizaService {
    private final UtilizaRepository repository;
    public List<Utiliza> listar() { return repository.findAll(); }
    public Optional<Utiliza> buscar(Long id) { return repository.findById(id); }
    public Utiliza salvar(Utiliza obj) { return repository.save(obj); }
    public void deletar(Long id) { repository.deleteById(id); }
}
