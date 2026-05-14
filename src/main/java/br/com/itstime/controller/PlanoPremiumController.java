package br.com.itstime.controller;

import br.com.itstime.model.PlanoPremium;
import br.com.itstime.service.PlanoPremiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/planos")
@CrossOrigin(origins = "*")
public class PlanoPremiumController {
    @Autowired
    private PlanoPremiumService planoService;

    @GetMapping
    public List<PlanoPremium> listar() {
        return planoService.listaTodos();
    }

    @PostMapping
    public ResponseEntity<?> contratar(@RequestBody Map<String, Object> dados) {
        try {
            float valor = Float.parseFloat(dados.get("valorMensal").toString());
            boolean suporte = (Boolean) dados.get("suportePrioritário");
            String metodo = (String) dados.get("metodoPagamentos");
            int dias = (Integer) dados.get("diasValidade");

            PlanoPremium novo = planoService.contratar(valor, suporte, metodo, dias);
            return ResponseEntity.ok(novo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/{id}/ativo")
    public ResponseEntity<?> verificarAtivo(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(Map.of("ativo", planoService.verificarAtivo(id)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelar(@PathVariable UUID id) {
        try {
            planoService.cancelar(id);
            return ResponseEntity.ok(Map.of("mensagem", "Plano cancelado"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
}