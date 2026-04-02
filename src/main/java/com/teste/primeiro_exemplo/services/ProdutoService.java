package com.teste.primeiro_exemplo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.primeiro_exemplo.model.Produto;
import com.teste.primeiro_exemplo.model.exception.InvalidProductException;
import com.teste.primeiro_exemplo.model.exception.ResourceNotFoundException;
import com.teste.primeiro_exemplo.repository.ProdutoRepository;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    private void validarProduto(Produto produto) {
        if(produto == null || produto.getNome() == null || produto.getNome().isBlank() || produto.getQuantidade() < 0 || produto.getValor() < 0) {
            throw new InvalidProductException("Dados inválidos do produto, verifique os campos.");
        }
    }

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
    public Produto obterPorId(Integer id) {
        return produtoRepository.obterPorId(id)
        .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado. ID inválido"));
    }

    /**
     * Método que adiciona um produto na lista.
     * @param produto que vai ser adicionado na lista.
     * @return Retorna o produto que foi adicionado na lista.
     */
    public Produto adicionar(Produto produto) {
        validarProduto(produto);
        return produtoRepository.adicionar(produto);
    }

    /**
     * Método que deleta um produto da lista.
     * @param id do produto que vai ser deletado.
     */
    public void deletar(Integer id) {

        boolean deletou = produtoRepository.deletar(id);

        if(!deletou) {
            throw new ResourceNotFoundException("Produto não encontrado");
        }
    }

    /**
     * Método que deleta todos os produtos.
     */
    public void deletarTodos() {
        produtoRepository.deletarTodos();
    }

    /**
     * Método que atualiza um produto.
     * @param produto que irá ser atualizado.
     * @return Retorna o novo produto.
     */
    public Produto atualizar(Integer id, Produto produto) {
        Produto produtoEncontrado = obterPorId(id);

        validarProduto(produto);

        produtoEncontrado.setNome(produto.getNome());
        produtoEncontrado.setQuantidade(produto.getQuantidade());
        produtoEncontrado.setValor(produto.getValor());
        produtoEncontrado.setObservacao(produto.getObservacao());

        return produtoEncontrado;
    }
}
