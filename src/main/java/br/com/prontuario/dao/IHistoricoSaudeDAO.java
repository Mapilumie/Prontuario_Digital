package br.com.prontuario.dao;

import br.com.prontuario.model.HistoricoSaude;
import br.com.prontuario.model.Paciente;

public interface IHistoricoSaudeDAO {
    void save(HistoricoSaude historicoSaude);

    HistoricoSaude findByPaciente(Paciente paciente);

    HistoricoSaude findById(Long id);

    void update(HistoricoSaude historicoSaude);

    void delete(HistoricoSaude historicoSaude);
}
