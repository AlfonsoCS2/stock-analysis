package com.alfonsocanal.analisis.services;

import com.alfonsocanal.analisis.AnalisisResultado;
import com.alfonsocanal.analisis.entities.*;
import com.alfonsocanal.analisis.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AnalisisService {

    private final EmpresaRepository empresaRepo;
    private final AnalisisRepository analisisRepo;
    private final InfoAnalisisRepository infoRepo;
    private final ResultadoAnalisisRepository resultadoRepo;
    private final RegistroMedidaRepository registroRepo;
    private final AnalizadorTecnico analizador;
    
    private static final Logger log = LoggerFactory.getLogger(AnalisisService.class);

    public AnalisisService(EmpresaRepository empresaRepo,
                           AnalisisRepository analisisRepo,
                           InfoAnalisisRepository infoRepo,
                           RegistroMedidaRepository registroRepo,
                           ResultadoAnalisisRepository resultadoRepo,
                           AnalizadorTecnico analizador) {
        this.empresaRepo = empresaRepo;
        this.analisisRepo = analisisRepo;
        this.infoRepo = infoRepo;
        this.registroRepo = registroRepo;
        this.resultadoRepo = resultadoRepo;
        this.analizador = analizador;
    }
    @Transactional
    public void generarAnalisisDiario(Empresa empresa) {
        // Crear un nuevo análisis
        Analisis analisis = new Analisis();
        analisis.setEmpresa(empresa);
        analisis.setFecha(LocalDate.now());
        analisisRepo.save(analisis);

        // Obtener medidas actuales de la empresa
        RegistroMedida registro = registroRepo
                .findTopByEmpresaOrderByFechaDesc(empresa);

        if (registro == null) {
            throw new IllegalStateException(
                "La empresa no tiene registros de medidas"
            );
        }
        AnalisisResultado AR = analizador.calcular(registro);
		
        
        log.info("[ANALISIS] Empresa: {}", registro.getEmpresa().getNombre());
        log.info("[ANALISIS] Precio cierre: {}", registro.getPrecioCierre());
        log.info("[ANALISIS] Score: {}", AR.getScoreFinal());
        // Crear InfoAnalisis
        InfoAnalisis info = new InfoAnalisis();
        info.setVersion("1.0");
        info.setDescripcion("Analisis con ponderacion 0.40 RSI, 0.35 EMA , 0.25 SMA ");
        infoRepo.save(info);

        // Guardar ResultadoAnalisis
        ResultadoAnalisis resultado = new ResultadoAnalisis();
        resultado.setAnalisis(analisis);
        resultado.setInfoAnalisis(info);
        resultado.setValor(AR.getScoreFinal());
        resultadoRepo.save(resultado);
    }
}

