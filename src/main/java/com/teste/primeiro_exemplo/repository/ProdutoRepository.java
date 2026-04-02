package com.teste.primeiro_exemplo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.teste.primeiro_exemplo.model.Produto;


@Repository
public class ProdutoRepository {
    
    private List<Produto> produtos = new ArrayList<>();
    private Integer ultimoId = 0;

    /**
     * Método para retornar uma lista de produtos.
     * @return Lista de produtos.
     */
    public List<Produto> obterTodos() {
        return new ArrayList<>(produtos);
    }

    /**
     * Método que retorna um produto pelo id.
     * @param Id do produto.
     * @return Retorna um Optional contendo produto caso seja encontrado.
     */
    public Optional<Produto> obterPorId(Integer id) {
        return produtos.stream()
        .filter(produto -> produto.getId().equals(id))
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
     * @return Retorna true caso o produto for deletado ou retorna falso se o produto não for deletado
     */
    public boolean deletar(Integer id) {
        return produtos.removeIf(produto -> produto.getId().equals(id));
    }

    /**
     * Método que deleta todos os produtos.
     */
    public void deletarTodos() {
        produtos.clear();
    }

    // TRANSFORMAR EM INTERFACE
    // MUDAR NOME DOS MÉTODOS
    // CRIAR MÉTODO ATUALIZAR PARCIAL
    // CRIAR MÉTODO BUSCAR POR NOME
    // CRIAR MÉTODO BUSCAR PRODUTOS COM VALOR MAIOR QUE
    // CRIAR MÉTODO BUSCAR PRODUTOS COM VALOR MENOR QUE
}
