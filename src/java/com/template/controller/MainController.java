package com.template.controller;

import com.template.model.DinossauroDTO;
import com.template.service.DinossauroService;
import com.template.service.IDinossauroService;
import com.template.util.AlertUtil;
import com.template.util.DinossauroFormHelper;
import com.template.util.DinossauroTableHelper;
import com.template.validator.DinossauroValidator;
import com.template.validator.IDinossauroValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    @FXML private Button btnAlternarFiltros;

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

    private final IDinossauroService service;
    private final IDinossauroValidator validator;
    private final ObservableList<DinossauroDTO> dadosTabela = FXCollections.observableArrayList();
    private FilteredList<DinossauroDTO> dadosFiltrados;
    private Integer idSelecionado = null;

    // Injeção de dependência via construtor (DIP - Princípio da Inversão de Dependência)
    public MainController(IDinossauroService service, IDinossauroValidator validator) {
        this.service = service;
        this.validator = validator;
    }

    public MainController() {
        this(new DinossauroService(), new DinossauroValidator());
    }

    @FXML
    public void initialize() {
        dadosFiltrados = new FilteredList<>(dadosTabela, p -> true);

        DinossauroTableHelper.configurarColunas(
                tblDinossauro, colId, colEspecie, colSignificadoNome, colOrdem, colEra,
                colMyaInicio, colMyaFim, colHabitat, colDieta, colTipo, colLocomocao,
                colAnoDescoberta, dadosFiltrados
        );
        DinossauroFormHelper.configurarComboBoxes(cbDieta, cbLocomocao);
        DinossauroTableHelper.configurarFiltroPesquisa(
                txtPesquisa, dadosFiltrados,
                () -> DinossauroTableHelper.atualizarContador(lblContador, dadosFiltrados.size())
        );

        tblDinossauro.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> carregarCampos());

        DinossauroTableHelper.atualizarTabela(
                dadosTabela, service,
                () -> DinossauroTableHelper.atualizarContador(lblContador, dadosFiltrados.size())
        );
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
                idSelecionado = null;
                DinossauroFormHelper.limparFormulario(obterCamposTexto(), obterCombos(), lblMensagem, tblDinossauro);
                AlertUtil.definirMensagemFeedback(lblMensagem, "Registro excluído com sucesso!", "#16a34a");
                DinossauroTableHelper.atualizarTabela(dadosTabela, service, () -> DinossauroTableHelper.atualizarContador(lblContador, dadosFiltrados.size()));
            } catch (Exception e) {
                AlertUtil.exibirMensagem(Alert.AlertType.ERROR, "Erro", "Falha ao excluir: " + e.getMessage());
            }
        }
    }

    @FXML
    private void btnLimparAction() {
        idSelecionado = null;
        DinossauroFormHelper.limparFormulario(obterCamposTexto(), obterCombos(), lblMensagem, tblDinossauro);
    }

    @FXML
    private void carregarCampos() {
        DinossauroDTO sel = tblDinossauro.getSelectionModel().getSelectedItem();
        if (sel == null) return;

        idSelecionado = sel.getId();
        DinossauroFormHelper.carregarNoFormulario(
                sel, txtEspecie, txtSignificadoNome, txtOrdem, txtEra,
                txtMyaInicio, txtMyaFim, txtHabitat, cbDieta, txtTipo,
                cbLocomocao, txtAnoDescoberta
        );
    }

    @FXML
    private void btnAlternarFiltrosAction() {
        boolean visivel = !txtPesquisa.isVisible();
        txtPesquisa.setVisible(visivel);
        txtPesquisa.setManaged(visivel);
    }
    @FXML
    private void executarGravacao(String mensagemSucesso) {
        try {
            DinossauroDTO dino = validator.validarEConstruir(
                    idSelecionado,
                    txtEspecie.getText(), txtSignificadoNome.getText(), txtOrdem.getText(), txtEra.getText(),
                    txtMyaInicio.getText(), txtMyaFim.getText(), txtHabitat.getText(),
                    cbDieta.getValue(), txtTipo.getText(), cbLocomocao.getValue(), txtAnoDescoberta.getText()
            );

            service.salvar(dino);
            idSelecionado = null;
            DinossauroFormHelper.limparFormulario(obterCamposTexto(), obterCombos(), lblMensagem, tblDinossauro);
            AlertUtil.definirMensagemFeedback(lblMensagem, mensagemSucesso, "#16a34a");
            DinossauroTableHelper.atualizarTabela(dadosTabela, service, () -> DinossauroTableHelper.atualizarContador(lblContador, dadosFiltrados.size()));
        } catch (IllegalArgumentException e) {
            AlertUtil.exibirMensagem(Alert.AlertType.WARNING, "Validação", e.getMessage());
        } catch (Exception e) {
            AlertUtil.exibirMensagem(Alert.AlertType.ERROR, "Erro", "Falha na operação: " + e.getMessage());
        }
    }
    @FXML
    private TextField[] obterCamposTexto() {
        return new TextField[]{ txtEspecie, txtSignificadoNome, txtOrdem, txtEra, txtMyaInicio, txtMyaFim, txtHabitat, txtTipo, txtAnoDescoberta };
    }
    @FXML
    private ComboBox<?>[] obterCombos() {
        return new ComboBox<?>[]{ cbDieta, cbLocomocao };
    }
}