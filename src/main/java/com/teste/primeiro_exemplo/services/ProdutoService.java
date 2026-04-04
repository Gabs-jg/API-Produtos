package com.teste.primeiro_exemplo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.teste.primeiro_exemplo.model.Produto;
import com.teste.primeiro_exemplo.model.exception.InvalidProductException;
import com.teste.primeiro_exemplo.model.exception.ResourceNotFoundException;
import com.teste.primeiro_exemplo.repository.ProdutoRepository;

/**
 * Serviço responsável pelas regras de negócio relacionadas a produtos.
 * Realiza validações, operações com CRUD e consultas específicas.
 * 
 * @author Gabriel Oliveira
 * @version 1.0
 * @since 2026
 */
@Service
public class ProdutoService {
    
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    /**
     * Valida o produto pelo nome, quantidade e valor.
     * 
     * @param produto Produto que irá ser validado.
     * @throws InvalidProductException Caso algum campo informado seja inválido.
     */
    private void validarProduto(Produto produto) {
        if(produto == null) {
            throw new InvalidProductException("Produto não pode ser nulo.");
        }
        
        validarNome(produto.getNome());
        
        validarValor(produto.getValor());

        validarQuantidade(produto.getQuantidade());
    }

    /**
     * Valida o valor de um produto.
     * 
     * @param valor Valor do produto.
     * @throws InvalidProductException Caso o valor seja nulo ou negativo.
     */
    private void validarValor(Double valor) {
        if(valor == null || valor < 0) {
            throw new InvalidProductException("Valor não pode ser nulo ou negativo.");
        }
    }

    /**
     * Valida a quantidade de um produto.
     * 
     * @param quantidade Quantidade do produto.
     * @throws InvalidProductException Caso a quantidade seja nula ou negativa.
     */
    private void validarQuantidade(Integer quantidade) {
        if(quantidade == null || quantidade < 0) {
            throw new InvalidProductException("Quantidade não pode ser nula ou negativa.");
        }
    }

    /**
     * Valida o nome de um produto
     * 
     * @param nome Nome do produto
     * @throws InvalidProductException Caso o nome seja nulo ou vazio.
     */
    private void validarNome(String nome) {
        if(nome == null || nome.isBlank()) {
            throw new InvalidProductException("Nome não pode ser nulo ou vazio.");
        }
    }

    /**
     * Retorna uma lista com todos os produtos cadastrados.
     * 
     * @return Lista de produtos.
     */
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    /**
     * Busca um produto pelo ID.
     * 
     * @param id ID do produto a ser buscado.
     * @return Produto encontrado.
     * @throws ResourceNotFoundException Caso o id informado não existir.
     */
    public Produto buscarPorId(Integer id) {
        return produtoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado para o ID: " + id));
    }

    /**
     * Busca um produto pelo nome.
     * 
     * @param nome Nome do produto.
     * @return Produto encontrado.
     * @throws InvalidProductException Caso o nome seja nulo ou vazio.
     * @throws ResourceNotFoundException Caso o nome do produto não exista.
     */
    public Produto buscarPorNome(String nome) {
        validarNome(nome);

        return produtoRepository.findByNome(nome)
        .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado para o nome: " + nome));
    }

    /**
     * Adiciona um novo produto.
     * 
     * @param produto Produto que vai ser criado.
     * @return Produto criado.
     * @throws InvalidProductException Caso algum campo informado seja inválido.
     */
    public Produto adicionar(Produto produto) {
        validarProduto(produto);
        return produtoRepository.save(produto);
    }

    /**
     * Deleta um produto pelo ID.
     * 
     * @param id ID do produto que vai ser deletado.
     * @throws ResourceNotFoundException Caso o id não existir.
     */
    public void deletar(Integer id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }

    /**
     * Deleta todos os produtos.
     */
    public void deletarTodos() {
        produtoRepository.deleteAll();
    }

    /**
     * Retorna uma lista de produtos com valor maior que o informado.
     * 
     * @param valor Valor usado como parâmetro.
     * @return Lista de produtos com valor maior que o informado.
     * @throws InvalidProductException Caso o valor seja nulo ou negativo.
     */
    public List<Produto> listarPorValorMaiorQue(Double valor) {
        validarValor(valor);
        return produtoRepository.findByValorGreaterThan(valor);
    }

    /**
     * Retorna uma lista de produtos com valor menor que o informado.
     * 
     * @param valor Valor usado como parâmetro.
     * @return Lista de produtos com valor menor que o informado.
     * @throws InvalidProductException Caso o valor seja nulo ou negativo.
     */
    public List<Produto> listarPorValorMenorQue(Double valor) {
        validarValor(valor);
        return produtoRepository.findByValorLessThan(valor);
    }

    /**
     * Retorna uma lista de produtos com quantidade maior que o informado.
     * 
     * @param quantidade Quantidade usada como parâmetro.
     * @return Lista de produtos com quantidade maior que o informado.
     * @throws InvalidProductException Caso a quantidade seja nula ou negativa.
     */
    public List<Produto> listarPorQuantidadeMaiorQue(Integer quantidade) {
        validarQuantidade(quantidade);
        return produtoRepository.findByQuantidadeGreaterThan(quantidade);
    }

    /**
     * Retorna uma lista de produtos com quantidade menor que o informado.
     * 
     * @param quantidade Quantidade usada como parâmetro.
     * @return Lista de produtos com quantidade menor que o informado.
     * @throws InvalidProductException Caso a quantidade seja nula ou negativa.
     */
    public List<Produto> listarPorQuantidadeMenorQue(Integer quantidade) {
        validarQuantidade(quantidade);
        return produtoRepository.findByQuantidadeLessThan(quantidade);
    }

    /**
     * Atualiza todos os campos de um produto.
     * 
     * @param id Id do produto que irá ser atualizado.
     * @param produto Produto com os novos dados que substituirão o produto existente.
     * @return Produto atualizado.
     * @throws ResourceNotFoundException Caso o id não existir.
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
     * Atualiza alguns campos de um produto.
     * 
     * @param id Id do produto que irá ser atualizado.
     * @param produto Produto com os novos dados que substituirão o produto existente.
     * @return Produto atualizado.
     * @throws ResourceNotFoundException Caso o id não existir.
     * @throws InvalidProductException Caso algum campo informado seja inválido.
     */
    public Produto atualizarParcial(Integer id, Produto produto) {
        Produto produtoEncontrado = buscarPorId(id);

        String nome = produto.getNome();
        if (nome != null) {
            validarNome(nome);
            produtoEncontrado.setNome(nome);
        }

        Double valor = produto.getValor();
        if(valor != null) {
            validarValor(valor);
            produtoEncontrado.setValor(valor);
        }

        Integer quantidade = produto.getQuantidade();
        if(quantidade != null) {
            validarQuantidade(quantidade);
            produtoEncontrado.setQuantidade(quantidade);
        }

        if(produto.getObservacao() != null) {
            produtoEncontrado.setObservacao(produto.getObservacao());
        }

        return produtoRepository.save(produtoEncontrado);
    }
}