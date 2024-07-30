package com.adas.crud_jpa.service;

import com.adas.crud_jpa.model.Cliente;
import com.adas.crud_jpa.model.Historico;
import com.adas.crud_jpa.model.Produto;
import com.adas.crud_jpa.repository.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoricoService {
    
    @Autowired
    private HistoricoRepository historicoRepository;

    @Autowired
    private ClienteService clienteService;

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

    public void registrarVenda(Integer idCliente, List<Produto> produtos) {

        // Declarando um objeto da classe Cliente e passando valor para o atributo id
        Cliente cliente = clienteService.findById(idCliente);

        // Forma padrão de declarar objetos no Java
//        Cliente cliente2 = new Cliente();
//        cliente2.setId(idCliente);

        // Declarando um objeto novo da classe Historico com base em um construtor
        // sob demanda, feito através da anotação 'Builder'
        Historico historico = Historico
                .builder()
                .dataTransacao(LocalDateTime.now())
                .cliente(cliente)
                .produtosHistorico(produtos)
                .build();

        this.save(historico);
    }
    
}
