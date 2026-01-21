package com.alfonsocanal.analisis.repositories;

import com.alfonsocanal.analisis.entities.Empresa;
import com.alfonsocanal.analisis.entities.RegistroMedida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroMedidaRepository extends JpaRepository<RegistroMedida, Long> {
		RegistroMedida findTopByEmpresaOrderByFechaDesc(Empresa empresa);
}