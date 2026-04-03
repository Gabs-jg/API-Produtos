package com.teste.primeiro_exemplo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.teste.primeiro_exemplo.model.Produto;
import com.teste.primeiro_exemplo.model.exception.InvalidProductException;
import com.teste.primeiro_exemplo.model.exception.ResourceNotFoundException;
import com.teste.primeiro_exemplo.repository.ProdutoRepository;

@Service
public class ProdutoService {
    
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    /**
     * Método que faz validações referente ao produto.
     * @param produto que irá ser validado.
     */
    private void validarProduto(Produto produto) {
        if(produto == null) {
            throw new InvalidProductException("Produto não pode ser nulo.");
        }
        
        if(produto.getNome() == null || produto.getNome().isBlank()) {
            throw new InvalidProductException("Nome não pode ser vazio ou nulo.");
        }
        
        if(produto.getQuantidade() == null || produto.getQuantidade() <  0) {
            throw new InvalidProductException("Quantidade não pode ser negativa ou nula.");
        }
        
        if(produto.getValor() == null || produto.getValor() < 0) {
            throw new InvalidProductException("Valor não pode ser nulo ou negativo.");
        }
    }

    /**
     * Método que valida o valor de um produto.
     * @param valor do produto.
     */
    private void validarValor(Double valor) {
        if(valor == null || valor < 0) {
            throw new InvalidProductException("Valor não pode ser nulo ou negativo.");
        }
    }

    /**
     * Método que valida a quantidade de um produto.
     * @param quantidade do produto.
     */
    private void validarQuantidade(Integer quantidade) {
        if(quantidade == null || quantidade < 0) {
            throw new InvalidProductException("Quantidade não pode ser nula ou negativa.");
        }
    }

    /**
     * Método para retornar uma lista de produtos.
     * @return Retorna uma lista de produtos.
     */
    public List<Produto> obterTodos() {
        return produtoRepository.findAll();
    }

    /**
     * Método que retorna um produto por Id.
     * @param id do produto a ser buscado.
     * @return Retorna um produto pelo seu Id.
     */
    public Produto buscarPorId(Integer id) {
        return produtoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado. ID inválido"));
    }

    /**
     * Método que retorna um produto pelo nome.
     * @param nome do produto.
     * @return Retorna um Produto pelo seu nome.
     */
    public Produto buscarPorNome(String nome) {
        if(nome == null || nome.isBlank()) {
            throw new InvalidProductException("Nome não pode ser vazio ou nulo.");
        }

        return produtoRepository.findByName(nome)
        .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));
    }

    /**
     * Método que adiciona um produto na lista.
     * @param produto que vai ser adicionado na lista.
     * @return Retorna o produto que foi adicionado na lista.
     */
    public Produto adicionar(Produto produto) {
        validarProduto(produto);
        return produtoRepository.save(produto);
    }

    /**
     * Método que deleta um produto do banco de dados.
     * @param id do produto que vai ser deletado.
     */
    public void deletar(Integer id) {
        Produto p = buscarPorId(id);
        produtoRepository.delete(p);
    }

    /**
     * Método que deleta todos os produtos do banco de dados.
     */
    public void deletarTodos() {
        produtoRepository.deleteAll();
    }

    /**
     * Método que retorna uma lista de produto com valor maior que o parâmetro.
     * @param valor usado como parâmetro.
     * @return Retorna uma lista de produtos que possuem valor maior que o parâmetro.
     */
    public List<Produto> listarPorValorMaiorQue(Double valor) {
        validarValor(valor);
        return produtoRepository.findByValorGreaterThan(valor);
    }

    /**
     * Método que retorna uma lista de produto com valor menor que o parâmetro.
     * @param valor usado como parâmetro.
     * @return Retorna uma lista de produtos que possuem valor menor que o parâmetro.
     */
    public List<Produto> listarPorValorMenorQue(Double valor) {
        validarValor(valor);
        return produtoRepository.findByValorLessThan(valor);
    }

    /**
     * Método que retorna uma lista de produto com quantidade maior que o parâmetro.
     * @param valor usado como parâmetro.
     * @return Retorna uma lista de produtos que possuem quantidade maior que o parâmetro.
     */
    public List<Produto> listarPorQuantidadeMaiorQue(Integer quantidade) {
        validarQuantidade(quantidade);
        return produtoRepository.findByQuantidadeGreaterThan(quantidade);
    }

    /**
     * Método que retorna uma lista de produto com quantidade menor que o parâmetro.
     * @param valor usado como parâmetro.
     * @return Retorna uma lista de produtos que possuem quantidade menor que o parâmetro.
     */
    public List<Produto> listarPorQuantidadeMenorQue(Integer quantidade) {
        validarQuantidade(quantidade);
        return produtoRepository.findByQuantidadeLessThan(quantidade);
    }

    /**
     * Método que atualiza todos os campos de um produto.
     * @param id do prduto que irá ser atualizado.
     * @param produto que vai substituir o produto antigo.
     * @return Retorna o novo produto.
     */
    public Produto atualizar(Integer id, Produto produto) {
        Produto produtoEncontrado = buscarPorId(id);

        validarProduto(produto);

        produtoEncontrado.setNome(produto.getNome());
        produtoEncontrado.setQuantidade(produto.getQuantidade());
        produtoEncontrado.setValor(produto.getValor());
        produtoEncontrado.setObservacao(produto.getObservacao());

        return produtoRepository.save(produtoEncontrado);
    }

    /**
     * Método que atualiza alguns campos de um produto.
     * @param id do produto que irá ser atualizado.
     * @param produto que vai substituir o produto antigo.
     * @return Retorna o novo produto.
     */
    public Produto atualizarParcial(Integer id, Produto produto) {
        Produto produtoEncontrado = buscarPorId(id);

        if(produto.getNome() != null) {
            if(produto.getNome().isBlank()) {
                throw new InvalidProductException("Nome não pode ser vazio.");
            }
            produtoEncontrado.setNome(produto.getNome());
        }

        if(produto.getValor() != null) {
            validarValor(produto.getValor());
            produtoEncontrado.setValor(produto.getValor());
        }

        if(produto.getQuantidade() != null) {
            validarQuantidade(produto.getQuantidade());
            produtoEncontrado.setQuantidade(produto.getQuantidade());
        }

        if(produto.getObservacao() != null) {
            produtoEncontrado.setObservacao(produto.getObservacao());
        }

        return produtoRepository.save(produtoEncontrado);
    }
}