package com.teste.primeiro_exemplo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teste.primeiro_exemplo.model.Produto;

/**
 * Repositório responsável pela comunicação com o banco de dados
 * para operações relacionadas à entidade Produto.
 * 
 * @author Gabriel Oliveira
 * @version 1.0
 * @since 2026
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    /**
     * Busca um produto pelo nome.
     * 
     * @param nome Nome do produto.
     * @return Optional contendo produto caso seja encontrado.
     */
    Optional<Produto> findByNome(String nome);

    /**
     * Retorna uma lista de produtos com valor maior que o informado.
     * 
     * @param valor Valor mínimo para filtro.
     * @return Lista de produtos com valor maior que o informado.
     */
    List<Produto> findByValorGreaterThan(Double valor);

    /**
     * Retorna uma lista de produtos com valor menor que o informado.
     * 
     * @param valor Valor máximo para filtro.
     * @return Lista de produtos com valor menor que o informado.
     */
    List<Produto> findByValorLessThan(Double valor);

    /**
     * Retorna uma lista de produtos com quantidade maior que o informado.
     * 
     * @param quantidade Quantidade mínima para filtro.
     * @return Lista de produtos com quantidade maior que o informado.
     */
    List<Produto> findByQuantidadeGreaterThan(Integer quantidade);

    /**
     * Retorna uma lista de produtos com quantidade menor que o informado.
     * 
     * @param quantidade Quantidade máxima para filtro.
     * @return Lista de produtos com quantidade menor que o informado.
     */
    List<Produto> findByQuantidadeLessThan(Integer quantidade);
}