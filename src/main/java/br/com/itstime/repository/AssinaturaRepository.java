package br.com.itstime.repository;

import br.com.itstime.model.Assinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface AssinaturaRepository extends JpaRepository<Assinatura, UUID> {
    // So precisa dos metodos basicos do JpaRepository

}
