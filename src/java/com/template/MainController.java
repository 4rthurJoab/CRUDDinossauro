package com.template;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // ==========================================================================
    // MAPEAMENTO DOS COMPONENTES DO SCENE BUILDER (@FXML)
    // ==========================================================================

    // Campos de Entrada de Texto [cite: 27]
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

    // Botões de Ação [cite: 27]
    @FXML private Button btnSalvar;
    @FXML private Button btnAlterar;
    @FXML private Button btnExcluir;
    @FXML private Button btnLimpar;

    // Tabela e suas respectivas Colunas [cite: 27]
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
    // MÉTODO DE INICIALIZAÇÃO (Interface Initializable)
    // ==========================================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Interliga cada TableColumn com os atributos correspondentes do DinossauroDTO [cite: 31, 32]
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

        // Carrega os dados iniciais do banco de dados para a tabela [cite: 31]
        carregarDinossauros();
    }

    // ==========================================================================
    // MÉTODOS DE CONTROLE E REGRAS DE NEGÓCIO
    // ==========================================================================

    /**
     * Busca a lista atualizada de dinossauros no banco e preenche a TableView[cite: 28].
     */
    @FXML
    private void carregarDinossauros() {
        DinossauroDAO dao = new DinossauroDAO();
        ArrayList<DinossauroDTO> lista = dao.listarDinossauros();

        // Converte a lista comum para ObservableList para que a interface atualize automaticamente [cite: 29, 30]
        tblDinossauro.setItems(FXCollections.observableArrayList(lista));
    }

    /**
     * Ação do botão "Salvar" para inserir um novo registro[cite: 27, 33].
     */
    @FXML
    void btnSalvarAction(ActionEvent event) {
        DinossauroDTO dino = obterDadosDoFormulario();

        DinossauroDAO dao = new DinossauroDAO();
        dao.inserirDinossauro(dino);

        // Atualiza o componente visual e limpa a tela para o próximo uso [cite: 33]
        carregarDinossauros();
        limparCampos();
    }

    /**
     * Ação do botão "Alterar" para atualizar os dados de um dinossauro existente[cite: 34].
     */
    @FXML
    void btnAlterarAction(ActionEvent event) {
        if (idSelecionado != 0) {
            DinossauroDTO dino = obterDadosDoFormulario();
            dino.setId(idSelecionado); // Define o ID que será usado na cláusula WHERE do SQL

            DinossauroDAO dao = new DinossauroDAO();
            dao.atualizarDinossauro(dino);

            carregarDinossauros();
            limparCampos();
        }
    }

    /**
     * Ação do botão "Excluir" para apagar o registro selecionado do banco[cite: 34].
     */
    @FXML
    void btnExcluirAction(ActionEvent event) {
        if (idSelecionado != 0) {
            DinossauroDAO dao = new DinossauroDAO();
            dao.excluirDinossauro(idSelecionado);

            carregarDinossauros();
            limparCampos();
        }
    }

    /**
     * Ação do botão "Limpar" para resetar o formulário[cite: 34].
     */
    @FXML
    void btnLimparAction(ActionEvent event) {
        limparCampos();
    }

    /**
     * Captura o evento de clique na linha da tabela e preenche os campos do formulário[cite: 35].
     * Vinculado ao evento "On Mouse Clicked" da TableView no Scene Builder.
     */
    @FXML
    void carregarCampos(MouseEvent event) {
        // Pega o item do modelo de seleção da linha clicada pelo usuário [cite: 35, 36, 37]
        DinossauroDTO dino = tblDinossauro.getSelectionModel().getSelectedItem();

        if (dino != null) {
            idSelecionado = dino.getId(); // Guarda o ID para futuras alterações ou exclusões

            // Popula os TextFields com os dados textuais do objeto [cite: 35]
            txtEspecie.setText(dino.getEspecie());
            txtSignificadoNome.setText(dino.getSignificadoNome());
            txtOrdem.setText(dino.getOrdem());
            txtEra.setText(dino.getEra());
            txtHabitat.setText(dino.getHabitat());
            txtDieta.setText(dino.getDieta());
            txtTipo.setText(dino.getTipo());
            txtLocomocao.setText(dino.getLocomocao());

            // Converte os valores numéricos primitivos para String para exibição segura nos campos
            txtMyaInicio.setText(String.valueOf(dino.getMyaInicio()));
            txtMyaFim.setText(String.valueOf(dino.getMyaFim()));
            txtAnoDescoberta.setText(String.valueOf(dino.getAnoDescoberta()));
        }
    }

    // ==========================================================================
    // MÉTODOS AUXILIARES PRIVADOS
    // ==========================================================================

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

        // Tratamento seguro para conversão de dados numéricos digitados (evita travamento por NumberFormatException)
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

        // Remove a seleção visual ativa da linha da tabela
        tblDinossauro.getSelectionModel().clearSelection();
    }
}