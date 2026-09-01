package com.template.controller;

import com.template.model.DinossauroDTO;
import com.template.service.DinossauroService;
import com.template.util.AlertUtil;
import com.template.util.DinossauroFilterUtil;
import com.template.util.DinossauroFormHelper;
import com.template.validator.DinossauroValidator;
import com.template.validator.IDinossauroValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {

    @FXML private TextField txtPesquisa;
    @FXML private TextField txtEspecie;
    @FXML private TextField txtSignificadoNome;
    @FXML private TextField txtOrdem;
    @FXML private TextField txtEra;
    @FXML private TextField txtMyaInicio;
    @FXML private TextField txtMyaFim;
    @FXML private TextField txtHabitat;
    @FXML private ComboBox<String> cbDieta;
    @FXML private TextField txtTipo;
    @FXML private ComboBox<String> cbLocomocao;
    @FXML private TextField txtAnoDescoberta;

    @FXML private Button btnSalvar;
    @FXML private Button btnAlterar;
    @FXML private Button btnExcluir;
    @FXML private Button btnLimpar;

    @FXML private Label lblMensagem;
    @FXML private Label lblContador;

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

    private Integer idSelecionado = null;
    private final DinossauroService service = new DinossauroService();
    private final IDinossauroValidator validator = new DinossauroValidator();
    private final ObservableList<DinossauroDTO> dadosTabela = FXCollections.observableArrayList();
    private FilteredList<DinossauroDTO> dadosFiltrados;

    @FXML
    public void initialize() {
        configurarColunas();
        configurarComboBoxes();
        configurarFiltroPesquisa();
        configurarSelecaoTabela();
        atualizarTabela();
    }

    @FXML
    private void btnSalvarAction() {
        idSelecionado = null;
        executarGravacao("Registro salvo com sucesso!");
    }

    @FXML
    private void btnAlterarAction() {
        if (idSelecionado == null) {
            AlertUtil.exibirMensagem(Alert.AlertType.WARNING, "Atenção", "Selecione um registro na tabela para alterar.");
            return;
        }
        executarGravacao("Registro atualizado com sucesso!");
    }

    @FXML
    private void btnExcluirAction() {
        if (idSelecionado == null) {
            AlertUtil.exibirMensagem(Alert.AlertType.WARNING, "Atenção", "Selecione um registro na tabela para excluir.");
            return;
        }

        if (AlertUtil.exibirConfirmacao("Confirmação", "Deseja excluir a espécie selecionada?")) {
            try {
                service.excluir(idSelecionado);
                definirMensagemFeedback("Registro excluído com sucesso!", "#16a34a");
                limparFormulario();
                atualizarTabela();
            } catch (Exception e) {
                AlertUtil.exibirMensagem(Alert.AlertType.ERROR, "Erro", "Falha ao excluir: " + e.getMessage());
            }
        }
    }

    @FXML
    private void btnLimparAction() {
        limparFormulario();
    }

    @FXML
    private void carregarCampos() {
        DinossauroDTO sel = tblDinossauro.getSelectionModel().getSelectedItem();
        if (sel == null) return;

        idSelecionado = sel.getId();
        DinossauroFormHelper.carregarNoFormulario(
                sel,
                txtEspecie,
                txtSignificadoNome,
                txtOrdem,
                txtEra,
                txtMyaInicio,
                txtMyaFim,
                txtHabitat,
                cbDieta,
                txtTipo,
                cbLocomocao,
                txtAnoDescoberta
        );
    }

    private void executarGravacao(String mensagemSucesso) {
        try {
            DinossauroDTO dino = validator.validarEConstruir(
                    idSelecionado,
                    txtEspecie.getText(),
                    txtSignificadoNome.getText(),
                    txtOrdem.getText(),
                    txtEra.getText(),
                    txtMyaInicio.getText(),
                    txtMyaFim.getText(),
                    txtHabitat.getText(),
                    cbDieta.getValue(),
                    txtTipo.getText(),
                    cbLocomocao.getValue(),
                    txtAnoDescoberta.getText()
            );

            service.salvar(dino);
            definirMensagemFeedback(mensagemSucesso, "#16a34a");
            limparFormulario();
            atualizarTabela();
        } catch (IllegalArgumentException e) {
            AlertUtil.exibirMensagem(Alert.AlertType.WARNING, "Validação", e.getMessage());
        } catch (Exception e) {
            AlertUtil.exibirMensagem(Alert.AlertType.ERROR, "Erro", "Falha na operação: " + e.getMessage());
        }
    }

    private void configurarColunas() {
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

        dadosFiltrados = new FilteredList<>(dadosTabela, p -> true);
        tblDinossauro.setItems(dadosFiltrados);
    }

    private void configurarComboBoxes() {
        cbDieta.setItems(FXCollections.observableArrayList("Carnívoro", "Herbívoro", "Onívoro", "Piscívoro"));
        cbLocomocao.setItems(FXCollections.observableArrayList("Bípede", "Quadrúpede", "Semibípede", "Facultativo"));
    }

    private void configurarFiltroPesquisa() {
        txtPesquisa.textProperty().addListener((obs, antigo, novo) -> {
            dadosFiltrados.setPredicate(dino -> DinossauroFilterUtil.atendeFiltro(dino, novo));
            atualizarContador();
        });
    }

    private void configurarSelecaoTabela() {
        tblDinossauro.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            carregarCampos();
        });
    }

    private void atualizarTabela() {
        try {
            dadosTabela.setAll(service.listarTodos());
            atualizarContador();
        } catch (Exception e) {
            AlertUtil.exibirMensagem(Alert.AlertType.ERROR, "Erro", "Erro ao carregar dados: " + e.getMessage());
        }
    }

    private void atualizarContador() {
        lblContador.setText("Total de espécies exibidas: " + dadosFiltrados.size());
    }

    private void definirMensagemFeedback(String msg, String corHex) {
        lblMensagem.setText(msg);
        lblMensagem.setStyle("-fx-text-fill: " + corHex + "; -fx-font-weight: bold;");
    }

    private void limparFormulario() {
        idSelecionado = null;
        TextField[] camposTexto = {
                txtEspecie, txtSignificadoNome, txtOrdem, txtEra,
                txtMyaInicio, txtMyaFim, txtHabitat, txtTipo, txtAnoDescoberta
        };
        ComboBox<?>[] combos = { cbDieta, cbLocomocao };

        DinossauroFormHelper.limparCampos(camposTexto, combos);
        lblMensagem.setText("");
        tblDinossauro.getSelectionModel().clearSelection();
    }
}