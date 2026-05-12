package br.com.itstime.model;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "configuracoes_rotina")

public class ConfiguracaoRotina {

    //ATRIBUTOS//
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalTime horarioInicioDia;

    @Column(nullable = false)
    private LocalTime horarioFimDia;

    @Column(nullable = false, length = 50)
    private String fusoHorario;

    //CONSTRUTORES//

    public ConfiguracaoRotina(){

    }

    public ConfiguracaoRotina(LocalTime horarioInicioDia, LocalTime horarioFimDia, String fusoHorario){
        this.horarioInicioDia = horarioInicioDia;
        this.horarioFimDia = horarioFimDia;
        this.fusoHorario = fusoHorario;
    }

    //METODOS DE NEGOCIO//

    public void gerarGradeAutomatica(){
        System.out.println("Gerando grade automatica das " + horarioInicioDia + " até " + horarioFimDia);
    }

    public void redefinirHorarios(LocalTime novoInicio, LocalTime novoFim) {
        this.horarioInicioDia = novoInicio;
        this.horarioFimDia = novoFim;
    }

    public void pausarNotificacoes(int minutos) {
        System.out.println("Notificações pausadas por " + minutos + "minutos.");
    }

    //GETTERS E SETTERS//

    public UUID getId() {
        return id;
    }

    public LocalTime getHorarioInicioDia() {
        return horarioInicioDia;
    }

    public void setHorarioInicioDia(LocalTime horarioInicioDia) {
        this.horarioInicioDia = horarioInicioDia;
    }

    public LocalTime getHorarioFimDia() {
        return horarioFimDia;
    }

    public void setHorarioFimDia(LocalTime horarioFimDia) {
        this.horarioFimDia = horarioFimDia;
    }

    public String getFusoHorario() {
        return fusoHorario;
    }

    public void setFusoHorario(String fusoHorario) {
        this.fusoHorario = fusoHorario;
    }

    @Override
    public String toString() {
        return "ConfiguraçãoRotina{" + horarioInicioDia + " - " + horarioFimDia + ", fuso=" + fusoHorario + "}";
    }

}
