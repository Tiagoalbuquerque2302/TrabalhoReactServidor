/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.edu.ifrs.restinga.dev1.projetoFinal.tudo.modelo.Usuario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Tiago
 */

@Entity
public class Solicitacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    private int id;
    private String enderecousuario, enderecoentregador, tempo, distancia, modoentrega, status="Aguardando Aceitação";
    
    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getModoentrega() {
        return modoentrega;
    }

    public void setModoentrega(String modoentrega) {
        this.modoentrega = modoentrega;
    }
    private double custo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnderecousuario() {
        return enderecousuario;
    }

    public void setEnderecousuario(String enderecousuario) {
        this.enderecousuario = enderecousuario;
    }

    public String getEnderecoentregador() {
        return enderecoentregador;
    }

    public void setEnderecoentregador(String enderecoentregador) {
        this.enderecoentregador = enderecoentregador;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }  

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
