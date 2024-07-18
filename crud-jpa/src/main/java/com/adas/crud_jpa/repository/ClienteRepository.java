package com.adas.crud_jpa.repository;

import com.adas.crud_jpa.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
