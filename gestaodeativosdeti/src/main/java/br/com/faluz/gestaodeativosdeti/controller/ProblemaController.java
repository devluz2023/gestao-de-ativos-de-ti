package br.com.faluz.gestaodeativosdeti.controller;

import br.com.faluz.gestaodeativosdeti.model.Problema;
import br.com.faluz.gestaodeativosdeti.service.ProblemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/problemas")
@RequiredArgsConstructor
public class ProblemaController {
    private final ProblemaService service;

    @GetMapping
    public List<Problema> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public ResponseEntity<Problema> buscar(@PathVariable Long id) {
        return service.buscar(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Problema> criar(@RequestBody Problema obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.buscar(id).isEmpty()) return ResponseEntity.notFound().build();
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
