package com.acervo.repository;

import com.acervo.model.Obra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ObraRepository extends JpaRepository<Obra, Long> {


    @Query("SELECT o FROM Obra o WHERE " +
           "(:termo IS NULL OR LOWER(o.titulo) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(o.autor) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(o.assuntos) LIKE LOWER(CONCAT('%', :termo, '%'))) AND " +
           "(:tipo IS NULL OR o.tipo = :tipo) AND " +
           "(:categoria IS NULL OR o.categoria = :categoria) AND " +
           "(:dataInicio IS NULL OR o.dataPublicacao >= :dataInicio) AND " +
           "(:dataFim IS NULL OR o.dataPublicacao <= :dataFim)")
    List<Obra> buscarComFiltros(
            @Param("termo") String termo,
            @Param("tipo") String tipo,
            @Param("categoria") String categoria,
            @Param("dataInicio") String dataInicio,
            @Param("dataFim") String dataFim
    );


    List<Obra> findTop5ByOrderByContadorBuscasDesc();
}
