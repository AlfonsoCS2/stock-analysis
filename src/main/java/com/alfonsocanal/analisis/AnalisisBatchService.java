package com.alfonsocanal.analisis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alfonsocanal.analisis.entities.Empresa;
import com.alfonsocanal.analisis.repositories.EmpresaRepository;
import com.alfonsocanal.analisis.services.AnalisisService;
import com.alfonsocanal.analisis.services.marketdata.MarketDataException;
import com.alfonsocanal.analisis.services.marketdata.MarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import jakarta.transaction.Transactional;

@Service
public class AnalisisBatchService {

    private final EmpresaRepository empresaRepo;
    private final MarketDataService marketDataService;
    private final AnalisisService analisisService;
    private static final Logger log =
            LoggerFactory.getLogger(AnalisisBatchService.class);


    public AnalisisBatchService(
            EmpresaRepository empresaRepo,
            MarketDataService marketDataService,
            AnalisisService analisisService
    ) {
        this.empresaRepo = empresaRepo;
        this.marketDataService = marketDataService;
        this.analisisService = analisisService;
    }

    @Transactional
    public void ejecutarAnalisisDiario() {
    	log.info(">>> ENTRANDO EN AnalisisBatchService");

        empresaRepo.findAll().forEach(empresa -> {
            try {
            	Thread.sleep(1100);

                log.info("[BATCH] Procesando empresa {}", empresa.getTicker());

                marketDataService.obtenerRegistroCompleto(empresa);
                analisisService.generarAnalisisDiario(empresa);

            } catch (MarketDataException e) {
                log.warn(
                    "[BATCH] Error obteniendo datos de {}: {}",
                    empresa.getTicker(),
                    e.getMessage()
                );
            } catch (Exception e) {
                log.error(
                    "[BATCH] Error inesperado procesando {}",
                    empresa.getTicker(),
                    e
                );
            }
        });
    }
}
