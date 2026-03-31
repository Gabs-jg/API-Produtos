package com.teste.primeiro_exemplo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.primeiro_exemplo.model.Produto;
import com.teste.primeiro_exemplo.repository.ProdutoRepository;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Método para retornar uma lista de produtos.
     * @return Retorna uma lista de produtos.
     */
    public List<Produto> obterTodos() {
        return produtoRepository.obterTodos();
    }

    /**
     * Método que retorna um produto por Id.
     * @param id do produto a ser buscado.
     * @return Retorna um produto pelo seu Id.
     */
    public Optional<Produto> obterPorId(Integer id) {
        return produtoRepository.obterPorId(id);
    }

    /**
     * Método que adiciona um produto na lista.
     * @param produto que vai ser adicionado na lista.
     * @return Retorna o produto que foi adicionado na lista.
     */
    public Produto adicionar(Produto produto) {
        return produtoRepository.adicionar(produto);
    }

    /**
     * Método que deleta um produto da lista.
     * @param id do produto que vai ser deletado.
     */
    public void deletar(Integer id) {
        produtoRepository.deletar(id);
    }

    /**
     * Método que atualiza um produto.
     * @param produto que irá ser atualizado.
     * @return Retorna o novo produto.
     */
    public Produto atualizar(Integer id, Produto produto) {
        produto.setId(id);
        return produtoRepository.atualizar(produto);
    }
}
