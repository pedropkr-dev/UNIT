package br.com.itstime.repository;

import br.com.itstime.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;


@Repository
public interface GrupoRepository extends JpaRepository<Grupo, UUID> {

    // Aqui é para buscar todos os grupos onde um usuario é membro
    List<Grupo> findByMembros_id(UUID usuarioId);
}
