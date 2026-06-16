package com.template;

import javafx.collections.FXCollections;
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

    // ==========================================================================
    // MAPEAMENTO DOS COMPONENTES DO SCENE BUILDER (@FXML)
    // ==========================================================================

    // Campos de Entrada de Texto
    @FXML private TextField txtEspecie;
    @FXML private TextField txtSignificadoNome;
    @FXML private TextField txtOrdem;
    @FXML private TextField txtEra;
    @FXML private TextField txtMyaInicio;
    @FXML private TextField txtMyaFim;
    @FXML private TextField txtHabitat;
    @FXML private TextField txtDieta;
    @FXML private TextField txtTipo;
    @FXML private TextField txtLocomocao;
    @FXML private TextField txtAnoDescoberta;

    // Botões de Ação
    @FXML private Button btnSalvar;
    @FXML private Button btnAlterar;
    @FXML private Button btnExcluir;
    @FXML private Button btnLimpar;

    // Labels de UI/UX adicionados
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

    // Variável de controle interna para armazenar o ID do registro selecionado
    private int idSelecionado = 0;

    // ==========================================================================
    // MÉTODO DE INICIALIZAÇÃO
    // ==========================================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Interliga cada TableColumn com os atributos correspondentes do DinossauroDTO
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

        // UX: Bloqueia a digitação de letras em campos numéricos
        configurarValidacaoNumerica();

        // UX: Configura o estado inicial dos botões
        alternarEstadoBotoes(false);

        // Carrega os dados iniciais do banco de dados para a tabela e atualiza o contador
        carregarDinossauros();
    }

    // ==========================================================================
    // MÉTODOS DE CONTROLE E REGRAS DE NEGÓCIO
    // ==========================================================================

    /**
     * Busca a lista atualizada de dinossauros no banco e preenche a TableView.
     */
    @FXML
    private void carregarDinossauros() {
        DinossauroDAO dao = new DinossauroDAO();
        ArrayList<DinossauroDTO> lista = dao.listarDinossauros();

        tblDinossauro.setItems(FXCollections.observableArrayList(lista));

        // UI: Atualiza o contador de dinossauros cadastrados
        lblContador.setText("Total de espécies catalogadas: " + lista.size());
    }

    /**
     * Ação do botão "Salvar" para inserir um novo registro.
     */
    @FXML
    void btnSalvarAction(ActionEvent event) {
        if (txtEspecie.getText().trim().isEmpty()) {
            exibirMensagem("O campo 'Espécie' é obrigatório para o cadastro!", false);
            return;
        }

        DinossauroDTO dino = obterDadosDoFormulario();
        DinossauroDAO dao = new DinossauroDAO();
        dao.inserirDinossauro(dino);

        exibirMensagem("Dinossauro '" + dino.getEspecie() + "' catalogado com sucesso!", true);
        carregarDinossauros();
        limparCampos();
    }

    /**
     * Ação do botão "Alterar" para atualizar os dados de um dinossauro existente.
     */
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

    /**
     * Ação do botão "Excluir" com caixa de diálogo de confirmação (UX).
     */
    @FXML
    void btnExcluirAction(ActionEvent event) {
        if (idSelecionado != 0) {
            // UX: Solicitar confirmação antes de excluir registros
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

    /**
     * Ação do botão "Limpar" para resetar o formulário.
     */
    @FXML
    void btnLimparAction(ActionEvent event) {
        limparCampos();
        lblMensagem.setText("");
    }

    /**
     * Captura o evento de clique na linha da tabela e preenche os campos do formulário.
     */
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
            txtDieta.setText(dino.getDieta());
            txtTipo.setText(dino.getTipo());
            txtLocomocao.setText(dino.getLocomocao());

            txtMyaInicio.setText(String.valueOf(dino.getMyaInicio()));
            txtMyaFim.setText(String.valueOf(dino.getMyaFim()));
            txtAnoDescoberta.setText(String.valueOf(dino.getAnoDescoberta()));

            // UX: Ativa os botões de Edição/Exclusão pois um item válido foi selecionado
            alternarEstadoBotoes(true);
        }
    }

    // ==========================================================================
    // MÉTODOS AUXILIARES PRIVADOS (UI/UX)
    // ==========================================================================

    /**
     * Controla quais ações estão disponíveis dependendo do contexto da aplicação.
     */
    private void toggleBotaoEstado(boolean itemSelecionado) {
        // Se houver item selecionado, desativa o "Salvar" e ativa "Alterar"/"Excluir"
        btnSalvar.setDisable(itemSelecionado);
        btnAlterar.setDisable(!itemSelecionado);
        btnExcluir.setDisable(!itemSelecionado);
    }

    // Alias para manter legibilidade com a assinatura antiga do esqueleto
    private void alternarEstadoBotoes(boolean itemSelecionado) {
        toggleBotaoEstado(itemSelecionado);
    }

    /**
     * UX: Impede que o usuário digite texto onde apenas números inteiros ou decimais são válidos.
     */
    private void configurarValidacaoNumerica() {
        // Apenas dígitos numéricos positivos para o ano
        txtAnoDescoberta.textProperty().addListener((obs, antigo, novo) -> {
            if (!novo.matches("\\d*")) {
                txtAnoDescoberta.setText(novo.replaceAll("[^\\d]", ""));
            }
        });

        // Valores decimais estruturados para os campos de Milhões de Anos atrás (mya)
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

    /**
     * UI: Exibe mensagens personalizadas em cores dependendo da resposta da operação.
     */
    private void exibirMensagem(String texto, boolean ehSucesso) {
        lblMensagem.setText(texto);
        if (ehSucesso) {
            lblMensagem.setStyle("-fx-text-fill: #10b981; -fx-font-weight: bold;"); // Emerald Green
        } else {
            lblMensagem.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;"); // Crimson Red
        }
    }

    /**
     * Captura os textos digitados na tela e monta um objeto DinossauroDTO pronto para o banco.
     */
    private DinossauroDTO obterDadosDoFormulario() {
        DinossauroDTO dino = new DinossauroDTO();

        dino.setEspecie(txtEspecie.getText());
        dino.setSignificadoNome(txtSignificadoNome.getText());
        dino.setOrdem(txtOrdem.getText());
        dino.setEra(txtEra.getText());
        dino.setHabitat(txtHabitat.getText());
        dino.setDieta(txtDieta.getText());
        dino.setTipo(txtTipo.getText());
        dino.setLocomocao(txtLocomocao.getText());

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

    /**
     * Reseta todos os campos de texto do formulário e limpa estados de seleção.
     */
    private void limparCampos() {
        idSelecionado = 0;

        txtEspecie.clear();
        txtSignificadoNome.clear();
        txtOrdem.clear();
        txtEra.clear();
        txtMyaInicio.clear();
        txtMyaFim.clear();
        txtHabitat.clear();
        txtDieta.clear();
        txtTipo.clear();
        txtLocomocao.clear();
        txtAnoDescoberta.clear();

        tblDinossauro.getSelectionModel().clearSelection();

        // Retorna os botões para o estado padrão seguro (Inserção habilitada)
        alternarEstadoBotoes(false);

        // UX: Posiciona automaticamente o foco inicial de volta ao primeiro campo
        txtEspecie.requestFocus();
    }
}