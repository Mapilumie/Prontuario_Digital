package br.com.prontuario.dao;

import br.com.prontuario.model.Paciente;

import java.util.List;

public interface IPacienteDAO {
    void save(Paciente paciente);

    List<Paciente> findAll();

    List<Paciente> findAllByNomeSobrenome(String nome);

    Paciente findByCpf(String cpf);

    void update(Paciente paciente);

    void delete(Paciente paciente);
}
