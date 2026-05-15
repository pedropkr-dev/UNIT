package br.com.itstime.controller;

import br.com.itstime.model.Assinatura;
import br.com.itstime.service.AssinaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/assinaturas")
@CrossOrigin(origins = "*")
public class AssinaturaController {
    @Autowired
    private AssinaturaService planoService;

    @GetMapping
    public List<Assinatura> listar() {
        return planoService.listaTodos();
    }

    @PostMapping
    public ResponseEntity<?> contratar(@RequestBody Map<String, Object> dados) {
        try {

            if (dados.get("valorMensal") == null) {
                return ResponseEntity.badRequest().body(Map.of("erro", "valorMensal obrigatorio"));
            }
            if (dados.get("suportePrioritario") == null) {
                return ResponseEntity.badRequest().body(Map.of("erro", "suportePrioritario obrigatorio"));
            }
            if (dados.get("metodoPagamento") == null) {
                return ResponseEntity.badRequest().body(Map.of("erro", "metodoPagamento obrigatorio"));
            }
            if (dados.get("diasValidade") == null) {
                return ResponseEntity.badRequest().body(Map.of("erro", "diasValidade obrigatorio"));
            }

            float valor = Float.parseFloat(dados.get("valorMensal").toString());
            boolean suporte = Boolean.parseBoolean(dados.get("suportePrioritario").toString());
            String metodo = dados.get("metodoPagamento").toString();
            int dias = Integer.parseInt(dados.get("diasValidade").toString());

            Assinatura nova = planoService.contratar(valor, suporte, metodo, dias);
            return ResponseEntity.ok(nova);
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