package com.adas.crud_jpa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor @Builder @Data
public class Caixa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private boolean status;
    private Double saldo;
    private Double limite;

    /*
    * Criando a tabela auxiliar 'caixa_produto' para receber o vínculo entre as
    * 'Caixa' e 'Produto'.
    * Vinculando a chave primária de Caixa como chave estrangeira no campo 'caixa_id'
    * Vinculando a chave primária de Produto como chave estrangeira no campo
    * 'produto_id'
    * */
    @ManyToMany
    @JoinTable(
            name = "caixa_produto",
            joinColumns = @JoinColumn(name = "caixa_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> produtosCaixa;

}
