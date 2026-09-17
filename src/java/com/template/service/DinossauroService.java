package com.template.service;

import com.template.model.DinossauroDAO;
import com.template.model.DinossauroDTO;
import com.template.model.IDinossauroDAO;
import java.util.List;

public class DinossauroService implements IDinossauroService {

    private final IDinossauroDAO dao;

    public DinossauroService() {
        this(new DinossauroDAO());
    }

    public DinossauroService(IDinossauroDAO dao) {
        this.dao = dao;
    }

    @Override
    public void salvar(DinossauroDTO dino) {
        if (dino.getId() == null || dino.getId() == 0) {
            dao.cadastrar(dino);
        } else {
            dao.atualizar(dino);
        }
    }

    @Override
    public void excluir(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido para exclusão.");
        }
        dao.excluir(id);
    }

    @Override
    public List<DinossauroDTO> listarTodos() {
        return dao.listar();
    }
}