package br.com.prontuario.dao;

import br.com.prontuario.model.Enfermeiro;

import java.util.List;

public interface IEnfermeiroDAO {
    void save(Enfermeiro enfermeiro);

    List<Enfermeiro> findAll();

    Enfermeiro findByCoren(String coren);

    void update(Enfermeiro enfermeiro);

    void delete(Enfermeiro enfermeiro);

}
