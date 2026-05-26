package com.acervo.config;

import com.acervo.model.Usuario;
import com.acervo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Cria o usuário admin padrão na primeira vez que o sistema inicia
@Component
public class CargaInicial implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) {
        if (!usuarioRepository.existsByEmail("admin@museu.com")) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador do Museu");
            admin.setEmail("admin@museu.com");
            admin.setSenha("admin123");
            admin.setPapel("ADMINISTRADOR");
            usuarioRepository.save(admin);
            System.out.println("Admin padrão criado: admin@museu.com / admin123");
        }
    }
}
