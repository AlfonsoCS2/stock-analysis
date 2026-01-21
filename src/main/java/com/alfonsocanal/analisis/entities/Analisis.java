package com.alfonsocanal.analisis.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Analisis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @OneToMany(mappedBy = "analisis", cascade = CascadeType.ALL)
    private List<ResultadoAnalisis> resultados;

    // Getters y setters
    public Long getId() { return id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
    public List<ResultadoAnalisis> getResultados() { return resultados; }
    public void setResultados(List<ResultadoAnalisis> resultados) { this.resultados = resultados; }
}
