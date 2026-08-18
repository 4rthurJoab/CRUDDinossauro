package com.template.util;

import com.template.model.DinossauroDTO;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class DinossauroFormHelper {

    private DinossauroFormHelper() {}

    public static void carregarNoFormulario(
            DinossauroDTO dino,
            TextField txtEspecie,
            TextField txtSignificadoNome,
            TextField txtOrdem,
            TextField txtEra,
            TextField txtMyaInicio,
            TextField txtMyaFim,
            TextField txtHabitat,
            ComboBox<String> cbDieta,
            TextField txtTipo,
            ComboBox<String> cbLocomocao,
            TextField txtAnoDescoberta) {

        if (dino == null) return;

        txtEspecie.setText(dino.getEspecie() != null ? dino.getEspecie() : "");
        txtSignificadoNome.setText(dino.getSignificadoNome() != null ? dino.getSignificadoNome() : "");
        txtOrdem.setText(dino.getOrdem() != null ? dino.getOrdem() : "");
        txtEra.setText(dino.getEra() != null ? dino.getEra() : "");
        txtMyaInicio.setText(dino.getMyaInicio() != null ? String.valueOf(dino.getMyaInicio()) : "");
        txtMyaFim.setText(dino.getMyaFim() != null ? String.valueOf(dino.getMyaFim()) : "");
        txtHabitat.setText(dino.getHabitat() != null ? dino.getHabitat() : "");
        cbDieta.setValue(dino.getDieta());
        txtTipo.setText(dino.getTipo() != null ? dino.getTipo() : "");
        cbLocomocao.setValue(dino.getLocomocao());
        txtAnoDescoberta.setText(dino.getAnoDescoberta() != null ? String.valueOf(dino.getAnoDescoberta()) : "");
    }

    public static void limparCampos(TextField[] textFields, ComboBox<?>[] comboBoxes) {
        for (TextField tf : textFields) {
            if (tf != null) tf.clear();
        }
        for (ComboBox<?> cb : comboBoxes) {
            if (cb != null) cb.setValue(null);
        }
    }
}