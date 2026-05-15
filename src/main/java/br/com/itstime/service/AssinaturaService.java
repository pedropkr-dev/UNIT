package br.com.itstime.service;

import br.com.itstime.model.Assinatura;
import br.com.itstime.repository.AssinaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import  java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class AssinaturaService {
    @Autowired
    private AssinaturaRepository planoRepository;

    public Assinatura contratar(float valorMensal, boolean suportePrioritario, String metodoPagamento, int diasValidade) {
        if (valorMensal <= 0) {
            throw new RuntimeException("Valor mensal deve ser maior que zero");
        }

        LocalDateTime dataExpiracao = LocalDateTime.now().plusDays(diasValidade);
        Assinatura novoPlano = new Assinatura(valorMensal, suportePrioritario, dataExpiracao, metodoPagamento);


        // Processa a assinatura
        if (!novoPlano.processarAssinatura()) {
            throw new RuntimeException("Falha ao processar assinatura");
        }

        return planoRepository.save(novoPlano);
    }

    public boolean verificarAtivo(UUID idPlano) {
        Assinatura plano = buscarPorId(idPlano);
        return plano.verificarStatus();
    }

    public Assinatura renovar(UUID idPlano, int diasAdicionais) {
        Assinatura plano = buscarPorId(idPlano);
        plano.setDataExpiracao(plano.getDataExpiracao().plusDays(diasAdicionais));
        return planoRepository.save(plano);
    }

    public Assinatura buscarPorId(UUID id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));
    }

    public List<Assinatura> listaTodos() {
        return planoRepository.findAll();
    }

    public void cancelar (UUID id) {
        Assinatura plano = buscarPorId(id);
        planoRepository.delete(plano);
    }
}
