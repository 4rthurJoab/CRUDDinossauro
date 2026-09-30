package com.template.validator;

import java.time.Year;

public class AnoDescobertaValidador {

    public Integer validarEConverter(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }

        try {
            int ano = Integer.parseInt(valor.trim());
            int anoAtual = Year.now().getValue();

            if (ano < 1600 || ano > anoAtual) {
                throw new IllegalArgumentException(
                        "O Ano de Descoberta deve estar entre 1600 e " + anoAtual + "."
                );
            }

            return ano;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "O Ano de Descoberta deve ser um número inteiro válido."
            );
        }
    }
}
