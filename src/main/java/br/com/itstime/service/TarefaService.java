package br.com.itstime.service;

import br.com.itstime.model.Tarefa;
import br.com.itstime.model.Usuario;
import br.com.itstime.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioService usuarioService;

    // Pontos ganhos por tarefa baseado na prioridade
    private static final int PONTOS_BAIXA = 5;
    private static final int PONTOS_MEDIA = 10;
    private static final int PONTOS_ALTA = 20;

    //CRIAR TAREFA//
    public Tarefa criar(UUID idUsuario, String titulo, LocalDateTime prazo, int prioridade) {
        //Primeiramente valida o usuario
        Usuario usuario = usuarioService.buscarPorId(idUsuario);

        //Validacoes

        if (titulo == null || titulo.isBlank()){
            throw new RuntimeException("Titulo não pode ser vazio");
        }
        if (prioridade < 1 || prioridade > 3) {
            throw new RuntimeException("Prioridade deve ser 1, 2 ou 3");
        }
        if (prazo.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Prazo não pode ser no passado");
        }

        Tarefa novaTarefa = new Tarefa(titulo, prazo, prioridade);
        usuario.getTarefas().add(novaTarefa);

        return tarefaRepository.save(novaTarefa);
    }

    //CONCLUIR TAREFA//
    public Tarefa concluir(UUID idTarefa, UUID idUsuario) {
        Tarefa tarefa = buscarPorId(idTarefa);

        if (tarefa.isConcluida()) {
            throw new RuntimeException("Tarefa já está concluída");
        }
        tarefa.marcarComoConcluida();

        int pontosGanhos = calcularPontos(tarefa.getPrioridade());
        usuarioService.adicionarPontos(idUsuario, pontosGanhos);

        return tarefaRepository.save(tarefa);
    }

    //METODOS PRIVADO AUXILIAR//

    private int calcularPontos(int prioridade) {
        return switch (prioridade) {
            case 1 -> PONTOS_BAIXA;
            case 2 -> PONTOS_MEDIA;
            case 3 -> PONTOS_ALTA;
            default -> 0;
        };
    }
    //EDITAR//
    public Tarefa editar(UUID idTarefa, String novoTitulo, LocalDateTime novoPrazo, int novaPrioridade) {
        Tarefa tarefa = buscarPorId(idTarefa);
        tarefa.editarTarefa(novoTitulo, novoPrazo, novaPrioridade);
        return tarefaRepository.save(tarefa);
    }

    //EXCLUIR//
    public void excluir(UUID idTarefa){
        Tarefa tarefa = buscarPorId(idTarefa);
        tarefaRepository.delete(tarefa);
    }

    //BUSCAR//
    public Tarefa buscarPorId(UUID idTarefa) {
        return tarefaRepository.findById(idTarefa)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }
    public List<Tarefa> listaTodas() {
        return tarefaRepository.findAll();
    }
    public List<Tarefa> listarPorStatus(boolean concluida) {
        return tarefaRepository.findByConcluida(concluida);
    }
}
