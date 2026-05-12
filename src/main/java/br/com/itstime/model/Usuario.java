package br.com.itstime.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table (name = "usuarios")

public class Usuario{

    //ATRIBUTOS//
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nomeUsuario;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private int pontosTotais;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id")
    private List<Tarefa> tarefas = new ArrayList<>();

    //CONSTRUTORES//


    // Isso construtor vazio é obrigatorio pro JPA funcionar
    public Usuario(){

    }

    // Construtor com parametros para criar usuarios facilmente
    public Usuario(String nomeUsuario, String email, String senha){
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.senha = senha;
        this.pontosTotais = 0; // Regra para o usuario começar com 0 pontos.
    }

    //METODOS DE NEGOCIO//

    public boolean fazerLogin(String email, String senha) {
        return this.email.equals(email) && this.senha.equals(senha);
    }

    public void atualizarPerfil(String novoNome,String novoEmail){
        this.nomeUsuario = novoNome;
        this.email = novoEmail;
    }

    public int verificarStreak(){
        //Logica real fica no Service
        return 0;
    }

    //GETTERS E SETTERS//


    public UUID getId() {
        return id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getPontosTotais() {
        return pontosTotais;
    }

    public void setPontosTotais(int pontosTotais) {
        this.pontosTotais = pontosTotais;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    @Override
    public String toString(){
        return  "Usuario{nome=" + nomeUsuario + ", email=" + email + ", pontos=" + pontosTotais + "}";
    }


}
