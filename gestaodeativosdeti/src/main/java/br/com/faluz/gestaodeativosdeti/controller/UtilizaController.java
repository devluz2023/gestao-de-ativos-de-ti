package br.com.faluz.gestaodeativosdeti.controller;

import br.com.faluz.gestaodeativosdeti.model.Utiliza;
import br.com.faluz.gestaodeativosdeti.service.UtilizaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/utiliza")
@RequiredArgsConstructor
public class UtilizaController {
    private final UtilizaService service;

    @GetMapping
    public List<Utiliza> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public ResponseEntity<Utiliza> buscar(@PathVariable Long id) {
        return service.buscar(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Utiliza> criar(@RequestBody Utiliza obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.buscar(id).isEmpty()) return ResponseEntity.notFound().build();
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
