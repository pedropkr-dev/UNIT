package br.com.itstime.service;

import br.com.itstime.model.Usuario;
import br.com.itstime.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //CADASTRO//
    public Usuario cadastrar(String nome, String email, String senha) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("Email já cadastrado: " + email);
        }

        if (senha == null || senha.length() < 6){
            throw new RuntimeException("Senha deve ter no mínimo 6 caracteres");
        }

        Usuario novoUsuario = new Usuario(nome, email, senha);
        return usuarioRepository.save(novoUsuario);
    }

    //LOGIN//
    public Usuario fazerLogin(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if(usuarioOpt.isEmpty()){
            throw new RuntimeException("Usuario não encontrado");
        }
        Usuario usuario = usuarioOpt.get();
        if (!usuario.fazerLogin(email, senha)){
            throw new RuntimeException("Senha incorreta");
        }
        return usuario;
    }
    //BUSCAR//
    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado com id: " + id));

    }
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    //ATUALIZAR//
    public Usuario atualizarPerfil(UUID id, String novoNome, String novoEmail) {
        Usuario usuario = buscarPorId(id);
        usuario.atualizarPerfil(novoNome, novoEmail);
        return usuarioRepository.save(usuario);
    }

    //ADICIONAR PONTOS//
    public Usuario adicionarPontos(UUID idUsuario, int quantidade) {
        Usuario usuario = buscarPorId(idUsuario);
        usuario.setPontosTotais(usuario.getPontosTotais() + quantidade);
        return usuarioRepository.save(usuario);
    }

    //DELETAR//
    public void deletar(UUID id) {
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
    }
}
