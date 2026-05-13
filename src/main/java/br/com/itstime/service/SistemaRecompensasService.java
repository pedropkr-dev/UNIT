package br.com.itstime.service;

import br.com.itstime.model.SistemaRecompensas;
import br.com.itstime.repository.SistemaRecompensasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SistemaRecompensasService {
    @Autowired
    private SistemaRecompensasRepository sistemaRepository;

    public SistemaRecompensas criar() {
        SistemaRecompensas sistema = new SistemaRecompensas();
        return sistemaRepository.save(sistema);
    }

    public SistemaRecompensas resgatarPremio(UUID idSistema, String nomePremio) {
        SistemaRecompensas sistema = buscarPorId(idSistema);

        if (nomePremio == null || nomePremio.isBlank()) {
            throw new RuntimeException("Nome do prêmio obrigatório");
        }

        sistema.resgatarPremio(nomePremio);
        return sistemaRepository.save(sistema);
    }

    //Regra de negócio: Quando acumula pontos, o badge sobe.
    public SistemaRecompensas atualizarBadge(UUID idSistema, int pontosTotais) {
        SistemaRecompensas sistema = buscarPorId(idSistema);

        String novaBadge;
        if (pontosTotais >= 1000) {
            novaBadge = "Grão-Mestre";
            sistema.setMultiplicadorPontos(3);
        } else if (pontosTotais >= 500) {
            novaBadge = "Mestre";
            sistema.setMultiplicadorPontos(2);
        } else if (pontosTotais >= 100) {
            novaBadge = "Intermediário";
        } else {
            novaBadge = "Iniciante";
            sistema.setMultiplicadorPontos(1);
        }

        sistema.setBadgeAtual(novaBadge);
        return sistemaRepository.save(sistema);
    }

    public SistemaRecompensas buscarPorId(UUID id) {
        return sistemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sistema de recompensas não encontrado"));
    }
    public List<SistemaRecompensas> listaTodos() {
        return sistemaRepository.findAll();
    }
}
