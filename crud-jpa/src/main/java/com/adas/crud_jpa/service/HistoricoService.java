package com.adas.crud_jpa.service;

import com.adas.crud_jpa.model.Historico;
import com.adas.crud_jpa.repository.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoService {
    
    @Autowired
    private HistoricoRepository historicoRepository;

    public List<Historico> findAll() {
        return historicoRepository.findAll();
    }

    public Historico findById(Integer id) {
        return historicoRepository.findById(id).orElse(null);
    }

    public Historico save(Historico historico) {
        return historicoRepository.save(historico);
    }

    public void delete(Historico historico) {
        historicoRepository.delete(historico);
    }
    
}
