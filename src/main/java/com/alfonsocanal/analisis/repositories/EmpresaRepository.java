package com.alfonsocanal.analisis.repositories;
import com.alfonsocanal.analisis.entities.Empresa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    @Query("SELECT e FROM Empresa e LEFT JOIN FETCH e.registros WHERE e.id = :id")
    Empresa findByIdWithRegistros(@Param("id") Long id);
    Optional<Empresa> findByTicker(String ticker);
}


