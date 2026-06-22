package com.template;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // Campos de Entrada de Texto
    @FXML private TextField txtEspecie;
    @FXML private TextField txtSignificadoNome;
    @FXML private TextField txtOrdem;
    @FXML private TextField txtEra;
    @FXML private TextField txtMyaInicio;
    @FXML private TextField txtMyaFim;
    @FXML private TextField txtHabitat;
    @FXML private TextField txtTipo;
    @FXML private TextField txtAnoDescoberta;

    // UX: Substituição de TextFields por ComboBoxes
    @FXML private ComboBox<String> cbDieta;
    @FXML private ComboBox<String> cbLocomocao;

    // UX: Campo de Pesquisa em Tempo Real
    @FXML private TextField txtPesquisa;

    // Botões de Ação
    @FXML private Button btnSalvar;
    @FXML private Button btnAlterar;
    @FXML private Button btnExcluir;
    @FXML private Button btnLimpar;

    // Labels de UI/UX
    @FXML private Label lblMensagem;
    @FXML private Label lblContador;

    // Tabela e suas respectivas Colunas
    @FXML private TableView<DinossauroDTO> tblDinossauro;
    @FXML private TableColumn<DinossauroDTO, Integer> colId;
    @FXML private TableColumn<DinossauroDTO, String> colEspecie;
    @FXML private TableColumn<DinossauroDTO, String> colSignificadoNome;
    @FXML private TableColumn<DinossauroDTO, String> colOrdem;
    @FXML private TableColumn<DinossauroDTO, String> colEra;
    @FXML private TableColumn<DinossauroDTO, Double> colMyaInicio;
    @FXML private TableColumn<DinossauroDTO, Double> colMyaFim;
    @FXML private TableColumn<DinossauroDTO, String> colHabitat;
    @FXML private TableColumn<DinossauroDTO, String> colDieta;
    @FXML private TableColumn<DinossauroDTO, String> colTipo;
    @FXML private TableColumn<DinossauroDTO, String> colLocomocao;
    @FXML private TableColumn<DinossauroDTO, Integer> colAnoDescoberta;

    private int idSelecionado = 0;

    // Lista observável encapsulada para permitir filtros dinâmicos
    private FilteredList<DinossauroDTO> dadosFiltrados;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

        // UX 1: Alimentando as ComboBoxes com dados paleontológicos padronizados
        cbDieta.setItems(FXCollections.observableArrayList("Carnívoro", "Herbívoro", "Onívoro", "Piscívoro"));
        cbLocomocao.setItems(FXCollections.observableArrayList("Bípede", "Quadrúpede", "Semibípede"));

        configurarValidacaoNumerica();
        alternarEstadoBotoes(false);
        carregarDinossauros();
    }

    @FXML
    private void carregarDinossauros() {
        DinossauroDAO dao = new DinossauroDAO();
        ArrayList<DinossauroDTO> lista = dao.listarDinossauros();
        ObservableList<DinossauroDTO> masterData = FXCollections.observableArrayList(lista);

        // UX 2: Configuração do Filtro Dinâmico em tempo real
        dadosFiltrados = new FilteredList<>(masterData, p -> true);

        txtPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            dadosFiltrados.setPredicate(dino -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return dino.getEspecie().toLowerCase().contains(lowerCaseFilter) ||
                        dino.getHabitat().toLowerCase().contains(lowerCaseFilter);
            });
            lblContador.setText("Total de espécies catalogadas: " + dadosFiltrados.size());
        });

        tblDinossauro.setItems(dadosFiltrados);
        lblContador.setText("Total de espécies catalogadas: " + lista.size());
    }

    @FXML
    void btnSalvarAction(ActionEvent event) {
        if (txtEspecie.getText().trim().isEmpty()) {
            exibirMensagem("O campo 'Espécie' é obrigatório para o cadastro!", false);
            // UX 3: Feedback visual com borda vermelha indicando erro no campo
            txtEspecie.setStyle("-fx-border-color: #ef4444; -fx-border-width: 1.5px; -fx-border-radius: 4px;");
            txtEspecie.requestFocus();
            return;
        }

        DinossauroDTO dino = obterDadosDoFormulario();
        DinossauroDAO dao = new DinossauroDAO();
        dao.inserirDinossauro(dino);

        exibirMensagem("Dinossauro '" + dino.getEspecie() + "' catalogado com sucesso!", true);
        carregarDinossauros();
        limparCampos();
    }

    @FXML
    void btnAlterarAction(ActionEvent event) {
        if (idSelecionado != 0) {
            DinossauroDTO dino = obterDadosDoFormulario();
            dino.setId(idSelecionado);

            DinossauroDAO dao = new DinossauroDAO();
            dao.atualizarDinossauro(dino);

            exibirMensagem("Dados do dinossauro atualizados com sucesso!", true);
            carregarDinossauros();
            limparCampos();
        }
    }

    @FXML
    void btnExcluirAction(ActionEvent event) {
        if (idSelecionado != 0) {
            Alert alertaConfirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            alertaConfirmacao.setTitle("Confirmar Exclusão");
            alertaConfirmacao.setHeaderText("Excluir Registro de Fóssil");
            alertaConfirmacao.setContentText("Atenção! Você tem certeza que deseja apagar este dinossauro permanentemente?");

            Optional<ButtonType> resultado = alertaConfirmacao.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                DinossauroDAO dao = new DinossauroDAO();
                dao.excluirDinossauro(idSelecionado);

                exibirMensagem("Registro removido do arquivo com sucesso.", true);
                carregarDinossauros();
                limparCampos();
            } else {
                exibirMensagem("Operação de exclusão cancelada.", false);
            }
        }
    }

    @FXML
    void btnLimparAction(ActionEvent event) {
        limparCampos();
        lblMensagem.setText("");
    }

    @FXML
    void carregarCampos(MouseEvent event) {
        DinossauroDTO dino = tblDinossauro.getSelectionModel().getSelectedItem();

        if (dino != null) {
            idSelecionado = dino.getId();

            txtEspecie.setText(dino.getEspecie());
            txtSignificadoNome.setText(dino.getSignificadoNome());
            txtOrdem.setText(dino.getOrdem());
            txtEra.setText(dino.getEra());
            txtHabitat.setText(dino.getHabitat());
            txtTipo.setText(dino.getTipo());

            // Tratamento para ComboBoxes mapeadas
            cbDieta.setValue(dino.getDieta());
            cbLocomocao.setValue(dino.getLocomocao());

            txtMyaInicio.setText(String.valueOf(dino.getMyaInicio()));
            txtMyaFim.setText(String.valueOf(dino.getMyaFim()));
            txtAnoDescoberta.setText(String.valueOf(dino.getAnoDescoberta()));

            txtEspecie.setStyle(""); // Limpa erro visual caso mude de ação
            alternarEstadoBotoes(true);
        }
    }

    private void toggleBotaoEstado(boolean itemSelecionado) {
        btnSalvar.setDisable(itemSelecionado);
        btnAlterar.setDisable(!itemSelecionado);
        btnExcluir.setDisable(!itemSelecionado);
    }

    private void alternarEstadoBotoes(boolean itemSelecionado) {
        toggleBotaoEstado(itemSelecionado);
    }

    private void configurarValidacaoNumerica() {
        txtAnoDescoberta.textProperty().addListener((obs, antigo, novo) -> {
            if (!novo.matches("\\d*")) {
                txtAnoDescoberta.setText(novo.replaceAll("[^\\d]", ""));
            }
        });

        txtMyaInicio.textProperty().addListener((obs, antigo, novo) -> {
            if (!novo.matches("\\d*(\\.\\d*)?")) {
                txtMyaInicio.setText(antigo);
            }
        });

        txtMyaFim.textProperty().addListener((obs, antigo, novo) -> {
            if (!novo.matches("\\d*(\\.\\d*)?")) {
                txtMyaFim.setText(antigo);
            }
        });
    }

    private void exibirMensagem(String texto, boolean ehSucesso) {
        lblMensagem.setText(texto);
        if (ehSucesso) {
            lblMensagem.setStyle("-fx-text-fill: #16a34a; -fx-font-weight: bold;");
        } else {
            lblMensagem.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
        }
    }

    private DinossauroDTO obterDadosDoFormulario() {
        DinossauroDTO dino = new DinossauroDTO();

        dino.setEspecie(txtEspecie.getText());
        dino.setSignificadoNome(txtSignificadoNome.getText());
        dino.setOrdem(txtOrdem.getText());
        dino.setEra(txtEra.getText());
        dino.setHabitat(txtHabitat.getText());
        dino.setTipo(txtTipo.getText());

        // Captura o valor das ComboBoxes evitando NullPointer
        dino.setDieta(cbDieta.getValue() != null ? cbDieta.getValue() : "");
        dino.setLocomocao(cbLocomocao.getValue() != null ? cbLocomocao.getValue() : "");

        try {
            dino.setMyaInicio(Double.parseDouble(txtMyaInicio.getText()));
            dino.setMyaFim(Double.parseDouble(txtMyaFim.getText()));
            dino.setAnoDescoberta(Integer.parseInt(txtAnoDescoberta.getText()));
        } catch (NumberFormatException e) {
            dino.setMyaInicio(0.0);
            dino.setMyaFim(0.0);
            dino.setAnoDescoberta(0);
        }

        return dino;
    }

    private void limparCampos() {
        idSelecionado = 0;

        txtEspecie.clear();
        txtSignificadoNome.clear();
        txtOrdem.clear();
        txtEra.clear();
        txtMyaInicio.clear();
        txtMyaFim.clear();
        txtHabitat.clear();
        txtTipo.clear();

        // Limpa as ComboBoxes estruturadas
        cbDieta.setValue(null);
        cbLocomocao.setValue(null);

        txtAnoDescoberta.clear();
        txtPesquisa.clear();

        txtEspecie.setStyle(""); // Remove o estilo customizado de erro visual
        tblDinossauro.getSelectionModel().clearSelection();
        alternarEstadoBotoes(false);
        txtEspecie.requestFocus();
    }
}