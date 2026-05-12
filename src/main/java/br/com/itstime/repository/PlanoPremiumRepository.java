package br.com.itstime.repository;

import br.com.itstime.model.PlanoPremium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PlanoPremiumRepository extends JpaRepository<PlanoPremium, UUID> {
    // So precisa dos metodos basicos do JpaRepository

}
