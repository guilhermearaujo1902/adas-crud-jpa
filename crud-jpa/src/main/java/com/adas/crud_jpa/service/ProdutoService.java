package com.adas.crud_jpa.service;

import com.adas.crud_jpa.model.Produto;
import com.adas.crud_jpa.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Produto findById(Integer id) {
        return produtoRepository.findById(id).orElse(null);
    }

    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void delete(Produto produto) {
        produtoRepository.delete(produto);
    }

    public List<Produto> buscarPorNomeExato(String nomeExato) {
        return produtoRepository.buscarPorNomeExato(nomeExato);
    }

    public List<Produto> buscarPorNomeSimilar(String nomeSimilar) {
        return produtoRepository.buscarPorNomeSimilar(nomeSimilar);
    }

    public List<Produto> buscarPorPrecoMaiorQue(Double preco) {
        return produtoRepository.buscarPorPrecoMaiorQue(preco);
    }

}
