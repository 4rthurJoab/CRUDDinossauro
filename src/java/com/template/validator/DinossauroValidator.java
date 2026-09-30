package com.template.validator;

import com.template.model.DinossauroDTO;
import java.util.ArrayList;
import java.util.List;

public class DinossauroValidator implements IDinossauroValidator {

    @Override
    public boolean validarDinossauro(DinossauroDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("O DTO do Dinossauro não pode ser nulo.");
        }

        List<Validador<String>> validadores = new ArrayList<>();

        // Validações de campos obrigatórios
        validadores.add(new CampoObrigatorioValidador("Espécie", dto.getEspecie()));
        validadores.add(new CampoObrigatorioValidador("Ordem", dto.getOrdem()));
        validadores.add(new CampoObrigatorioValidador("Era", dto.getEra()));
        validadores.add(new CampoObrigatorioValidador("Dieta", dto.getDieta()));
        validadores.add(new CampoObrigatorioValidador("Locomoção", dto.getLocomocao()));

        // Validações de regras específicas (MYA e Ano de Descoberta)
        validadores.add(new MyaValidador(dto.getMyaInicio() != null ? dto.getMyaInicio().toString() : null));
        validadores.add(new MyaValidador(dto.getMyaFim() != null ? dto.getMyaFim().toString() : null));

        // Iteração de validação baseada no padrão do código de referência
        for (Validador<String> validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                throw new IllegalArgumentException(validador.getMensagemErro());
            }
        }

        // Caso haja uma validação cruzada entre myaInicio e myaFim
        if (dto.getMyaInicio() != null && dto.getMyaFim() != null) {
            new MyaValidador().validarIntervalo(dto.getMyaInicio(), dto.getMyaFim());
        }

        return true;
    }
}