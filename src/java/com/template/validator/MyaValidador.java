package com.template.validator;

public class MyaValidador implements Validador<String> {

    private final String valor;

    // Construtor vazio mantido por segurança caso outra classe do projeto o utilize
    public MyaValidador() {
        this.valor = null;
    }

    public MyaValidador(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar(String valor) {

        if (valor == null || valor.trim().isEmpty()) {
            return true;
        }

        try {
            double numero = Double.parseDouble(
                    valor.trim().replace(",", ".")
            );

            // Se for menor que zero, retorna false para o for disparar o getMensagemErro()
            if (numero < 0) {
                return false;
            }

            return true;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Informe valores numéricos válidos para os campos de MYA."
            );
        }
    }

    @Override
    public String getMensagemErro() {
        return "ERRO! O valor de MYA deve ser maior ou igual a 0.";
    }

    @Override
    public String getValor() {
        return this.valor; // <-- Aqui estava retornando "" em vez de this.valor
    }

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
                    "O MYA Inicial deve ser maior ou igual ao MYA Final (tempo geológico decorrido)."
            );
        }
    }
}