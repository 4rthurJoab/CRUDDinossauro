package com.template.service;

import com.template.model.DinossauroDAO;
import com.template.model.DinossauroDTO;
import java.util.List;

public class DinossauroService {

    private final DinossauroDAO dao;

    public DinossauroService() {
        this.dao = new DinossauroDAO();
    }

    public DinossauroService(DinossauroDAO dao) {
        this.dao = dao;
    }

    public void salvar(DinossauroDTO dino) {
        if (dino.getId() == null || dino.getId() == 0) {
            dao.cadastrar(dino);
        } else {
            dao.atualizar(dino);
        }
    }

    public void excluir(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido para exclusão.");
        }
        dao.excluir(id);
    }

    public List<DinossauroDTO> listarTodos() {
        return dao.listar();
    }
}