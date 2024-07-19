package com.adas.crud_jpa.controller;

import com.adas.crud_jpa.model.Historico;
import com.adas.crud_jpa.service.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/historico")
public class HistoricoController {
    
    @Autowired
    private HistoricoService historicoService;

    @GetMapping
    public ResponseEntity<List<Historico>> findAll() {
        return ResponseEntity.ok(historicoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Historico> findById(@PathVariable int id) {
        Historico historicoEncontrada = historicoService.findById(id);
        if (historicoEncontrada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(historicoEncontrada);
    }

    @PostMapping
    public ResponseEntity<Historico> add(@RequestBody Historico novoHistorico) {
        novoHistorico.setDataTransacao(LocalDateTime.now());
        return ResponseEntity.ok(historicoService.save(novoHistorico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Historico> update(@RequestBody Historico historicoEditada, @PathVariable int id) {
        Historico historicoEncontrada = historicoService.findById(id);
        if (historicoEncontrada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(historicoService.save(historicoEditada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Historico> delete(@PathVariable int id) {
        Historico historicoEncontrada = historicoService.findById(id);
        if (historicoEncontrada == null) {
            return ResponseEntity.notFound().build();
        }
        historicoService.delete(historicoEncontrada);
        return ResponseEntity.ok(historicoEncontrada);
    }
    
}
