package inf.laboratorio.museutreze.repository;

import inf.laboratorio.museutreze.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNomeUsuario(String login);
    Usuario findByEmail(String email);
    Usuario findById(long id);

    List<Usuario> findAllByNomeUsuario(String nomeUsuario);
    List<Usuario> findAllByEmail(String email);

}
