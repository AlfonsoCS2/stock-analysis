package com.alfonsocanal.analisis.services.indicators;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class IndicadoresTecnicos {

    public double calcularSMA(List<Double> precios) {
        return precios.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElseThrow();
    }

    public double calcularEMA(List<Double> precios) {
        double k = 2.0 / (precios.size() + 1);
        double ema = precios.get(0);

        for (double precio : precios) {
            ema = precio * k + ema * (1 - k);
        }
        return ema;
    }

    public double calcularRSI(List<Double> precios) {
        double ganancias = 0;
        double perdidas = 0;

        for (int i = 1; i < precios.size(); i++) {
            double diff = precios.get(i) - precios.get(i - 1);
            if (diff > 0) ganancias += diff;
            else perdidas -= diff;
        }

        double rs = ganancias / (perdidas == 0 ? 1 : perdidas);
        return 100 - (100 / (1 + rs));
    }
}

