package com.adas.crud_jpa.controller;

import com.adas.crud_jpa.model.Cliente;
import com.adas.crud_jpa.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> findAll() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable int id) {
        Cliente clienteEncontrada = clienteService.findById(id);
        if (clienteEncontrada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteEncontrada);
    }

    @PostMapping
    public ResponseEntity<Cliente> add(@RequestBody Cliente novaCliente) {
        return ResponseEntity.ok(clienteService.save(novaCliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@RequestBody Cliente clienteEditada, @PathVariable int id) {
        Cliente clienteEncontrada = clienteService.findById(id);
        if (clienteEncontrada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteService.save(clienteEditada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> delete(@PathVariable int id) {
        Cliente clienteEncontrada = clienteService.findById(id);
        if (clienteEncontrada == null) {
            return ResponseEntity.notFound().build();
        }
        clienteService.delete(clienteEncontrada);
        return ResponseEntity.ok(clienteEncontrada);
    }
    
}
