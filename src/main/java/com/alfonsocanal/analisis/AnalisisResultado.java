package com.alfonsocanal.analisis;

public class AnalisisResultado {

    private double scoreFinal;
    private double rsiScore;
    private double emaScore;
    private double smaScore;

    public AnalisisResultado(
            double scoreFinal,
            double rsiScore,
            double emaScore,
            double smaScore) {

        this.scoreFinal = scoreFinal;
        this.rsiScore = rsiScore;
        this.emaScore = emaScore;
        this.smaScore = smaScore;
    }

    public double getScoreFinal() {
        return scoreFinal;
    }

    public double getRsiScore() {
        return rsiScore;
    }

    public double getEmaScore() {
        return emaScore;
    }

    public double getSmaScore() {
        return smaScore;
    }
}


