package com.alfonsocanal.analisis;
import com.alfonsocanal.analisis.AnalisisBatchService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestAnalisisRunner implements CommandLineRunner {

    private final AnalisisBatchService batch;
    
    public TestAnalisisRunner(AnalisisBatchService batchService) {
        this.batch = batchService;
    } 
    
    @Override
    public void run(String... args) {
    	System.out.println(">>> INICIANDO ANALISIS BATCH");
        batch.ejecutarAnalisisDiario();
        System.out.println(">>> ANALISIS COMPLETADO");
    }
}


