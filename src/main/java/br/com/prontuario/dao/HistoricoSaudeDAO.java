package br.com.prontuario.dao;

import br.com.prontuario.db.ConnectionFactory;
import br.com.prontuario.model.HistoricoSaude;
import br.com.prontuario.model.Paciente;

import java.sql.*;

public class HistoricoSaudeDAO {
    public void save(HistoricoSaude historicoSaude) {
        String sql = "INSERT INTO historicoSaude (tabagismo, neoplasia, doencaAutoimune, doencaRespiratoria, doencaCardiovascular, diabetes, doencaRenal, doencasInfectocontagiosas, dislipidemia, etilismo, hipertensao, transfusaoSanguinea, viroseInfancia, CPF_Paciente) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = null;
        Connection conexao = null;
        ResultSet rs = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setBoolean(1, historicoSaude.isTabagismo());
            pst.setBoolean(2, historicoSaude.isNeoplasia());
            pst.setBoolean(3, historicoSaude.isDoencaAutoimune());
            pst.setBoolean(4, historicoSaude.isDoencaRespiratoria());
            pst.setBoolean(5, historicoSaude.isDoencaCardiovascular());
            pst.setBoolean(6, historicoSaude.isDiabetes());
            pst.setBoolean(7, historicoSaude.isDoencaRenal());
            pst.setBoolean(8, historicoSaude.isDoencasInfectocontagiosas());
            pst.setBoolean(9, historicoSaude.isDislipidemia());
            pst.setBoolean(10, historicoSaude.isEtilismo());
            pst.setBoolean(11, historicoSaude.isHipertensao());
            pst.setBoolean(12, historicoSaude.isTransfusaoSanguinea());
            pst.setBoolean(13, historicoSaude.isViroseInfancia());
            if (historicoSaude.getPaciente() != null) {
                pst.setString(14, historicoSaude.getPaciente().getCpf());
            } else {
                throw new SQLException("Erro ao salvar historicoSaude: Nenhum paciente vinculado");
            }
            pst.execute();

            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                historicoSaude.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar historicoSaude: " + e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar ResultSet: " + e.getMessage());
                }
            }

            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
                }
            }

            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar Connection: " + e.getMessage());
                }
            }
        }
    }

    public HistoricoSaude findByPaciente(Paciente paciente) {
        String sql = "SELECT * FROM historicoSaude WHERE CPF_Paciente=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        ResultSet rs = null;
        HistoricoSaude historicoSaude = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, paciente.getCpf());
            rs = pst.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    historicoSaude = new HistoricoSaude();
                    historicoSaude.setId(rs.getLong("Id_HistoricoSaude"));
                    historicoSaude.setTabagismo(rs.getBoolean("tabagismo"));
                    historicoSaude.setNeoplasia(rs.getBoolean("neoplasia"));
                    historicoSaude.setDoencaAutoimune(rs.getBoolean("doencaAutoimune"));
                    historicoSaude.setDoencaRespiratoria(rs.getBoolean("doencaRespiratoria"));
                    historicoSaude.setDoencaCardiovascular(rs.getBoolean("doencaCardiovascular"));
                    historicoSaude.setDiabetes(rs.getBoolean("diabetes"));
                    historicoSaude.setDoencaRenal(rs.getBoolean("doencaRenal"));
                    historicoSaude.setDoencasInfectocontagiosas(rs.getBoolean("doencasInfectocontagiosas"));
                    historicoSaude.setDislipidemia(rs.getBoolean("dislipidemia"));
                    historicoSaude.setEtilismo(rs.getBoolean("etilismo"));
                    historicoSaude.setHipertensao(rs.getBoolean("hipertensao"));
                    historicoSaude.setTransfusaoSanguinea(rs.getBoolean("transfusaoSanguinea"));
                    historicoSaude.setViroseInfancia(rs.getBoolean("viroseInfancia"));

                    historicoSaude.setPaciente(paciente);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar histórico de saúde do paciente \"" + paciente.getCpf() + "\": " + e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar ResultSet: " + e.getMessage());
                }
            }

            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
                }
            }

            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar Connection: " + e.getMessage());
                }
            }
        }
        return historicoSaude;
    }

    public HistoricoSaude findById(Long id) {
        String sql = "SELECT * FROM historicoSaude WHERE Id_HistoricoSaude=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        ResultSet rs = null;
        HistoricoSaude historicoSaude = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    historicoSaude = new HistoricoSaude();
                    historicoSaude.setId(rs.getLong("Id_HistoricoSaude"));
                    historicoSaude.setTabagismo(rs.getBoolean("tabagismo"));
                    historicoSaude.setNeoplasia(rs.getBoolean("neoplasia"));
                    historicoSaude.setDoencaAutoimune(rs.getBoolean("doencaAutoimune"));
                    historicoSaude.setDoencaRespiratoria(rs.getBoolean("doencaRespiratoria"));
                    historicoSaude.setDoencaCardiovascular(rs.getBoolean("doencaCardiovascular"));
                    historicoSaude.setDiabetes(rs.getBoolean("diabetes"));
                    historicoSaude.setDoencaRenal(rs.getBoolean("doencaRenal"));
                    historicoSaude.setDoencasInfectocontagiosas(rs.getBoolean("doencasInfectocontagiosas"));
                    historicoSaude.setDislipidemia(rs.getBoolean("dislipidemia"));
                    historicoSaude.setEtilismo(rs.getBoolean("etilismo"));
                    historicoSaude.setHipertensao(rs.getBoolean("hipertensao"));
                    historicoSaude.setTransfusaoSanguinea(rs.getBoolean("transfusaoSanguinea"));
                    historicoSaude.setViroseInfancia(rs.getBoolean("viroseInfancia"));

                    String fkPaciente = rs.getString("CPF_Paciente");
                    if (fkPaciente != null) {
                        Paciente paciente = new Paciente();
                        paciente.setCpf(fkPaciente);
                        historicoSaude.setPaciente(paciente);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar histórico de saúde  por id {" + id + "}: " + e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar ResultSet: " + e.getMessage());
                }
            }

            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
                }
            }

            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar Connection: " + e.getMessage());
                }
            }
        }
        return historicoSaude;
    }

    public void update(HistoricoSaude historicoSaude) {
        String sql = "UPDATE historicoSaude SET tabagismo=?, neoplasia=?, doencaAutoimune=?, doencaRespiratoria=?, doencaCardiovascular=?, diabetes=?, doencaRenal=?, doencasInfectocontagiosas=?, dislipidemia=?, etilismo=?, hipertensao=?, transfusaoSanguinea=?, viroseInfancia=? WHERE Id_HistoricoSaude=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);

            pst.setBoolean(1, historicoSaude.isTabagismo());
            pst.setBoolean(2, historicoSaude.isNeoplasia());
            pst.setBoolean(3, historicoSaude.isDoencaAutoimune());
            pst.setBoolean(4, historicoSaude.isDoencaRespiratoria());
            pst.setBoolean(5, historicoSaude.isDoencaCardiovascular());
            pst.setBoolean(6, historicoSaude.isDiabetes());
            pst.setBoolean(7, historicoSaude.isDoencaRenal());
            pst.setBoolean(8, historicoSaude.isDoencasInfectocontagiosas());
            pst.setBoolean(9, historicoSaude.isDislipidemia());
            pst.setBoolean(10, historicoSaude.isEtilismo());
            pst.setBoolean(11, historicoSaude.isHipertensao());
            pst.setBoolean(12, historicoSaude.isTransfusaoSanguinea());
            pst.setBoolean(13, historicoSaude.isViroseInfancia());

            pst.setLong(14, historicoSaude.getId());

            pst.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar historicoSaude: " + e.getMessage());
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
                }
            }

            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar Connection: " + e.getMessage());
                }
            }
        }
    }


    public void delete(HistoricoSaude historicoSaude) {
        String sql = "DELETE FROM historicoSaude WHERE Id_HistoricoSaude=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setLong(1, historicoSaude.getId());
            pst.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar historicoSaude: ");
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
                }
            }

            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar Connection: " + e.getMessage());
                }
            }
        }
    }
}
