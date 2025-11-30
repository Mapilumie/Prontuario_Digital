package br.com.prontuario.dao;

import br.com.prontuario.db.ConnectionFactory;
import br.com.prontuario.model.FatorRisco;
import br.com.prontuario.model.Paciente;

import java.sql.*;

public class FatorRiscoDAO implements IFatorRIscoDAO {
    public void save(FatorRisco fatorRisco) {
        String sql = "INSERT INTO fatorRisco (alteracaoConsciencia, deficitMobilidade, deficitNutricional, peleUmida, cisalhamento, limitacaoMobilidade, criancaIdosoGestante, turgorPele, imunoDepressao, fragilidadeCapilar, quimioterapia, medHiperosmolar, convulsoes, delirium, visaoAudicao, hipotensao, usoAlcoolDrogas, foraDeRisco, CPF_Paciente) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = null;
        Connection conexao = null;
        ResultSet rs = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Lesão de pele
            pst.setBoolean(1, fatorRisco.isAlteracaoConsciencia());
            pst.setBoolean(2, fatorRisco.isDeficitMobilidade());
            pst.setBoolean(3, fatorRisco.isDeficitNutricional());
            pst.setBoolean(4, fatorRisco.isPeleUmida());
            pst.setBoolean(5, fatorRisco.isCisalhamento());
            pst.setBoolean(6, fatorRisco.isLimitacaoMobilidade());

            // Flebite
            pst.setBoolean(7, fatorRisco.isCriancaIdosoGestante());
            pst.setBoolean(8, fatorRisco.isTurgorPele());
            pst.setBoolean(9, fatorRisco.isImunoDepressao());
            pst.setBoolean(10, fatorRisco.isFragilidadeCapilar());
            pst.setBoolean(11, fatorRisco.isQuimioterapia());
            pst.setBoolean(12, fatorRisco.isMedHiperosmolar());

            // Queda
            pst.setBoolean(13, fatorRisco.isConvulsoes());
            pst.setBoolean(14, fatorRisco.isDelirium());
            pst.setBoolean(15, fatorRisco.isVisaoAudicao());
            pst.setBoolean(16, fatorRisco.isHipotensao());
            pst.setBoolean(17, fatorRisco.isUsoAlcoolDrogas());

            // Não há riscos
            pst.setBoolean(18, fatorRisco.isForaDeRisco());

            if (fatorRisco.getPaciente() != null) {
                pst.setString(19, fatorRisco.getPaciente().getCpf());
            } else {
                throw new SQLException("Erro ao salvar fatorRisco: Nenhum paciente vinculado");
            }
            pst.execute();

            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                fatorRisco.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar fatorRisco: " + e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar ResultSet: ");
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

    public FatorRisco findByPaciente(Paciente paciente) {
        String sql = "SELECT * FROM fatorRisco WHERE CPF_Paciente=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        ResultSet rs = null;
        FatorRisco fatorRisco = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, paciente.getCpf());
            rs = pst.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    fatorRisco = new FatorRisco();
                    fatorRisco.setId(rs.getLong("Id_FatorRisco"));

                    // Lesão
                    fatorRisco.setAlteracaoConsciencia(rs.getBoolean("alteracaoConsciencia"));
                    fatorRisco.setDeficitMobilidade(rs.getBoolean("deficitMobilidade"));
                    fatorRisco.setDeficitNutricional(rs.getBoolean("deficitNutricional"));
                    fatorRisco.setPeleUmida(rs.getBoolean("peleUmida"));
                    fatorRisco.setCisalhamento(rs.getBoolean("cisalhamento"));
                    fatorRisco.setLimitacaoMobilidade(rs.getBoolean("limitacaoMobilidade"));

                    // Flebite
                    fatorRisco.setCriancaIdosoGestante(rs.getBoolean("criancaIdosoGestante"));
                    fatorRisco.setTurgorPele(rs.getBoolean("turgorPele"));
                    fatorRisco.setImunoDepressao(rs.getBoolean("imunoDepressao"));
                    fatorRisco.setFragilidadeCapilar(rs.getBoolean("fragilidadeCapilar"));
                    fatorRisco.setQuimioterapia(rs.getBoolean("quimioterapia"));
                    fatorRisco.setMedHiperosmolar(rs.getBoolean("medHiperosmolar"));

                    // Queda
                    fatorRisco.setConvulsoes(rs.getBoolean("convulsoes"));
                    fatorRisco.setDelirium(rs.getBoolean("delirium"));
                    fatorRisco.setVisaoAudicao(rs.getBoolean("visaoAudicao"));
                    fatorRisco.setHipotensao(rs.getBoolean("hipotensao"));
                    fatorRisco.setUsoAlcoolDrogas(rs.getBoolean("usoAlcoolDrogas"));

                    // Não há riscos
                    fatorRisco.setForaDeRisco(rs.getBoolean("foraDeRisco"));

