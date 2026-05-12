package br.com.itstime.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "grupos")

public class Grupo {

    //ATRIBUTOS//
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "criador_id", nullable = false)
    private Usuario criador;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "grupo_membros",
            joinColumns = @JoinColumn(name = "grupo_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> membros = new ArrayList<>();

    //CONSTRUTORES//

    public Grupo() {
    }

    public Grupo(String nome, Usuario criador) {
        this.nome = nome;
        this.criador = criador;
        this.membros = new ArrayList<>();
        this.membros.add(criador);
    }

    //METODOS DE NEGOCIO//

    public void adicionarMembros(Usuario usuario){
        if (!membros.contains(usuario)){
            membros.add(usuario);
        }
    }

    public void removerMembro(Usuario usuario) {
        if (!usuario.equals(criador)){
            membros.remove(usuario);
        }
    }

    //GETTERS E SETTERS//

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario getCriador() {
        return criador;
    }

    public void setCriador(Usuario criador) {
        this.criador = criador;
    }

    public List<Usuario> getMembros() {
        return membros;
    }

    public void setMembros(List<Usuario> membros) {
        this.membros = membros;
    }

    @Override
    public String toString() {
        return "Grupo{nome" + nome + ", membros" + membros.size() + "}";
    }

}
