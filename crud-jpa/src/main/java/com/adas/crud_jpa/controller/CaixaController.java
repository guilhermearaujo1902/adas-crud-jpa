package com.adas.crud_jpa.controller;

import com.adas.crud_jpa.model.Caixa;
import com.adas.crud_jpa.service.CaixaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caixa")
public class CaixaController {

    @Autowired
    private CaixaService caixaService;

    @GetMapping
    public ResponseEntity<List<Caixa>> findAll() {
        return ResponseEntity.ok(caixaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caixa> findById(@PathVariable int id) {
        Caixa caixaEncontrada = caixaService.findById(id);
        if (caixaEncontrada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(caixaEncontrada);
    }

    @PostMapping
    public ResponseEntity<Caixa> add(@RequestBody Caixa novaCaixa) {
        return ResponseEntity.ok(caixaService.save(novaCaixa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Caixa> update(@RequestBody Caixa caixaEditada, @PathVariable int id) {
        Caixa caixaEncontrada = caixaService.findById(id);
        if (caixaEncontrada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(caixaService.save(caixaEditada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Caixa> delete(@PathVariable int id) {
        Caixa caixaEncontrada = caixaService.findById(id);
        if (caixaEncontrada == null) {
            return ResponseEntity.notFound().build();
        }
        caixaService.delete(caixaEncontrada);
        return ResponseEntity.ok(caixaEncontrada);
    }

}