                    fatorRisco.setPaciente(paciente);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar fatores de risco do paciente \"" + paciente.getCpf() + "\": " + e.getMessage());
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
        return fatorRisco;
    }

    public FatorRisco findById(Long id) {
        String sql = "SELECT * FROM fatorRisco WHERE Id_FatorRisco=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        ResultSet rs = null;
        FatorRisco fatorRisco = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    fatorRisco = new FatorRisco();
                    fatorRisco.setId(rs.getLong("Id_FatorRisco"));

                    // Lesão
                    fatorRisco.setAlteracaoConsciencia(rs.getBoolean("alteracaoConsciencia"));
                    fatorRisco.setDeficitMobilidade(rs.getBoolean("deficitMobilidade"));
                    fatorRisco.setDeficitNutricional(rs.getBoolean("deficitNutricional"));
                    fatorRisco.setPeleUmida(rs.getBoolean("peleUmida"));
                    fatorRisco.setCisalhamento(rs.getBoolean("cisalhamento"));
                    fatorRisco.setLimitacaoMobilidade(rs.getBoolean("limitacaoMobilidade"));

                    // Flebite
                    fatorRisco.setCriancaIdosoGestante(rs.getBoolean("criancaIdosoGestante"));
                    fatorRisco.setTurgorPele(rs.getBoolean("turgorPele"));
                    fatorRisco.setImunoDepressao(rs.getBoolean("imunoDepressao"));
                    fatorRisco.setFragilidadeCapilar(rs.getBoolean("fragilidadeCapilar"));
                    fatorRisco.setQuimioterapia(rs.getBoolean("quimioterapia"));
                    fatorRisco.setMedHiperosmolar(rs.getBoolean("medHiperosmolar"));

                    // Queda
                    fatorRisco.setConvulsoes(rs.getBoolean("convulsoes"));
                    fatorRisco.setDelirium(rs.getBoolean("delirium"));
                    fatorRisco.setVisaoAudicao(rs.getBoolean("visaoAudicao"));
                    fatorRisco.setHipotensao(rs.getBoolean("hipotensao"));
                    fatorRisco.setUsoAlcoolDrogas(rs.getBoolean("usoAlcoolDrogas"));

                    // Não há riscos
                    fatorRisco.setForaDeRisco(rs.getBoolean("foraDeRisco"));

                    String fkPaciente = rs.getString("CPF_Paciente");
                    if (fkPaciente != null) {
                        Paciente paciente = new Paciente();
                        paciente.setCpf(fkPaciente);
                        fatorRisco.setPaciente(paciente);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar fatorRisco por id {" + id + "}: " + e.getMessage());
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
        return fatorRisco;
    }

    public void update(FatorRisco fatorRisco) {
        String sql = "UPDATE fatorRisco SET alteracaoConsciencia=?, deficitMobilidade=?, deficitNutricional=?, peleUmida=?, cisalhamento=?, limitacaoMobilidade=?, criancaIdosoGestante=?, turgorPele=?, imunoDepressao=?, fragilidadeCapilar=?, quimioterapia=?, medHiperosmolar=?, convulsoes=?, delirium=?, visaoAudicao=?, hipotensao=?, usoAlcoolDrogas=?, foraDeRisco=? WHERE Id_FatorRisco=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);

            // Lesão de pele
            pst.setBoolean(1, fatorRisco.isAlteracaoConsciencia());
            pst.setBoolean(2, fatorRisco.isDeficitMobilidade());
            pst.setBoolean(3, fatorRisco.isDeficitNutricional());
            pst.setBoolean(4, fatorRisco.isPeleUmida());
            pst.setBoolean(5, fatorRisco.isCisalhamento());
            pst.setBoolean(6, fatorRisco.isLimitacaoMobilidade());

            // Flebite
            pst.setBoolean(7, fatorRisco.isCriancaIdosoGestante());
            pst.setBoolean(8, fatorRisco.isTurgorPele());
            pst.setBoolean(9, fatorRisco.isImunoDepressao());
            pst.setBoolean(10, fatorRisco.isFragilidadeCapilar());
            pst.setBoolean(11, fatorRisco.isQuimioterapia());
            pst.setBoolean(12, fatorRisco.isMedHiperosmolar());

            // Queda
            pst.setBoolean(13, fatorRisco.isConvulsoes());
            pst.setBoolean(14, fatorRisco.isDelirium());
            pst.setBoolean(15, fatorRisco.isVisaoAudicao());
            pst.setBoolean(16, fatorRisco.isHipotensao());
            pst.setBoolean(17, fatorRisco.isUsoAlcoolDrogas());

            // Não há riscos
            pst.setBoolean(18, fatorRisco.isForaDeRisco());

            pst.setLong(19, fatorRisco.getId());

            pst.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar fatorRisco: " + e.getMessage());
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

    public void delete(FatorRisco fatorRisco) {
        String sql = "DELETE FROM fatorRisco WHERE Id_FatorRisco=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setLong(1, fatorRisco.getId());
            pst.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar fatorRisco: " + e.getMessage());
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
