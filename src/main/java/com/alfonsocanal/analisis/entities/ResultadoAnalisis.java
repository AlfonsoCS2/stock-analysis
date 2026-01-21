package com.alfonsocanal.analisis.entities;

import jakarta.persistence.*;

@Entity
public class ResultadoAnalisis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double valor; // 0 a 100

    @ManyToOne
    @JoinColumn(name = "analisis_id")
    private Analisis analisis;

    @ManyToOne
    @JoinColumn(name = "infoanalisis_id")
    private InfoAnalisis infoAnalisis;

    // Getters y setters
    public Long getId() { return id; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public Analisis getAnalisis() { return analisis; }
    public void setAnalisis(Analisis analisis) { this.analisis = analisis; }
    public InfoAnalisis getInfoAnalisis() { return infoAnalisis; }
    public void setInfoAnalisis(InfoAnalisis infoAnalisis) { this.infoAnalisis = infoAnalisis; }
}

