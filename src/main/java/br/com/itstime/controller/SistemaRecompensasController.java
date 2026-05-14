package br.com.itstime.controller;

import br.com.itstime.model.SistemaRecompensas;
import br.com.itstime.service.SistemaRecompensasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/recompensas")
@CrossOrigin(origins = "*")
public class SistemaRecompensasController{

    @Autowired
    private SistemaRecompensasService recompensasService;

    @GetMapping
    public List<SistemaRecompensas> listar() {
        return recompensasService.listaTodos();
    }

    @PostMapping
    public SistemaRecompensas criar() {
        return recompensasService.criar();
    }

    @PostMapping("/{id}/premio")
    public ResponseEntity<?> resgatarPremio(@PathVariable UUID id, @RequestBody Map<String, String> dados) {
        try {
            return ResponseEntity.ok(recompensasService.resgatarPremio(id, dados.get("nomePremio")));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
}
