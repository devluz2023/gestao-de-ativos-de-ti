package br.com.faluz.gestaodeativosdeti.controller;

import br.com.faluz.gestaodeativosdeti.model.Aquisicao;
import br.com.faluz.gestaodeativosdeti.service.AquisicaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/aquisicoes")
@RequiredArgsConstructor
public class AquisicaoController {
    private final AquisicaoService service;

    @GetMapping
    public List<Aquisicao> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public ResponseEntity<Aquisicao> buscar(@PathVariable String id) {
        return service.buscar(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Aquisicao> criar(@RequestBody Aquisicao obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        if (service.buscar(id).isEmpty()) return ResponseEntity.notFound().build();
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
