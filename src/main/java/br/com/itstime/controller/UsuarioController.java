package br.com.itstime.controller;

import br.com.itstime.model.Usuario;
import br.com.itstime.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // permite que o frontend acesse de qualquer origem

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // GET /api/usuarios -> lista todos
    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listarTodos();
    }

    // GET /api/usuarios/{id} -> busca um usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable UUID id){
        try {
            return ResponseEntity.ok(usuarioService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
    // POST /api/usuarios -> cadastra novo usuario
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Map<String, String> dados) {
        try {
            Usuario novo = usuarioService.cadastrar(
                    dados.get("nome"),
                    dados.get("email"),
                    dados.get("senha")
            );
            return ResponseEntity.ok(novo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // POST /api/usuarios/login -> faz login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> dados) {
        try {
            Usuario usuario = usuarioService.fazerLogin(
                    dados.get("email"),
                    dados.get("senha")
            );
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // PUT /api/usuarios/{id} -> atualiza perfil
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable UUID id, @RequestBody Map<String, String> dados) {
        try {
            Usuario atualizado = usuarioService.atualizarPerfil(id, dados.get("nome"), dados.get("email"));
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }


    // DELETE /api/usuarios/{id} -> deleta usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable UUID id) {
        try {
            usuarioService.deletar(id);
            return ResponseEntity.ok(Map.of("mensagem", "Usuario deletado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

}


