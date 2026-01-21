package com.alfonsocanal.analisis.services;

import org.springframework.stereotype.Component;

import com.alfonsocanal.analisis.AnalisisResultado;
import com.alfonsocanal.analisis.entities.RegistroMedida;

@Component
public class AnalizadorTecnico {

    public AnalisisResultado calcular(RegistroMedida registro) {

        double rsiScore = 100 - registro.getRsi();

        double emaDiff = (registro.getPrecioCierre() - registro.getEma()) / registro.getEma();
        double emaScore = Math.min(100, Math.max(0, 50 + emaDiff * 500));

        double smaDiff = (registro.getPrecioCierre() - registro.getSma()) / registro.getSma();
        double smaScore = Math.min(100, Math.max(0, 50 + smaDiff * 500));

        double scoreFinal =
                rsiScore * 0.40 +
                emaScore * 0.35 +
                smaScore * 0.25;

        return new AnalisisResultado(
                scoreFinal,
                rsiScore,
                emaScore,
                smaScore
        );
    }
}

