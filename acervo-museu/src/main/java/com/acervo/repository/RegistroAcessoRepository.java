package com.acervo.repository;

import com.acervo.model.RegistroAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistroAcessoRepository extends JpaRepository<RegistroAcesso, Long> {
    // Retorna todos os acessos, do mais recente ao mais antigo
    List<RegistroAcesso> findAllByOrderByDataHoraDesc();
}
