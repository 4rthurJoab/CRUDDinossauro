package com.template;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DinossauroDAO {

    private static final Logger logger = Logger.getLogger(DinossauroDAO.class.getName());
    private ArrayList<DinossauroDTO> listaDinossauros = new ArrayList<>();

    public ArrayList<DinossauroDTO> listarDinossauros() {
        String sql = "SELECT * FROM dinossauros";
        listaDinossauros.clear();

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DinossauroDTO dino = new DinossauroDTO();
                dino.setId(rs.getInt("id"));
                dino.setEspecie(rs.getString("especie"));
                dino.setSignificadoNome(rs.getString("significado_nome"));
                dino.setOrdem(rs.getString("ordem"));
                dino.setEra(rs.getString("era"));
                dino.setMyaInicio(rs.getDouble("mya_inicio"));
                dino.setMyaFim(rs.getDouble("mya_fim"));
                dino.setHabitat(rs.getString("habitat"));
                dino.setDieta(rs.getString("dieta"));
                dino.setTipo(rs.getString("tipo"));
                dino.setLocomocao(rs.getString("locomocao"));
                dino.setAnoDescoberta(rs.getInt("ano_descoberta"));

                listaDinossauros.add(dino);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao listar dinossauros", ex);
        }
        return listaDinossauros;
    }

    public void inserirDinossauro(DinossauroDTO dino) {
        String sql = "INSERT INTO dinossauros (especie, significado_nome, ordem, era, mya_inicio, mya_fim, habitat, dieta, tipo, locomocao, ano_descoberta) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, dino.getEspecie());
            ps.setString(2, dino.getSignificadoNome());
            ps.setString(3, dino.getOrdem());
            ps.setString(4, dino.getEra());
            ps.setDouble(5, dino.getMyaInicio());
            ps.setDouble(6, dino.getMyaFim());
            ps.setString(7, dino.getHabitat());
            ps.setString(8, dino.getDieta());
            ps.setString(9, dino.getTipo());
            ps.setString(10, dino.getLocomocao());
            ps.setInt(11, dino.getAnoDescoberta());

            ps.execute();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao inserir dinossauro", ex);
        }
    }

    public void atualizarDinossauro(DinossauroDTO dino) {
        String sql = "UPDATE dinossauros SET especie = ?, significado_nome = ?, ordem = ?, era = ?, mya_inicio = ?, mya_fim = ?, habitat = ?, dieta = ?, tipo = ?, locomocao = ?, ano_descoberta = ? WHERE id = ?";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, dino.getEspecie());
            ps.setString(2, dino.getSignificadoNome());
            ps.setString(3, dino.getOrdem());
            ps.setString(4, dino.getEra());
            ps.setDouble(5, dino.getMyaInicio());
            ps.setDouble(6, dino.getMyaFim());
            ps.setString(7, dino.getHabitat());
            ps.setString(8, dino.getDieta());
            ps.setString(9, dino.getTipo());
            ps.setString(10, dino.getLocomocao());
            ps.setInt(11, dino.getAnoDescoberta());
            ps.setInt(12, dino.getId());

            ps.executeUpdate();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao atualizar dinossauro", ex);
        }
    }

    public void excluirDinossauro(int id) {
        String sql = "DELETE FROM dinossauros WHERE id = ?";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao excluir dinossauro", ex);
        }
    }
}