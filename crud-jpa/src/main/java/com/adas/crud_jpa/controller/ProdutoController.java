package com.adas.crud_jpa.controller;

import com.adas.crud_jpa.model.Produto;
import com.adas.crud_jpa.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;
    
    @GetMapping
    public ResponseEntity<List<Produto>> findAll() {
        return ResponseEntity.ok(produtoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable int id) {
        Produto produtoEncontrada = produtoService.findById(id);
        if (produtoEncontrada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produtoEncontrada);
    }

    @PostMapping
    public ResponseEntity<Produto> add(@RequestBody Produto novaProduto) {
        return ResponseEntity.ok(produtoService.save(novaProduto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@RequestBody Produto produtoEditada, @PathVariable int id) {
        Produto produtoEncontrada = produtoService.findById(id);
        if (produtoEncontrada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produtoService.save(produtoEditada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Produto> delete(@PathVariable int id) {
        Produto produtoEncontrada = produtoService.findById(id);
        if (produtoEncontrada == null) {
            return ResponseEntity.notFound().build();
        }
        produtoService.delete(produtoEncontrada);
        return ResponseEntity.ok(produtoEncontrada);
    }
 
}
