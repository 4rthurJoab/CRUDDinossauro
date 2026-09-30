package com.template.validator;

public class MyaValidador {

    public Double converter(String valor, String nomeCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }

        try {
            double numero = Double.parseDouble(
                    valor.trim().replace(",", ".")
            );

            if (numero < 0) {
                throw new IllegalArgumentException(
                        nomeCampo + " não pode ser negativo."
                );
            }

            return numero;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    nomeCampo + " deve ser um número válido."
            );
        }
    }

    public void validarIntervalo(Double inicio, Double fim) {
        if (inicio != null && fim != null && inicio < fim) {
            throw new IllegalArgumentException(
                    "O MYA Inicial deve ser maior ou igual ao MYA Final."
            );
        }
    }
}
