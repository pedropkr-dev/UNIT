package br.com.itstime.repository;

import br.com.itstime.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, UUID> {

    List<Tarefa> findByConcluida(boolean concluida);
    List<Tarefa> findByPrioridade(int prioridade);
}
