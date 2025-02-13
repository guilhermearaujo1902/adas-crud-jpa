package com.adas.crud_jpa.controller;

import com.adas.crud_jpa.model.Caixa;
import com.adas.crud_jpa.model.Categoria;
import com.adas.crud_jpa.model.Historico;
import com.adas.crud_jpa.model.Produto;
import com.adas.crud_jpa.service.CaixaService;
import com.adas.crud_jpa.service.CategoriaService;
import com.adas.crud_jpa.service.HistoricoService;
import com.adas.crud_jpa.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CaixaService caixaService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private HistoricoService historicoService;
    
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

    @GetMapping("/nome/exato/{nome}")
    public ResponseEntity<List<Produto>> buscarPorNomeExato(@PathVariable String nome) {
        return ResponseEntity.ok(produtoService.buscarPorNomeExato(nome));
    }

    @GetMapping("/nome/similar/{nome}")
    public ResponseEntity<List<Produto>> buscarPorNomeSimilar(@PathVariable String nome) {
        return ResponseEntity.ok(produtoService.buscarPorNomeSimilar(nome));
    }

    @GetMapping("/preco/{valor}")
    public ResponseEntity<List<Produto>> buscarPorPrecoMaiorQue(@PathVariable Double valor) {
        return ResponseEntity.ok(produtoService.buscarPorPrecoMaiorQue(valor));
    }

    @GetMapping("/categoria/{codigo}")
    public ResponseEntity<List<Produto>> buscarPorCodigoCategoria(@PathVariable Integer codigo) {
        return ResponseEntity.ok(produtoService.buscarPorCodigoCategoria(codigo));
    }

    @GetMapping("/categoria/ativa/{codigo}")
    public ResponseEntity<?> buscarPorCategoriaAtiva(@PathVariable Integer codigo) {
        Categoria categoria = categoriaService.buscarPorId(codigo);

        // Validando se a categoria não existe no banco de dados
        if (categoria == null) {
            return ResponseEntity.status(500).body("Categoria não encotrada para o código: " + codigo);
        }

        // Validando se o status da categoria é false
        if (!categoria.isStatus()) {
            return ResponseEntity.status(500).body("Categoria " + categoria.getNome() + " está inativa!");
        }

        // Retornar a lista de produtos pela categoria ativa
        return ResponseEntity.ok(produtoService.buscarPorCodigoCategoria(codigo));
    }

    @GetMapping("/categoria/nome/{nome}")
    public ResponseEntity<List<Produto>> findProdutosByCategoriaNome(@PathVariable String nome) {
        return ResponseEntity.ok(produtoService.findProdutosByCategoriaNome(nome));
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

    @PutMapping("/vender/{quantidade}/{idCaixa}/{idCliente}")
    public ResponseEntity<String> vender(@RequestBody Produto produto,
            @PathVariable int quantidade,
            @PathVariable int idCaixa,
            @PathVariable int idCliente) {

        // Verificar se o caixa está ativo
        Caixa caixa = caixaService.findById(idCaixa);
        if (!caixa.isStatus()) {
            return ResponseEntity.ok("O caixa de código "+ idCaixa + " está fechado! Nâo é possível realizar venda.");
        }

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

        // Criando a lista de produtos para enviar para o histórico
        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto);

        // Atualizar o saldo do caixa
        caixa = caixaService.realizarMovimentacao(idCaixa, totalVenda, "ENTRADA");

        // Registrar movimentção no histórico
        historicoService.registrarVenda(idCliente, produtos);

        // Concatenando os valores para montar um recibo da movimentação, tanto no produto quanto no caixa
        String recibo = " " +
                "Produto vendido: " + produtoAtual.getNome() +
                "\nTotal da venda: R$ " + totalVenda +
                "\nCaixa atualizado: " + idCaixa +
                "\nSaldo atual do caixa: R$ " + caixa.getSaldo();

        return ResponseEntity.ok(recibo);
    }


    @PutMapping("/vender/lista/{idCliente}")
    public ResponseEntity<String> venderLista(@RequestBody List<Produto> produtos,
                                         @PathVariable int idCliente) {

        historicoService.registrarVenda(idCliente, produtos);
        return ResponseEntity.ok("Venda registrada com sucesso!");
    }


    @PutMapping("/comprar/{quantidade}/{idCaixa}")
    public ResponseEntity<String> comprar(@RequestBody Produto produto,
                                         @PathVariable int quantidade,
                                         @PathVariable int idCaixa) {

        Caixa caixa = caixaService.findById(idCaixa);
        if (!caixa.isStatus()) {
            return ResponseEntity.ok("O caixa de código "+ idCaixa + " está fechado! Nâo é possível realizar compra.");
        }

        Produto produtoAtual = produtoService.findById(produto.getId());

        Double totalCompra = quantidade * produtoAtual.getPreco();

        // Verificar se existe saldo disponível no caixa para realizar a compra
        if (caixa.getSaldo() < totalCompra) {
            return ResponseEntity.ok("Saldo insulficiente para realizar a compra!");
        }

        produtoAtual.setQuantidade(produtoAtual.getQuantidade() + quantidade);
        produtoService.save(produtoAtual);

        caixa = caixaService.realizarMovimentacao(idCaixa, totalCompra, "SAIDA");

        String recibo = " " +
                "Produto comprado: " + produtoAtual.getNome() +
                "\nTotal da compra: R$ " + totalCompra +
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
