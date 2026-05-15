package br.com.itstime.controller;

import br.com.itstime.model.Usuario;
import br.com.itstime.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/ranking")
@CrossOrigin(origins = "*")

public class RankingController {
    @Autowired
    private RankingService rankingService;

    @GetMapping("/grupo/{idGrupo}")
    public ResponseEntity<?> rankingDoGrupo(@PathVariable UUID idGrupo) {
        try {
            List<Usuario> ranking = rankingService.rankingDoGrupo(idGrupo);
            return ResponseEntity.ok(ranking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/grupo/{idGrupo}/posicao/{idUsuario}")
    public ResponseEntity<?> posicaoNoGrupo(@PathVariable UUID idGrupo, @PathVariable UUID idUsuario) {
        try {
            int posicao = rankingService.posicaoNoGrupo(idGrupo, idUsuario);
            return ResponseEntity.ok(Map.of("posicao", posicao));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
}
