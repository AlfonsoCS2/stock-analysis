package com.alfonsocanal.analisis;


import com.alfonsocanal.analisis.entities.*;
import com.alfonsocanal.analisis.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

//Component
public class DataInitializer implements CommandLineRunner {

    private final EmpresaRepository empresaRepo;
    private final MedidaRepository medidaRepo;
    private final RegistroMedidaRepository registroMedidaRepo;
    private final InfoAnalisisRepository infoRepo;
    private final AnalisisRepository analisisRepo;
    private final ResultadoAnalisisRepository resultadoRepo;

    public DataInitializer(EmpresaRepository empresaRepo,
                           MedidaRepository medidaRepo,
                           RegistroMedidaRepository registroMedidaRepo,
                           InfoAnalisisRepository infoRepo,
                           AnalisisRepository analisisRepo,
                           ResultadoAnalisisRepository resultadoRepo) {
        this.empresaRepo = empresaRepo;
        this.medidaRepo = medidaRepo;
        this.registroMedidaRepo = registroMedidaRepo;
        this.infoRepo = infoRepo;
        this.analisisRepo = analisisRepo;
        this.resultadoRepo = resultadoRepo;
    }

    @Override
    public void run(String... args) throws Exception {
		/*
		 * // Crear medidas Medida rsi = new Medida(); rsi.setNombre("RSI"); Medida ema
		 * = new Medida(); ema.setNombre("EMA"); medidaRepo.saveAll(Arrays.asList(rsi,
		 * ema));
		 * 
		 * // Crear empresa Empresa apple = new Empresa(); apple.setNombre("Apple");
		 * apple.setTicker("AAPL"); empresaRepo.save(apple);
		 * 
		 * // Crear registros de medidas RegistroMedida reg1 = new RegistroMedida();
		 * reg1.setEmpresa(apple); reg1.setRsi(rsi); reg1.setFecha(LocalDate.now());
		 * reg1.setValor(65.0);
		 * 
		 * RegistroMedida reg2 = new RegistroMedida(); reg2.setEmpresa(apple);
		 * reg2.setMedida(ema); reg2.setFecha(LocalDate.now()); reg2.setValor(150.0);
		 */
    	
    	// Crear empresa 
    	Empresa apple = new Empresa(); apple.setNombre("Apple");
    	apple.setTicker("AAPL"); empresaRepo.save(apple);
    	
      //Crear medidas
    	RegistroMedida medidas = new RegistroMedida();
    	medidas.setEmpresa(apple);
    	medidas.setPrecioCierre(255); //datos realistas en USD
    	medidas.setRsi(37.35); //esta info llegaría de la API?
    	medidas.setEma(256.01); //esta info llegaría de la API?
    	medidas.setSma(256.16); //esta info llegaría de la API?
    	medidas.setFecha(LocalDate.now());
    	registroMedidaRepo.save(medidas);
    	
    	apple.getRegistros().add(medidas);
    	
    	

        // Crear InfoAnalisis
        InfoAnalisis info = new InfoAnalisis();
        info.setVersion("1.0");
        info.setDescripcion("Analisis basado en RSI y EMA");
        infoRepo.save(info);

        // Crear Analisis
        Analisis analisis = new Analisis();
        analisis.setEmpresa(apple);
        analisis.setFecha(LocalDate.now());
        analisisRepo.save(analisis);

        // Crear ResultadoAnalisis
        ResultadoAnalisis res = new ResultadoAnalisis();
        res.setAnalisis(analisis);
        res.setInfoAnalisis(info);
        res.setValor(75.0);
        resultadoRepo.save(res);

        System.out.println("Datos de prueba cargados correctamente.");
    }
}

