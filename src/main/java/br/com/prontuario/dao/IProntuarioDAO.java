package br.com.prontuario.dao;

import br.com.prontuario.model.Prontuario;

import java.util.List;

public interface IProntuarioDAO {
    void save(Prontuario prontuario);

    List<Prontuario> findAll();

    Prontuario findById(Long id);

    void update(Prontuario prontuario);

    void delete(Prontuario prontuario);
}
