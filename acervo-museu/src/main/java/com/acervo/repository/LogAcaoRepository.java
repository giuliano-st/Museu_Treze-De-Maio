package com.acervo.repository;

import com.acervo.model.LogAcao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LogAcaoRepository extends JpaRepository<LogAcao, Long> {
    List<LogAcao> findAllByOrderByDataHoraDesc();
}
