package br.com.itstime.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sistemas_recompensas")

public class SistemaRecompensas {
    //ATRIBUTOS//
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private int multiplicadorPontos;

    @Column(nullable = false, length = 100)
    private String badgeAtual;

    @ElementCollection
    @CollectionTable(name = "historico_premios", joinColumns = @JoinColumn(name = "sistema_id"))
    @Column(name = "premio")
    private List<String> historicoPremios = new ArrayList<>();

    //CONSTRUTORES//

    public SistemaRecompensas(){
        this.multiplicadorPontos = 1;
        this.badgeAtual = "Iniciante";
        this.historicoPremios = new ArrayList<>();
    }

    //METODOS DE NEGOCIO//

    public void atribuirPontos(int quantidade){
        //A logica de somar pontos no usuario fica no service
        int pontosFinais = quantidade * multiplicadorPontos;
        System.out.println("Pontos atribuidos: " + pontosFinais);
    }

    public boolean resgatarPremio(String nomePremio) {
        historicoPremios.add(nomePremio);
        System.out.println("Premio resgatado: " + nomePremio);
        return true;
    }

    public void limparHistorico(){
        historicoPremios.clear();
    }

    //GETTERS E SETTERS//


    public UUID getId() {
        return id;
    }

    public int getMultiplicadorPontos() {
        return multiplicadorPontos;
    }

    public void setMultiplicadorPontos(int multiplicadorPontos) {
        this.multiplicadorPontos = multiplicadorPontos;
    }

    public String getBadgeAtual() {
        return badgeAtual;
    }

    public void setBadgeAtual(String badgeAtual) {
        this.badgeAtual = badgeAtual;
    }

    public List<String> getHistoricoPremios() {
        return historicoPremios;
    }

    public void setHistoricoPremios(List<String> historicoPremios) {
        this.historicoPremios = historicoPremios;
    }

    @Override
    public String toString(){
        return "SistemaRecompensas{badge=" + badgeAtual + ", multiplicador=" + multiplicadorPontos + "x}";
    }
}
