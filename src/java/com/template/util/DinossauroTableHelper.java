package com.template.util;

import com.template.model.DinossauroDTO;
import com.template.service.IDinossauroService;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class DinossauroTableHelper {

    private DinossauroTableHelper() {}

    public static void configurarColunas(
            TableView<DinossauroDTO> tabela,
            TableColumn<DinossauroDTO, Integer> colId,
            TableColumn<DinossauroDTO, String> colEspecie,
            TableColumn<DinossauroDTO, String> colSignificadoNome,
            TableColumn<DinossauroDTO, String> colOrdem,
            TableColumn<DinossauroDTO, String> colEra,
            TableColumn<DinossauroDTO, Double> colMyaInicio,
            TableColumn<DinossauroDTO, Double> colMyaFim,
            TableColumn<DinossauroDTO, String> colHabitat,
            TableColumn<DinossauroDTO, String> colDieta,
            TableColumn<DinossauroDTO, String> colTipo,
            TableColumn<DinossauroDTO, String> colLocomocao,
            TableColumn<DinossauroDTO, Integer> colAnoDescoberta,
            FilteredList<DinossauroDTO> dadosFiltrados) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        colSignificadoNome.setCellValueFactory(new PropertyValueFactory<>("significadoNome"));
        colOrdem.setCellValueFactory(new PropertyValueFactory<>("ordem"));
        colEra.setCellValueFactory(new PropertyValueFactory<>("era"));
        colMyaInicio.setCellValueFactory(new PropertyValueFactory<>("myaInicio"));
        colMyaFim.setCellValueFactory(new PropertyValueFactory<>("myaFim"));
        colHabitat.setCellValueFactory(new PropertyValueFactory<>("habitat"));
        colDieta.setCellValueFactory(new PropertyValueFactory<>("dieta"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colLocomocao.setCellValueFactory(new PropertyValueFactory<>("locomocao"));
        colAnoDescoberta.setCellValueFactory(new PropertyValueFactory<>("anoDescoberta"));

        tabela.setItems(dadosFiltrados);
    }

    public static void configurarFiltroPesquisa(
            TextField txtPesquisa,
            FilteredList<DinossauroDTO> dadosFiltrados,
            Runnable onFiltroAlterado) {

        txtPesquisa.textProperty().addListener((obs, antigo, novo) -> {
            dadosFiltrados.setPredicate(dino -> DinossauroFilterUtil.atendeFiltro(dino, novo));
            if (onFiltroAlterado != null) {
                onFiltroAlterado.run();
            }
        });
    }

    public static void atualizarTabela(
            ObservableList<DinossauroDTO> dadosTabela,
            IDinossauroService service,
            Runnable callbackSucesso) {

        try {
            dadosTabela.setAll(service.listarTodos());
            if (callbackSucesso != null) {
                callbackSucesso.run();
            }
        } catch (Exception e) {
            AlertUtil.exibirMensagem(Alert.AlertType.ERROR, "Erro", "Erro ao carregar dados: " + e.getMessage());
        }
    }

    public static void atualizarContador(Label lblContador, int total) {
        if (lblContador != null) {
            lblContador.setText("Total de espécies exibidas: " + total);
        }
    }
}
