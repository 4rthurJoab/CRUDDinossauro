package com.template.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class DinossauroDAO {

    public void cadastrar(DinossauroDTO dino) {
        String sql = "INSERT INTO dinossauro (especie, significado_nome, ordem, era, mya_inicio, mya_fim, habitat, dieta, tipo, locomocao, ano_descoberta) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dino.getEspecie());
            stmt.setString(2, dino.getSignificadoNome());
            stmt.setString(3, dino.getOrdem());
            stmt.setString(4, dino.getEra());

            if (dino.getMyaInicio() != null) stmt.setDouble(5, dino.getMyaInicio());
            else stmt.setNull(5, Types.DOUBLE);

            if (dino.getMyaFim() != null) stmt.setDouble(6, dino.getMyaFim());
            else stmt.setNull(6, Types.DOUBLE);

            stmt.setString(7, dino.getHabitat());
            stmt.setString(8, dino.getDieta());
            stmt.setString(9, dino.getTipo());
            stmt.setString(10, dino.getLocomocao());

            if (dino.getAnoDescoberta() != null) stmt.setInt(11, dino.getAnoDescoberta());
            else stmt.setNull(11, Types.INTEGER);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar dinossauro: " + e.getMessage(), e);
        }
    }

    public void atualizar(DinossauroDTO dino) {
        String sql = "UPDATE dinossauro SET especie = ?, significado_nome = ?, ordem = ?, era = ?, mya_inicio = ?, "
                + "mya_fim = ?, habitat = ?, dieta = ?, tipo = ?, locomocao = ?, ano_descoberta = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dino.getEspecie());
            stmt.setString(2, dino.getSignificadoNome());
            stmt.setString(3, dino.getOrdem());
            stmt.setString(4, dino.getEra());

            if (dino.getMyaInicio() != null) stmt.setDouble(5, dino.getMyaInicio());
            else stmt.setNull(5, Types.DOUBLE);

            if (dino.getMyaFim() != null) stmt.setDouble(6, dino.getMyaFim());
            else stmt.setNull(6, Types.DOUBLE);

            stmt.setString(7, dino.getHabitat());
            stmt.setString(8, dino.getDieta());
            stmt.setString(9, dino.getTipo());
            stmt.setString(10, dino.getLocomocao());

            if (dino.getAnoDescoberta() != null) stmt.setInt(11, dino.getAnoDescoberta());
            else stmt.setNull(11, Types.INTEGER);

            stmt.setInt(12, dino.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar dinossauro: " + e.getMessage(), e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM dinossauro WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir dinossauro: " + e.getMessage(), e);
        }
    }

    public List<DinossauroDTO> listar() {
        List<DinossauroDTO> lista = new ArrayList<>();
        String sql = "SELECT id, especie, significado_nome, ordem, era, mya_inicio, mya_fim, habitat, dieta, tipo, locomocao, ano_descoberta FROM dinossauro ORDER BY id";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DinossauroDTO dino = new DinossauroDTO();
                dino.setId(rs.getInt("id"));
                dino.setEspecie(rs.getString("especie"));
                dino.setSignificadoNome(rs.getString("significado_nome"));
                dino.setOrdem(rs.getString("ordem"));
                dino.setEra(rs.getString("era"));

                double myaInicio = rs.getDouble("mya_inicio");
                dino.setMyaInicio(rs.wasNull() ? null : myaInicio);

                double myaFim = rs.getDouble("mya_fim");
                dino.setMyaFim(rs.wasNull() ? null : myaFim);

                dino.setHabitat(rs.getString("habitat"));
                dino.setDieta(rs.getString("dieta"));
                dino.setTipo(rs.getString("tipo"));
                dino.setLocomocao(rs.getString("locomocao"));

                int anoDescoberta = rs.getInt("ano_descoberta");
                dino.setAnoDescoberta(rs.wasNull() ? null : anoDescoberta);

                lista.add(dino);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar dinossauros: " + e.getMessage(), e);
        }
        return lista;
    }
}