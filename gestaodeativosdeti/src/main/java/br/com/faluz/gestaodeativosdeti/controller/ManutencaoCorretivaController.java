package br.com.faluz.gestaodeativosdeti.controller;

import br.com.faluz.gestaodeativosdeti.model.ManutencaoCorretiva;
import br.com.faluz.gestaodeativosdeti.service.ManutencaoCorretivaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/manutencoes-corretivas")
@RequiredArgsConstructor
public class ManutencaoCorretivaController {
    private final ManutencaoCorretivaService service;

    @GetMapping
    public List<ManutencaoCorretiva> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public ResponseEntity<ManutencaoCorretiva> buscar(@PathVariable Long id) {
        return service.buscar(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ManutencaoCorretiva> criar(@RequestBody ManutencaoCorretiva obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.buscar(id).isEmpty()) return ResponseEntity.notFound().build();
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
