package com.template.validator;

import com.template.model.DinossauroDTO;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class DinossauroValidator implements IDinossauroValidator {

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

        // 1. Validação de campos obrigatórios (SRP / OCP)
        List<Validador<String>> validadores = new ArrayList<>();
        validadores.add(new CampoObrigatorioValidador("Espécie", especie));
        validadores.add(new CampoObrigatorioValidador("Ordem", ordem));
        validadores.add(new CampoObrigatorioValidador("Era", era));
        validadores.add(new CampoObrigatorioValidador("Dieta", dieta));
        validadores.add(new CampoObrigatorioValidador("Locomoção", locomocao));

        for (Validador<String> validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                throw new IllegalArgumentException(validador.getMensagemErro());
            }
        }

        // 2. Validação e conversão de MYA
        Double parsedMyaInicio = null;
        Double parsedMyaFim = null;

        try {
            if (myaInicio != null && !myaInicio.trim().isEmpty()) {
                parsedMyaInicio = Double.parseDouble(myaInicio.trim().replace(",", "."));
                if (parsedMyaInicio < 0) {
                    throw new IllegalArgumentException("O MYA Inicial não pode ser negativo.");
                }
            }
            if (myaFim != null && !myaFim.trim().isEmpty()) {
                parsedMyaFim = Double.parseDouble(myaFim.trim().replace(",", "."));
                if (parsedMyaFim < 0) {
                    throw new IllegalArgumentException("O MYA Final não pode ser negativo.");
                }
            }
            if (parsedMyaInicio != null && parsedMyaFim != null && parsedMyaInicio < parsedMyaFim) {
                throw new IllegalArgumentException("O MYA Inicial deve ser maior ou igual ao MYA Final (tempo geológico decorrido).");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Informe valores numéricos válidos para os campos de MYA.");
        }

        // 3. Validação e conversão do Ano de Descoberta
        Integer parsedAnoDescoberta = null;
        if (anoDescoberta != null && !anoDescoberta.trim().isEmpty()) {
            try {
                parsedAnoDescoberta = Integer.parseInt(anoDescoberta.trim());
                int anoAtual = Year.now().getValue();
                if (parsedAnoDescoberta < 1600 || parsedAnoDescoberta > anoAtual) {
                    throw new IllegalArgumentException("O Ano de Descoberta deve estar entre 1600 e " + anoAtual + ".");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("O Ano de Descoberta deve ser um número inteiro válido.");
            }
        }

        // 4. Retorna a entidade construída
        return new DinossauroDTO(
                id,
                especie != null ? especie.trim() : null,
                significadoNome != null ? significadoNome.trim() : null,
                ordem != null ? ordem.trim() : null,
                era != null ? era.trim() : null,
                parsedMyaInicio,
                parsedMyaFim,
                habitat != null ? habitat.trim() : null,
                dieta,
                tipo != null ? tipo.trim() : null,
                locomocao,
                parsedAnoDescoberta
        );
    }

    @Override
    public boolean validarCampoObrigatorio(String nomeCampo, String valor) {
        return valor != null && !valor.trim().isEmpty();
    }

    @Override
    public boolean validarMya(String myaInicio, String myaFim) {
        try {
            if (myaInicio != null && !myaInicio.trim().isEmpty()) Double.parseDouble(myaInicio.trim());
            if (myaFim != null && !myaFim.trim().isEmpty()) Double.parseDouble(myaFim.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean validarAnoDescoberta(String anoDescoberta) {
        if (anoDescoberta == null || anoDescoberta.trim().isEmpty()) return true;
        try {
            int ano = Integer.parseInt(anoDescoberta.trim());
            return ano >= 1600 && ano <= Year.now().getValue();
        } catch (NumberFormatException e) {
            return false;
        }
    }
}