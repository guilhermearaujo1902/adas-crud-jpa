package com.adas.crud_jpa.repository;

import com.adas.crud_jpa.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
