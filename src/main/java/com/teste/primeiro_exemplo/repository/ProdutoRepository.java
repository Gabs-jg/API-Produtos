package com.teste.primeiro_exemplo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.teste.primeiro_exemplo.model.Produto;
import com.teste.primeiro_exemplo.model.exception.ResourceNotFoundException;

@Repository
public class ProdutoRepository {
    
    private List<Produto> produtos = new ArrayList<>();
    private Integer ultimoId = 0;

    /**
     * Método para retornar uma lista de produtos.
     * @return Lista de produtos.
     */
    public List<Produto> obterTodos() {
        return produtos;
    }

    /**
     * Método que retorna um produto pelo id.
     * @param Id do produto.
     * @return Retorna um produto caso seja encontrado ou null caso não seja encontrado.
     */
    public Optional<Produto> obterPorId(Integer Id) {
        return produtos.stream()
        .filter(produto -> produto.getId() == Id)
        .findFirst();
    }

    /**
     * Método que adiciona um produto na lista.
     * @param produto que vai ser adicionado.
     * @return Retorna o produto que foi adicionado na lista.
     */
    public Produto adicionar(Produto produto) {
        ultimoId++;
        produto.setId(ultimoId);
        produtos.add(produto);

        return produto;
    }

    /**
     * Método que deleta um produto a partir do Id.
     * @param id do produto que irá ser deletado.
     */
    public void deletar(Integer id) {
        produtos.removeIf(produto -> produto.getId() == id);
    }

    /**
     * Método que atualiza o produto na lista.
     * @param produto que será atualizado.
     * @return Retorna o produto após atualizar a lista.
     */
    public Produto atualizar(Produto produto) {
        Optional<Produto> produtoEncontrado = obterPorId(produto.getId());

        if(produtoEncontrado.isEmpty()) {
            throw new ResourceNotFoundException("Produto não encontrado.");
        }

        deletar(produto.getId());
        
        produtos.add(produto);

        return produto;
    }
}
