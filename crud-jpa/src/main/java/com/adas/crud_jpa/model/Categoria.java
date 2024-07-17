package com.adas.crud_jpa.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

// Define automaticamente o construtor vazio
@NoArgsConstructor

// Define automaticamente o construtor com TODOS os atributos
@AllArgsConstructor

// Define automaticamente os métoso GET e SET para todos os atributos
// Ou pode substituir pela anotação @Data
@Getter @Setter

// Gera todas as possibilidades de construtores que não são o 'Vazio' e o 'Cheio'
@Builder

// Gera automaticamente uma tabela no banco de dados, tendo as colunas dessa tabela
// de forma espelhada com os atributos da classe Categoria
@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Nonnull
    private String nome;

    private boolean status;

    // Vinculando um único registro da tabela Categoria com vários registros
    // da tabela Produto
    @JsonIgnore
    @OneToMany(mappedBy = "categoria")
    private List<Produto> produtos;

}
