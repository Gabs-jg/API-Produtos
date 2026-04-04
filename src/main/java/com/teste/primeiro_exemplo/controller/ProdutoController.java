package com.teste.primeiro_exemplo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teste.primeiro_exemplo.model.Produto;
import com.teste.primeiro_exemplo.model.exception.InvalidProductException;
import com.teste.primeiro_exemplo.model.exception.ResourceNotFoundException;
import com.teste.primeiro_exemplo.services.ProdutoService;

/**
 * Controller responsável por expor os endpoints da API relacionados a produtos.
 * 
 * Essa classe recebe requisições HTTP e delega as operações para o service,
 * retornando as respostas apropriadas ao cliente.
 * 
 * @author Gabriel Oliveira
 * @version 1.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    /**
     * Retorna uma lista com todos os produtos cadastrados.
     * 
     * @return Lista de produtos.
     */
    @GetMapping
    public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    /**
     * Busca um produto pelo ID.
     * 
     * @param id ID do produto a ser buscado.
     * @return Produto encontrado.
     * @throws ResourceNotFoundException Caso o id informado não existir.
     */
    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Integer id) {
        return produtoService.buscarPorId(id);
    }

    /**
     * Busca um produto pelo nome.
     * 
     * @param nome Nome do produto.
     * @return Produto encontrado.
     * @throws InvalidProductException Caso o nome seja nulo ou vazio.
     * @throws ResourceNotFoundException Caso o nome do produto não exista.
     */
    @GetMapping("/name/{nome}")
    public Produto buscarPorNome(@PathVariable String nome) {
        return produtoService.buscarPorNome(nome);
    }

    /**
     * Caso nenhum parâmetro seja informado, retorna todos os produtos.
     * Apenas um critério é aplicado por vez, seguindo a ordem de prioridade:
     * valor máximo, valor mínimo, quantidade máxima e quantidade mínima.
     * 
     * @param maxValor Valor mínimo para retornar produtos com valor maior que o informado.
     * @param minValor Valor máximo para retornar produtos com valor menor que o informado.
     * @param maxQtd Quantidade mínima para retornar produtos com quantidade maior que o informado.
     * @param minQtd Quantidade máxima para retornar produtos com quantidade menor que o informado.
     * @return Lista de produtos filtrados.
     * @throws InvalidProductException Caso o valor ou quantidade seja nulo ou negativo.
     */
    @GetMapping("/filter")
    public List<Produto> filtrar(
        @RequestParam(required=false) Double maxValor,
        @RequestParam(required=false) Double minValor,
        @RequestParam(required=false) Integer maxQtd,
        @RequestParam(required=false) Integer minQtd
    ) {

        if(maxValor != null) {
            return produtoService.listarPorValorMaiorQue(maxValor);
        }

        if(minValor != null) {
            return produtoService.listarPorValorMenorQue(minValor);
        }

        if(maxQtd != null) {
            return produtoService.listarPorQuantidadeMaiorQue(maxQtd);
        }

        if(minQtd != null) {
            return produtoService.listarPorQuantidadeMenorQue(minQtd);
        }

        return produtoService.listarTodos();
    }
    
    /**
     * Adiciona um novo produto.
     * 
     * @param produto Produto que vai ser criado.
     * @return Produto criado encapsulado em ResponseEntity com status 201.
     * @throws InvalidProductException Caso algum campo informado seja inválido.
     */
    @PostMapping
    public ResponseEntity<Produto> adicionar(@RequestBody Produto produto) {
        Produto produtoCriado = produtoService.adicionar(produto);

        return ResponseEntity.status(201).body(produtoCriado);
    }

    /**
     * Deleta um produto pelo ID.
     * 
     * @param id ID do produto que vai ser deletado.
     * @return ResponseEntity sem conteúdo com status 204.
     * @throws ResourceNotFoundException Caso o id não existir.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        produtoService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Deleta todos os produtos.
     * 
     * @return ResponseEntity sem conteúdo com status 204.
     */
    @DeleteMapping
    public ResponseEntity<Void> deletarTodos() {
        produtoService.deletarTodos();

        return ResponseEntity.noContent().build();
    }

    /**
     * Atualiza todos os campos de um produto.
     * 
     * @param id Id do produto que irá ser atualizado.
     * @param produto Produto com os novos dados que substituirão o produto existente.
     * @return Produto atualizado encapsulado em ResponseEntity com status 200.
     * @throws ResourceNotFoundException Caso o id não existir.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Integer id, @RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.atualizar(id, produto);

        return ResponseEntity.ok(produtoAtualizado);
    }

    /**
     * Atualiza alguns campos de um produto.
     * 
     * @param id Id do produto que irá ser atualizado.
     * @param produto Produto com os novos dados que substituirão o produto existente.
     * @return Produto atualizado encapsulado em ResponseEntity com status 200.
     * @throws ResourceNotFoundException Caso o id não existir.
     * @throws InvalidProductException Caso algum campo informado seja inválido.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Produto> atualizarParcial(@PathVariable Integer id, @RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.atualizarParcial(id, produto);

        return ResponseEntity.ok(produtoAtualizado);
    }
}