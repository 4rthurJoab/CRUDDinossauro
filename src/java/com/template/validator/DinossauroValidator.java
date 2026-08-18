package com.template.validator;

import com.template.model.DinossauroDTO;

public class DinossauroValidator {

    public static DinossauroDTO validarEConstruir(
            Integer id,
            String especie,
            String significadoNome,
            String ordem,
            String era,
            String myaInicioStr,
            String myaFimStr,
            String habitat,
            String dieta,
            String tipo,
            String locomocao,
            String anoDescobertaStr) {

        if (especie == null || especie.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'Espécie' é obrigatório.");
        }

        Double myaInicio = null;
        if (myaInicioStr != null && !myaInicioStr.trim().isEmpty()) {
            try {
                myaInicio = Double.parseDouble(myaInicioStr.trim().replace(",", "."));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("O campo 'Mya Início' deve conter um número válido.");
            }
        }

        Double myaFim = null;
        if (myaFimStr != null && !myaFimStr.trim().isEmpty()) {
            try {
                myaFim = Double.parseDouble(myaFimStr.trim().replace(",", "."));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("O campo 'Mya Fim' deve conter um número válido.");
            }
        }

        Integer anoDescoberta = null;
        if (anoDescobertaStr != null && !anoDescobertaStr.trim().isEmpty()) {
            try {
                anoDescoberta = Integer.parseInt(anoDescobertaStr.trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("O campo 'Ano de Descoberta' deve conter um número inteiro.");
            }
        }

        DinossauroDTO dto = new DinossauroDTO();
        dto.setId(id);
        dto.setEspecie(especie.trim());
        dto.setSignificadoNome(significadoNome != null ? significadoNome.trim() : null);
        dto.setOrdem(ordem != null ? ordem.trim() : null);
        dto.setEra(era != null ? era.trim() : null);
        dto.setMyaInicio(myaInicio);
        dto.setMyaFim(myaFim);
        dto.setHabitat(habitat != null ? habitat.trim() : null);
        dto.setDieta(dieta != null ? dieta.trim() : null);
        dto.setTipo(tipo != null ? tipo.trim() : null);
        dto.setLocomocao(locomocao != null ? locomocao.trim() : null);
        dto.setAnoDescoberta(anoDescoberta);

        return dto;
    }
}