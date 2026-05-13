package br.com.itstime.service;

import br.com.itstime.model.ConfiguracaoRotina;
import br.com.itstime.repository.ConfiguracaoRotinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class ConfiguracaoRotinaService {
    @Autowired
    private ConfiguracaoRotinaRepository configRepository;

    public ConfiguracaoRotina criar(LocalTime inicio, LocalTime fim, String fusoHorario) {
        if (inicio.isAfter(fim)) {
            throw new RuntimeException("Horário de início não pode ser depois do horário de fim");
        }
        if (fusoHorario == null || fusoHorario.isBlank()) {
            throw new RuntimeException("Fuso horário obrigatório");
        }

        ConfiguracaoRotina config = new ConfiguracaoRotina(inicio, fim, fusoHorario);
        return configRepository.save(config);
    }

    public ConfiguracaoRotina atualizarHorarios(UUID id, LocalTime novoInicio, LocalTime novoFim) {
        ConfiguracaoRotina config = buscarPorId(id);

        if (novoInicio.isAfter(novoFim)) {
            throw new RuntimeException("Horario invalido");
        }

        config.redefinirHorarios(novoInicio, novoFim);
        return configRepository.save(config);
    }

    public ConfiguracaoRotina buscarPorId(UUID id) {
        return configRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuracao nao encontrada"));
    }

    public List<ConfiguracaoRotina> listarTodas() {
        return configRepository.findAll();
    }

    public void deletar(UUID id) {
        ConfiguracaoRotina config = buscarPorId(id);
        configRepository.delete(config);
    }

}
