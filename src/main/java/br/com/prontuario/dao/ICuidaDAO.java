package br.com.prontuario.dao;

import br.com.prontuario.model.Cuida;
import br.com.prontuario.model.Enfermeiro;
import br.com.prontuario.model.Paciente;

import java.util.List;

public interface ICuidaDAO {
    void save(Cuida cuida);

    // Busca pacientes de um determinado enfermeiro
    List<Paciente> findPacienteByEnfermeiro(Enfermeiro enfermeiro);

    // Busca enfermeiros de um determinado paciente
    List<Enfermeiro> findEnfermeiroByPaciente(Paciente paciente);

    void delete(Cuida cuida);
}
