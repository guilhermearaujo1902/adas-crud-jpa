package com.adas.crud_jpa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor @Builder @Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nome;
    private Double preco;
    private Integer quantidade;

    // Vinculando vários registros da tabela Produto com um único registro
    // da tabela Categoria.
    // A tabela Produto recebe a chave primária de Categoria como chave estrangeira
    // dentro do campo 'categoria_id'
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToMany(mappedBy = "produtosCaixa")
    private List<Caixa> caixas;

}
