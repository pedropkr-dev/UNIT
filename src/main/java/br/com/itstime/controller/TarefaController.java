package br.com.itstime.controller;

import br.com.itstime.model.Tarefa;
import br.com.itstime.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "*")

public class TarefaController {
    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public List<Tarefa> listar() {
        return tarefaService.listaTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(tarefaService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }


    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Map<String, Object> dados) {
        try {
            UUID idUsuario = UUID.fromString((String) dados.get("idUsuario"));
            String titulo = (String) dados.get("titulo");
            LocalDateTime prazo = LocalDateTime.parse((String) dados.get("prazo"));
            int prioridade = (Integer) dados.get("prioridade");

            Tarefa nova = tarefaService.criar(idUsuario, titulo, prazo, prioridade);
            return ResponseEntity.ok(nova);
        }   catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }


    @PatchMapping("/{id}/concluir")
    public ResponseEntity<?> concluir(@PathVariable UUID id, @RequestParam UUID idUsuario) {
        try {
            Tarefa tarefa = tarefaService.concluir(id, idUsuario);
            return ResponseEntity.ok(tarefa);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable UUID id, @RequestBody Map<String, Object> dados) {
        try {
            String novoTitulo = (String) dados.get("titulo");
            LocalDateTime novoPrazo = LocalDateTime.parse((String) dados.get("prazo"));
            int novaPrioridade = (Integer) dados.get("prioridade");

            Tarefa editada = tarefaService.editar(id, novoTitulo, novoPrazo, novaPrioridade);
            return ResponseEntity.ok(editada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable UUID id) {
        try {
            tarefaService.excluir(id);
            return ResponseEntity.ok(Map.of("mensagem", "Tarefa excluida com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/status/{concluida}")
    public List<Tarefa> listarPorStatus(@PathVariable boolean concluida) {
        return tarefaService.listarPorStatus(concluida);
    }
}
