package br.com.prontuario.dao;

import br.com.prontuario.model.Acompanhante;
import br.com.prontuario.model.Paciente;

import java.util.List;

public interface IAcompanhanteDAO {
    void save(Acompanhante acompanhante);

    // Busca acompanhantes de um paciente
    List<Acompanhante> findAllByPaciente(Paciente paciente);

    Acompanhante findByCpf(String cpf);

    void update(Acompanhante acompanhante);

    void delete(Acompanhante acompanhante);
}
