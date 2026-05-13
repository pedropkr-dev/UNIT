package br.com.itstime.service;

import br.com.itstime.model.PlanoPremium;
import br.com.itstime.repository.PlanoPremiumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import  java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class PlanoPremiumService {
    @Autowired
    private PlanoPremiumRepository planoRepository;

    public PlanoPremium contratar(float valorMensal, boolean suportePrioritario, String metodoPagamento, int diasValidade) {
        if (valorMensal <= 0) {
            throw new RuntimeException("Valor mensal deve ser maior que zero");
        }

        LocalDateTime dataExpiracao = LocalDateTime.now().plusDays(diasValidade);
        PlanoPremium novoPlano = new PlanoPremium(valorMensal, suportePrioritario, dataExpiracao, metodoPagamento);


        // Processa a assinatura
        if (!novoPlano.processarAssinatura()) {
            throw new RuntimeException("Falha ao processar assinatura");
        }

        return planoRepository.save(novoPlano);
    }

    public boolean verificarAtivo(UUID idPlano) {
        PlanoPremium plano = buscarPorId(idPlano);
        return plano.verificarStatus();
    }

    public PlanoPremium renovar(UUID idPlano, int diasAdicionais) {
        PlanoPremium plano = buscarPorId(idPlano);
        plano.setDataExpiracao(plano.getDataExpiracao().plusDays(diasAdicionais));
        return planoRepository.save(plano);
    }

    public PlanoPremium buscarPorId(UUID id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));
    }

    public List<PlanoPremium> listaTodos() {
        return planoRepository.findAll();
    }

    public void cancelar (UUID id) {
        PlanoPremium plano = buscarPorId(id);
        planoRepository.delete(plano);
    }
}
