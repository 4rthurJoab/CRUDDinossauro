package com.template.model;

import java.util.List;

public interface IDinossauroDAO {

    void cadastrar(DinossauroDTO dino);

    void atualizar(DinossauroDTO dino);

    void excluir(int id);

    List<DinossauroDTO> listar();
}
