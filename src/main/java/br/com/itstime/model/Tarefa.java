package br.com.itstime.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tarefas")

public class Tarefa {

    //ATRIBUTOS//
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_tarefa")
    private UUID idTarefa;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false)
    private LocalDateTime prazo;

    @Column(nullable = false)
    private int prioridade; //1=baixa 2=media 3=alta

    @Column(nullable = false)
    private boolean concluida;

    //CONSTRUTORES//

    public Tarefa() {
    }

    public Tarefa(String titulo, LocalDateTime prazo, int prioridade) {
        this.titulo = titulo;
        this.prazo = prazo;
        this.prioridade = prioridade;
        this.concluida = false;
    }

    //METODOS DE NEGOCIO//

    public void marcarComoConcluida() {
        this.concluida = true;
    }

    public void editarTarefa(String novoTitulo, LocalDateTime novoPrazo, int novaPrioridade) {
        this.titulo = novoTitulo;
        this.prazo = novoPrazo;
        this.prioridade = novaPrioridade;
    }

    public void excluirTarefa() {
        //metodo existe no UML, mas exclusão real acontece no TarefaService, porque uma tarefa não se autoexclui da lista do usuario
        System.out.println("Tarefa marcada para exclusão: " + this.titulo);
    }

    //GETTERS E SETTERS


    public UUID getIdTarefa() {
        return idTarefa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDateTime getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDateTime prazo) {
        this.prazo = prazo;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    @Override
    public String toString(){
        String status = concluida ? "[X]" : "[ ]";
        return status + " " + titulo + " (prazo: " + prazo + ", prioridade: " + prioridade + ")";
    }

}
