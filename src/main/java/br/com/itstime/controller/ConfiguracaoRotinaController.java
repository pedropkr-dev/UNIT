package br.com.itstime.controller;

import br.com.itstime.model.ConfiguracaoRotina;
import br.com.itstime.service.ConfiguracaoRotinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/configuracoes")
@CrossOrigin(origins = "*")
public class ConfiguracaoRotinaController {
    @Autowired
    private ConfiguracaoRotinaService configService;

    @GetMapping
    public List<ConfiguracaoRotina> listar() {
        return configService.listarTodas();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Map<String, String> dados) {
        try {
            LocalTime inicio = LocalTime.parse(dados.get("horarioInicio"));
            LocalTime fim = LocalTime.parse(dados.get("horarioFim"));
            String fuso = dados.get("fusoHorario");

            ConfiguracaoRotina nova = configService.criar(inicio, fim, fuso);
            return ResponseEntity.ok(nova);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable UUID id, @RequestBody Map<String, String> dados) {
        try {
            LocalTime novoInicio = LocalTime.parse(dados.get("horarioInicio"));
            LocalTime novoFim = LocalTime.parse(dados.get("horarioFim"));

            ConfiguracaoRotina atualizada = configService.atualizarHorarios(id, novoInicio, novoFim);
            return ResponseEntity.ok(atualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
}
