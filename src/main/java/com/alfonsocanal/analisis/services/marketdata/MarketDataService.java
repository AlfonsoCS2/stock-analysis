package com.alfonsocanal.analisis.services.marketdata;

import com.alfonsocanal.analisis.entities.Empresa;
import com.alfonsocanal.analisis.entities.RegistroMedida;
import com.alfonsocanal.analisis.repositories.EmpresaRepository;
import com.alfonsocanal.analisis.repositories.RegistroMedidaRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;

@Service
public class MarketDataService {

    private final MarketDataClient client;
    private final RegistroMedidaRepository registroRepo;

    public MarketDataService(MarketDataClient client, RegistroMedidaRepository registro) {
        this.client = client;
        this.registroRepo = registro;
    }

    /**
     * Devuelve un registro completo con precio, RSI, EMA y SMA
     */
    public void obtenerRegistroCompleto(Empresa empresa) {

        String ticker = empresa.getTicker();

        // 1. Precio de cierre
        Map<String, Object> timeSeries = client.getDailyTimeSeries(ticker);

        String fechaStr = timeSeries.keySet()
                .stream()
                .sorted(Comparator.reverseOrder())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Sin datos diarios"));

        Map<String, String> ultimoDia =
                (Map<String, String>) timeSeries.get(fechaStr);

        double precioCierre =
                Double.parseDouble(ultimoDia.get("4. close"));

        // 2. RSI
        Map<String, Object> rsiSeries =
                client.getRsi(ticker);

        Map<String, String> rsiDia =
                (Map<String, String>) rsiSeries.get(fechaStr);

        if (rsiDia == null) {
            throw new IllegalStateException("RSI no disponible para " + fechaStr);
        }

        double rsi = Double.parseDouble(rsiDia.get("RSI"));

        // 3. EMA
        Map<String, Object> emaSeries =
                client.getEma(ticker);

        Map<String, String> emaDia =
                (Map<String, String>) emaSeries.get(fechaStr);

        if (emaDia == null) {
            throw new IllegalStateException("EMA no disponible para " + fechaStr);
        }

        double ema = Double.parseDouble(emaDia.get("EMA"));

        // 4. SMA
        Map<String, Object> smaSeries =
                client.getSma(ticker);

        Map<String, String> smaDia =
                (Map<String, String>) smaSeries.get(fechaStr);

        if (smaDia == null) {
            throw new IllegalStateException("SMA no disponible para " + fechaStr);
        }

        double sma = Double.parseDouble(smaDia.get("SMA"));

        // Registro final
        RegistroMedida registro = new RegistroMedida();
        registro.setEmpresa(empresa);
        registro.setFecha(LocalDate.parse(fechaStr));
        registro.setPrecioCierre(precioCierre);
        registro.setRsi(rsi);
        registro.setEma(ema);
        registro.setSma(sma);
        registroRepo.save(registro);

       
    }

}
