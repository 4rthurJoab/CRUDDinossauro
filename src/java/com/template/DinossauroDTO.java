package com.template;

public class DinossauroDTO {
    private int id;
    private String especie;
    private String significadoNome;
    private String ordem;
    private String era;
    private double myaInicio;
    private double myaFim;
    private String habitat;
    private String dieta;
    private String tipo;
    private String locomocao;
    private int anoDescoberta;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getSignificadoNome() { return significadoNome; }
    public void setSignificadoNome(String significadoNome) { this.significadoNome = significadoNome; }

    public String getOrdem() { return ordem; }
    public void setOrdem(String ordem) { this.ordem = ordem; }

    public String getEra() { return era; }
    public void setEra(String era) { this.era = era; }

    public double getMyaInicio() { return myaInicio; }
    public void setMyaInicio(double myaInicio) { this.myaInicio = myaInicio; }

    public double getMyaFim() { return myaFim; }
    public void setMyaFim(double myaFim) { this.myaFim = myaFim; }

    public String getHabitat() { return habitat; }
    public void setHabitat(String habitat) { this.habitat = habitat; }

    public String getDieta() { return dieta; }
    public void setDieta(String dieta) { this.dieta = dieta; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getLocomocao() { return locomocao; }
    public void setLocomocao(String locomocao) { this.locomocao = locomocao; }

    public int getAnoDescoberta() { return anoDescoberta; }
    public void setAnoDescoberta(int anoDescoberta) { this.anoDescoberta = anoDescoberta; }
}