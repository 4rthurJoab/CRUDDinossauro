package com.template.model;

public class DinossauroDTO {

    private Integer id;
    private String especie;
    private String significadoNome;
    private String ordem;
    private String era;
    private Double myaInicio;
    private Double myaFim;
    private String habitat;
    private String dieta;
    private String tipo;
    private String locomocao;
    private Integer anoDescoberta;

    public DinossauroDTO() {
    }

    public DinossauroDTO(Integer id, String especie, String significadoNome, String ordem, String era,
                         Double myaInicio, Double myaFim, String habitat, String dieta,
                         String tipo, String locomocao, Integer anoDescoberta) {
        this.id = id;
        this.especie = especie;
        this.significadoNome = significadoNome;
        this.ordem = ordem;
        this.era = era;
        this.myaInicio = myaInicio;
        this.myaFim = myaFim;
        this.habitat = habitat;
        this.dieta = dieta;
        this.tipo = tipo;
        this.locomocao = locomocao;
        this.anoDescoberta = anoDescoberta;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getSignificadoNome() { return significadoNome; }
    public void setSignificadoNome(String significadoNome) { this.significadoNome = significadoNome; }

    public String getOrdem() { return ordem; }
    public void setOrdem(String ordem) { this.ordem = ordem; }

    public String getEra() { return era; }
    public void setEra(String era) { this.era = era; }

    public Double getMyaInicio() { return myaInicio; }
    public void setMyaInicio(Double myaInicio) { this.myaInicio = myaInicio; }

    public Double getMyaFim() { return myaFim; }
    public void setMyaFim(Double myaFim) { this.myaFim = myaFim; }

    public String getHabitat() { return habitat; }
    public void setHabitat(String habitat) { this.habitat = habitat; }

    public String getDieta() { return dieta; }
    public void setDieta(String dieta) { this.dieta = dieta; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getLocomocao() { return locomocao; }
    public void setLocomocao(String locomocao) { this.locomocao = locomocao; }

    public Integer getAnoDescoberta() { return anoDescoberta; }
    public void setAnoDescoberta(Integer anoDescoberta) { this.anoDescoberta = anoDescoberta; }
}