package com.alfonsocanal.analisis.services.marketdata;

import com.alfonsocanal.analisis.entities.Empresa;
import com.alfonsocanal.analisis.entities.RegistroMedida;
import com.alfonsocanal.analisis.repositories.EmpresaRepository;
import com.alfonsocanal.analisis.services.marketdata.MarketDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class MarketDataTestRunner implements CommandLineRunner {

    private final EmpresaRepository empresaRepo;
    private final MarketDataService marketDataService;

    public MarketDataTestRunner(EmpresaRepository empresaRepo,
                                MarketDataService marketDataService) {
        this.empresaRepo = empresaRepo;
        this.marketDataService = marketDataService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Tomamos una empresa de prueba
    	Empresa empresa = empresaRepo.findByTicker("AAPL")
    	        .orElseGet(() -> {
    	            Empresa nueva = new Empresa();
    	            nueva.setNombre("Apple");
    	            nueva.setTicker("AAPL");
    	            return empresaRepo.save(nueva);
    	        });

        // Invocamos el servicio para obtener y guardar el precio
        RegistroMedida registro = null;//marketDataService.obtenerRegistroCompleto(empresa);

        // Logueamos los resultados
        System.out.println("Precio de cierre actualizado para " + empresa.getNombre());
        System.out.println("Fecha: " + registro.getFecha());
        System.out.println("Precio de cierre: " + registro.getPrecioCierre());
        System.out.println("RSI: " + registro.getRsi());
        System.out.println("EMA: " + registro.getEma());
        System.out.println("SMA: " + registro.getSma());
    }
}
