package com.adas.crud_jpa.repository;

import com.adas.crud_jpa.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    // Exemplos de SELECT baseado em JPQL
    @Query("SELECT p FROM Produto p WHERE p.nome = :NOME")
    List<Produto> buscarPorNomeExato(@Param("NOME") String nome);

    @Query("SELECT p FROM Produto p WHERE p.nome ILIKE %:NOME%")
    List<Produto> buscarPorNomeSimilar(@Param("NOME") String nome);


    // Exemplo de SELECT nativo, utilizando a propriedade 'nativeQuery = true'
    @Query(value = "SELECT * FROM produto p WHERE p.preco >= :PRECO", nativeQuery = true)
    List<Produto> buscarPorPrecoMaiorQue(@Param("PRECO") Double preco);


    @Query("SELECT p FROM Produto p WHERE p.categoria.id = :CATEGORIA_ID")
    List<Produto> buscarPorCodigoCategoria(@Param("CATEGORIA_ID") Integer idCategoria);


    // Exemplo de SELECT com JOIN, utilizando SQL Nativa
    @Query(value = "SELECT p.id, p.nome, p.preco, p.quantidade, p.categoria_id " +
            "FROM produto p " +
            "INNER JOIN categoria c ON p.categoria_id = c.id " +
            "WHERE c.nome ILIKE :CATEGORIA_NOME", nativeQuery = true)
    List<Produto> findProdutosByCategoriaNome(@Param("CATEGORIA_NOME") String categoriaNome);

}
