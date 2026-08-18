package com.template.util;

import com.template.model.DinossauroDTO;

public class DinossauroFilterUtil {

    private DinossauroFilterUtil() {}

    public static boolean atendeFiltro(DinossauroDTO dino, String termo) {
        if (termo == null || termo.isBlank()) return true;
        if (dino == null) return false;

        String termoNorm = termo.toLowerCase().trim();
        boolean matchEspecie = dino.getEspecie() != null && dino.getEspecie().toLowerCase().contains(termoNorm);
        boolean matchHabitat = dino.getHabitat() != null && dino.getHabitat().toLowerCase().contains(termoNorm);
        boolean matchOrdem = dino.getOrdem() != null && dino.getOrdem().toLowerCase().contains(termoNorm);

        return matchEspecie || matchHabitat || matchOrdem;
    }
}