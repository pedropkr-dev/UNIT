package br.com.itstime.controller;

import br.com.itstime.model.Grupo;
import br.com.itstime.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/grupos")
@CrossOrigin(origins = "*")

public class GrupoController {
    @Autowired
    private GrupoService grupoService;

    @GetMapping
    public List<Grupo> listar() {
        return grupoService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Map<String, String> dados) {
        try {
            String nome = dados.get("nome");
            UUID idCriador = UUID.fromString(dados.get("idCriador"));

            Grupo novo = grupoService.criar(nome, idCriador);
            return ResponseEntity.ok(novo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // POST /api/grupos/{id}/membros -> adiciona membro do grupo
    @PostMapping("/{id}/membros")
    public ResponseEntity<?> adicionarMembro(@PathVariable UUID id, @RequestBody Map<String, String> dados) {
        try {
            UUID idUsuario = UUID.fromString(dados.get("idUsuario"));
            return ResponseEntity.ok(grupoService.adicionarMembro(id, idUsuario));
        }   catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
    @DeleteMapping("/{id}/membros/{idUsuario}")
    public ResponseEntity<?> removerMembro(@PathVariable UUID id, @PathVariable UUID idUsuario) {
        try {
            return ResponseEntity.ok(grupoService.removerMembro(id, idUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
    @GetMapping("/usuario/{idUsuario}")
    public List<Grupo> listarPorUsuario(@PathVariable UUID idUsuario) {
        return grupoService.listarPorUsuario(idUsuario);
    }

}
