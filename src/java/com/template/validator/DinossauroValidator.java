package com.template.validator;

import com.template.model.DinossauroDTO;

public class DinossauroValidator implements IDinossauroValidator {

    private final MyaValidador myaValidador;
    private final AnoDescobertaValidador anoValidador;

    public DinossauroValidator() {
        this(new MyaValidador(), new AnoDescobertaValidador());
    }

    public DinossauroValidator(MyaValidador myaValidador, AnoDescobertaValidador anoValidador) {
        this.myaValidador = myaValidador;
        this.anoValidador = anoValidador;
    }

    @Override
    public DinossauroDTO validarEConstruir(
            Integer id,
            String especie,
            String significadoNome,
            String ordem,
            String era,
            String myaInicio,
            String myaFim,
            String habitat,
            String dieta,
            String tipo,
            String locomocao,
            String anoDescoberta) {

        validarObrigatorio("Espécie", especie);
        validarObrigatorio("Ordem", ordem);
        validarObrigatorio("Era", era);
        validarObrigatorio("Dieta", dieta);
        validarObrigatorio("Locomoção", locomocao);

        Double inicio = myaValidador.converter(
                myaInicio,
                "MYA Inicial"
        );

        Double fim = myaValidador.converter(
                myaFim,
                "MYA Final"
        );

        myaValidador.validarIntervalo(inicio, fim);

        Integer ano = anoValidador.validarEConverter(anoDescoberta);

        return new DinossauroDTO(
                id,
                especie.trim(),
                significadoNome != null ? significadoNome.trim() : null,
                ordem.trim(),
                era.trim(),
                inicio,
                fim,
                habitat != null ? habitat.trim() : null,
                dieta,
                tipo != null ? tipo.trim() : null,
                locomocao,
                ano
        );
    }

    private void validarObrigatorio(String nomeCampo, String valor) {
        CampoObrigatorioValidador validador = new CampoObrigatorioValidador(nomeCampo, valor);
        if (!validador.validar(valor)) {
            throw new IllegalArgumentException(validador.getMensagemErro());
        }
    }

    @Override
    public boolean validarCampoObrigatorio(String nomeCampo, String valor) {
        return new CampoObrigatorioValidador(nomeCampo, valor).validar(valor);
    }

    @Override
    public boolean validarMya(String myaInicio, String myaFim) {
        try {
            Double inicio = myaValidador.converter(myaInicio, "MYA Inicial");
            Double fim = myaValidador.converter(myaFim, "MYA Final");
            myaValidador.validarIntervalo(inicio, fim);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean validarAnoDescoberta(String anoDescoberta) {
        try {
            anoValidador.validarEConverter(anoDescoberta);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}