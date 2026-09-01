package com.template.validator;

import com.template.model.DinossauroDTO;

public interface IDinossauroValidator {

    DinossauroDTO validarEConstruir(
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
            String anoDescoberta
    );

    boolean validarCampoObrigatorio(String nomeCampo, String valor);

    boolean validarMya(String myaInicio, String myaFim);

    boolean validarAnoDescoberta(String anoDescoberta);
}