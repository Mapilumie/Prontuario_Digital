package br.com.prontuario.dao;

import br.com.prontuario.model.FatorRisco;
import br.com.prontuario.model.Paciente;

public interface IFatorRIscoDAO {
    void save(FatorRisco fatorRisco);

    FatorRisco findByPaciente(Paciente paciente);

    FatorRisco findById(Long id);

    void update(FatorRisco fatorRisco);

    void delete(FatorRisco fatorRisco);
}
