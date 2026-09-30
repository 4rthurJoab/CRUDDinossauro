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

        // 1. Validação de campos obrigatórios e MYA (SRP / OCP)
        List<Validador<String>> validadores = new ArrayList<>();
        validadores.add(new CampoObrigatorioValidador("Espécie", especie));
        validadores.add(new CampoObrigatorioValidador("Ordem", ordem));
        validadores.add(new CampoObrigatorioValidador("Era", era));
        validadores.add(new CampoObrigatorioValidador("Dieta", dieta));
        validadores.add(new CampoObrigatorioValidador("Locomoção", locomocao));
        validadores.add(new MyaValidador(myaInicio));
        validadores.add(new MyaValidador(myaFim));

        for (Validador<String> validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                throw new IllegalArgumentException(validador.getMensagemErro());
            }
        }

        // 2. Conversão e validação de intervalo de MYA
        MyaValidador myaHelper = new MyaValidador();
        Double parsedMyaInicio = myaHelper.converter(myaInicio, "O MYA Inicial");
        Double parsedMyaFim = myaHelper.converter(myaFim, "O MYA Final");
        myaHelper.validarIntervalo(parsedMyaInicio, parsedMyaFim);

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
            if (myaInicio != null && !myaInicio.trim().isEmpty()) Double.parseDouble(myaInicio.trim().replace(",", "."));
            if (myaFim != null && !myaFim.trim().isEmpty()) Double.parseDouble(myaFim.trim().replace(",", "."));
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