package inf.laboratorio.museutreze.config;

import inf.laboratorio.museutreze.model.Role;
import inf.laboratorio.museutreze.model.Usuario;
import inf.laboratorio.museutreze.repository.UsuarioRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String @NonNull ... args) {

        if (
                usuarioRepository
                        .findByEmail("admin@museu.com")
                        .isEmpty()
        ) {

            Usuario admin = new Usuario();

            admin.setNomeUsuario("Administrador");

            admin.setEmail("admin@museu.com");

            admin.setSenha(
                    passwordEncoder.encode("admin123")
            );

            admin.setRole(Role.ADMIN);

            usuarioRepository.save(admin);
        }

        if (
                usuarioRepository
                        .findByEmail("bibliotecario@museu.com")
                        .isEmpty()
        ) {

            Usuario bibliotecario = new Usuario();

            bibliotecario.setNomeUsuario("Bibliotecário");

            bibliotecario.setEmail(
                    "bibliotecario@museu.com"
            );

            bibliotecario.setSenha(
                    passwordEncoder.encode("biblio123")
            );

            bibliotecario.setRole(
                    Role.BIBLIOTECARIO
            );

            usuarioRepository.save(
                    bibliotecario
            );
        }
    }
}