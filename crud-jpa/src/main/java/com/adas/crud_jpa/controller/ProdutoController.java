package com.adas.crud_jpa.controller;

import com.adas.crud_jpa.model.Caixa;
import com.adas.crud_jpa.model.Produto;
import com.adas.crud_jpa.service.CaixaService;
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

    @Autowired
    private CaixaService caixaService;
    
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

    @PutMapping("/vender/{quantidade}/{idCaixa}")
    public ResponseEntity<String> vender(@RequestBody Produto produto,
            @PathVariable int quantidade,
            @PathVariable int idCaixa) {
        // Busca o objeto atualizado no banco de dados com base no id informado no corpo da requisição
        Produto produtoAtual = produtoService.findById(produto.getId());

        // Validar a quantidade de estoque para realizar ou não a venda do produto
        if (produtoAtual.getQuantidade() < quantidade) {
            return ResponseEntity.ok("Estoque insulficiente para venda do produto.");
        }

        // Atualizando a quantidade do produto, subtraindo com base na quantidade recebida na requisição
        produtoAtual.setQuantidade(produtoAtual.getQuantidade() - quantidade);
        produtoService.save(produtoAtual);

        // Descobrir o valor total da venda
        Double totalVenda = quantidade * produtoAtual.getPreco();

        // Atualizar o saldo do caixa
        Caixa caixa = caixaService.realizarMovimentacao(idCaixa, totalVenda, "ENTRADA");

        // Concatenando os valores para montar um recibo da movimentação, tanto no produto quanto no caixa
        String recibo = " " +
                "Produto vendido: " + produtoAtual.getNome() +
                "\nTotal da venda: R$ " + totalVenda +
                "\nCaixa atualizado: " + idCaixa +
                "\nSaldo atual do caixa: R$ " + caixa.getSaldo();

        return ResponseEntity.ok(recibo);
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
