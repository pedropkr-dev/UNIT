package br.com.itstime.service;

import br.com.itstime.model.Grupo;
import br.com.itstime.model.Usuario;
import br.com.itstime.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public Grupo criar(String nome, UUID idCriador) {
        if (nome == null || nome.isBlank()) {
            throw new RuntimeException("Nome do grupo obrigatório");
        }

        Usuario criador = usuarioService.buscarPorId(idCriador);
        Grupo novoGrupo = new Grupo(nome, criador);
        return grupoRepository.save(novoGrupo);
    }

    public Grupo adicionarMembro(UUID idGrupo, UUID idUsuario) {
        Grupo grupo = buscarPorId(idGrupo);
        Usuario novoMembro = usuarioService.buscarPorId(idUsuario);

        grupo.adicionarMembros(novoMembro);
        return grupoRepository.save(grupo);
    }

    public Grupo removerMembro(UUID idGrupo, UUID idUsuario) {
        Grupo grupo = buscarPorId(idGrupo);
        Usuario membro = usuarioService.buscarPorId(idUsuario);

        grupo.removerMembro(membro);
        return grupoRepository.save(grupo);
    }

    public Grupo buscarPorId(UUID id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));
    }

    public List<Grupo> listarPorUsuario(UUID idUsuario) {
        return grupoRepository.findByMembros_id(idUsuario);
    }

    public List<Grupo> listarTodos() {
        return grupoRepository.findAll();
    }

    public void deletar(UUID id) {
        Grupo grupo = buscarPorId(id);
        grupoRepository.delete(grupo);
    }
}