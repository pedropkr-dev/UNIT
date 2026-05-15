package br.com.itstime.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "assinaturas")

public class Assinatura {

    //ATRIBUTOS//
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private float valorMensal;

    @Column(nullable = false)
    private boolean suportePrioritario;

    @Column(nullable = false)
    private LocalDateTime dataExpiracao;

    @Column(nullable = false, length = 50)
    private String metodoPagamento;

    //CONSTRUTORES//

    public Assinatura(){

    }

    public Assinatura(float valorMensal, boolean suportePrioritario, LocalDateTime dataExpiracao, String metodoPagamento){
        this.valorMensal = valorMensal;
        this.suportePrioritario = suportePrioritario;
        this.dataExpiracao = dataExpiracao;
        this.metodoPagamento = metodoPagamento;
    }

    //METODOS DE NEGOCIO//

    public boolean processarAssinatura() {
        //Simulacao simples, sempre aprova o pagamento. Na vida real, teria que integrar com gateway de pagamento.
        System.out.println("Processando assinatura via " + metodoPagamento + "...");
        return true;
    }

    public boolean verificarStatus(){
        //Plano ativo se a data de expiração ainda não passou
        return dataExpiracao.isAfter(LocalDateTime.now());
    }

    //GETTERS E SETTERS//
    public UUID getId() {
        return id;
    }

    public float getValorMensal() {
        return valorMensal;
    }

    public void setValorMensal(float valorMensal) {
        this.valorMensal = valorMensal;
    }

    public boolean isSuportePrioritario() {
        return suportePrioritario;
    }

    public void setSuportePrioritario(boolean suportePrioritario) {
        this.suportePrioritario = suportePrioritario;
    }

    public LocalDateTime getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    @Override
    public String toString(){
        return "PlanoPremium{valor=R$" + valorMensal + ", expira=" + dataExpiracao + "}";
    }
}
