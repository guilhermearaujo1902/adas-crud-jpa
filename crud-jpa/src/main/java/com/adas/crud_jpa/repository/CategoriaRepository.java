package com.adas.crud_jpa.repository;

import com.adas.crud_jpa.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findByIdGreaterThanEqual(int id);

}