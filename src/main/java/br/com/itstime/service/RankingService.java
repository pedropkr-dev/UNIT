package br.com.itstime.service;

import br.com.itstime.model.Grupo;
import br.com.itstime.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class RankingService {

    @Autowired
    private GrupoService grupoService;

    //Retorna a lista de usuários do grupo, ordenada por pontos (maior para menor)
    public List<Usuario> rankingDoGrupo(UUID idGrupo) {
        Grupo grupo = grupoService.buscarPorId(idGrupo);

        return grupo.getMembros().stream()
                .sorted(Comparator.comparingInt(Usuario::getPontosTotais).reversed())
                .toList();
    }
    //Retorna a posição de um usuário específico no ranking do grupo
    public int posicaoNoGrupo(UUID idGrupo, UUID idUsuario) {
        List<Usuario> ranking = rankingDoGrupo(idGrupo);

        for (int i = 0; i < ranking.size(); i++) {
            if (ranking.get(i).getId().equals(idUsuario)){
                return i + 1;
            }
        }
        throw new RuntimeException("Usuário não pertence ao grupo");
    }
}
