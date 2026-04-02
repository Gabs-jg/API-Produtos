package com.teste.primeiro_exemplo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.primeiro_exemplo.model.Produto;
import com.teste.primeiro_exemplo.services.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> obterTodos() {
        return produtoService.obterTodos();
    }

    @PostMapping
    public ResponseEntity<Produto> adicionar(@RequestBody Produto produto) {
        Produto produtoCriado = produtoService.adicionar(produto);

        return ResponseEntity.status(201).body(produtoCriado);
    }

    @GetMapping("/{id}")
    public Produto obterPorId(@PathVariable Integer id) {
        return produtoService.obterPorId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        produtoService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> deletarTodos() {
        produtoService.deletarTodos();

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Integer id, @RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.atualizar(id, produto);

        return ResponseEntity.status(200).body(produtoAtualizado);
    }
}
