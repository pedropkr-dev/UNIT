package br.com.itstime.repository;

import br.com.itstime.model.ConfiguracaoRotina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ConfiguracaoRotinaRepository extends JpaRepository<ConfiguracaoRotina, UUID> {

}