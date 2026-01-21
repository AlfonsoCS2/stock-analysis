package com.alfonsocanal.analisis.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class RegistroMedida {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    // DATO BASE
    private double precioCierre;

    // INDICADORES DERIVADOS
    private double rsi;
    private double ema;
    private double sma;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;



    // Getters y setters
    public Long getId() { return id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public double getPrecioCierre() { return precioCierre; }
    public void setPrecioCierre(double precioCierre) { this.precioCierre = precioCierre; }
    public double getRsi() { return rsi; }
    public void setRsi(double rsi) { this.rsi = rsi; }
    public double getEma() { return ema; }
    public void setEma(double ema) { this.ema = ema; }
    public double getSma() { return sma; }
    public void setSma(double sma) { this.sma = sma; }
    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
}

