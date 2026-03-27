package br.com.infoservic.ctoConference.repository;

import br.com.infoservic.ctoConference.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Optional<Usuario> findByNome(String nome);


    // Busca parcial com ordenação
    List<Usuario> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

    // Busca por nome E email:
    List<Usuario> findByNomeContainingIgnoreCaseOrEmailContainingIgnoreCaseOrderByNomeAsc(
            String nome, String email);
}

