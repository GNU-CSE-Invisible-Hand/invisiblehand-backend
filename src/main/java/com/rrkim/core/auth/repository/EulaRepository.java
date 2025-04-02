package com.rrkim.core.auth.repository;

import com.rrkim.core.auth.domain.Eula;
import com.rrkim.core.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("EulaRepository")
public interface EulaRepository extends JpaRepository<Eula, Long> {

    List<Eula> findAll();
    List<Eula> findEulaByRequireYn(boolean requireYn);
    @Query("SELECT e FROM Eula e where e.requireYn = true or e.eulaId IN (:eulaIds)")
    List<Eula> findByRequireTrueOrEulaIdIn(List<String> eulaIds);
}
