package com.teste.primeiro_exemplo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teste.primeiro_exemplo.model.Produto;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    /**
     * Método que retorna um produto pelo nome.
     * @param nome do produto.
     * @return Retorna um Optional contendo produto caso seja encontrado.
     */
    public Optional<Produto> findByName(String nome);

    /**
     * Método que retorna uma lista de produto com valor maior que o parâmetro.
     * @param valor usado como parâmetro.
     * @return Retorna uma lista de produtos que possuem valor maior que o parâmetro.
     */
    public List<Produto> findByValorGreaterThan(Double valor);

    /**
     * Métoodo que retorna uma lista de produto com valor menor que o parâmetro.
     * @param valor usado como parâmetro
     * @return Retorna uma lista de produtos que possuem valor menor que o parâmetro.
     */
    public List<Produto> findByValorLessThan(Double valor);

    /**
     * Método que retorna uma lista de produto com quantidade maior que o parâmetro.
     * @param valor usado como parâmetro.
     * @return Retorna uma lista de produtos que possuem quantidade maior que o parâmetro.
     */
    public List<Produto> findByQuantidadeGreaterThan(Integer quantidade);

    /**
     * Métoodo que retorna uma lista de produto com quantidade menor que o parâmetro.
     * @param valor usado como parâmetro
     * @return Retorna uma lista de produtos que possuem quantidade menor que o parâmetro.
     */
    public List<Produto> findByQuantidadeLessThan(Integer quantidade);
}