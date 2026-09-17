package com.template.service;

import com.template.model.DinossauroDTO;
import java.util.List;

public interface IDinossauroService {

    void salvar(DinossauroDTO dino);

    void excluir(int id);

    List<DinossauroDTO> listarTodos();
}
