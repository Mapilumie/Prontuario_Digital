package br.com.prontuario.dao;

import br.com.prontuario.db.ConnectionFactory;
import br.com.prontuario.model.Enfermeiro;
import br.com.prontuario.model.FatorRisco;
import br.com.prontuario.model.HistoricoSaude;
import br.com.prontuario.model.Prontuario;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProntuarioDAO implements IProntuarioDAO {
    // Puxa o fuso horário da Bahia
    private static final ZoneId FUSO_HORARIO = ZoneId.of("America/Bahia");

    public void save(Prontuario prontuario) {
        String sql = "INSERT INTO prontuario (observacao, motivoOncologico, dataEmissao, COREN, Id_FatorRisco, Id_HistoricoSaude) VALUES (?,?,?,?,?,?)";
        PreparedStatement pst = null;
        Connection conexao = null;
        ResultSet rs = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, prontuario.getObservacao());
            pst.setString(2, prontuario.getMotivoOncologico());
            pst.setTimestamp(3, java.sql.Timestamp.from(prontuario.getDataEmissao().atZone(FUSO_HORARIO).toInstant()));
            if (prontuario.getFatorRisco() != null) {
                pst.setString(4, prontuario.getEnfermeiro().getCoren());
            } else {
                throw new SQLException("Erro ao salvar prontuario: Nenhum fatorRisco vinculado");
            }
            if (prontuario.getEnfermeiro() != null) {
                pst.setLong(5, prontuario.getFatorRisco().getId());
            } else {
                throw new SQLException("Erro ao salvar prontuario: Nenhum enfermeiro vinculado");
            }
            if (prontuario.getHistoricoSaude() != null) {
                pst.setLong(6, prontuario.getHistoricoSaude().getId());
            } else {
                throw new SQLException("Erro ao salvar prontuario: Nenhum historicoSaude vinculado");
            }
            pst.execute();

            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                prontuario.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar prontuario: " + e.getMessage());
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
            }

            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar Connection: " + e.getMessage());
            }
        }
    }

    public List<Prontuario> findAll() {
        String sql = "SELECT * FROM prontuario ORDER BY dataEmissao DESC";
        PreparedStatement pst = null;
        Connection conexao = null;
        ResultSet rs = null;
        List<Prontuario> prontuarios = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs != null) {
                prontuarios = new ArrayList<Prontuario>();
                while (rs.next()) {
                    Prontuario prontuario = new Prontuario();
                    prontuario.setId(rs.getLong("Id_Prontuario"));
                    prontuario.setObservacao(rs.getString("observacao"));
                    prontuario.setMotivoOncologico(rs.getString("motivoOncologico"));

                    ZonedDateTime emissaoFusoHorario = rs.getTimestamp("dataEmisao").toInstant().atZone(FUSO_HORARIO);
                    prontuario.setDataEmissao(emissaoFusoHorario.toLocalDateTime());

                    String fkEnfermeiro = rs.getString("COREN");
                    if (fkEnfermeiro != null) {
                        Enfermeiro enfermeiro = new Enfermeiro();
                        enfermeiro.setCoren(fkEnfermeiro);
                        prontuario.setEnfermeiro(enfermeiro);
                    }
                    long fkFatorRisco = rs.getLong("Id_FatorRisco");
                    if (fkFatorRisco != 0) {
                        FatorRisco fatorRisco = new FatorRisco();
                        fatorRisco.setId(fkFatorRisco);
                        prontuario.setFatorRisco(fatorRisco);
                    }
                    long fkHistoricoSaude = rs.getLong("Id_HistoricoSaude");
                    if (fkHistoricoSaude != 0) {
                        HistoricoSaude historicoSaude = new HistoricoSaude();
                        historicoSaude.setId(fkHistoricoSaude);
                        prontuario.setHistoricoSaude(historicoSaude);
                    }
                    prontuarios.add(prontuario);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar prontuarios: " + e.getMessage());
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
        return prontuarios;
    }

    public Prontuario findById(Long id) {
        String sql = "SELECT * FROM prontuario WHERE Id_Prontuario=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        ResultSet rs = null;
        Prontuario prontuario = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    prontuario = new Prontuario();
                    prontuario.setId(rs.getLong("Id_Prontuario"));
                    prontuario.setObservacao(rs.getString("observacao"));
                    prontuario.setMotivoOncologico(rs.getString("motivoOncologico"));

                    ZonedDateTime emissaoFusoHorario = rs.getTimestamp("dataEmisao").toInstant().atZone(FUSO_HORARIO);
                    prontuario.setDataEmissao(emissaoFusoHorario.toLocalDateTime());

                    String fkEnfermeiro = rs.getString("COREN");
                    if (fkEnfermeiro != null) {
                        Enfermeiro enfermeiro = new Enfermeiro();
                        enfermeiro.setCoren(fkEnfermeiro);
                        prontuario.setEnfermeiro(enfermeiro);
                    }
                    long fkFatorRisco = rs.getLong("Id_FatorRisco");
                    if (fkFatorRisco != 0) {
                        FatorRisco fatorRisco = new FatorRisco();
                        fatorRisco.setId(fkFatorRisco);
                        prontuario.setFatorRisco(fatorRisco);
                    }
                    long fkHistoricoSaude = rs.getLong("Id_HistoricoSaude");
                    if (fkHistoricoSaude != 0) {
                        HistoricoSaude historicoSaude = new HistoricoSaude();
                        historicoSaude.setId(fkHistoricoSaude);
                        prontuario.setHistoricoSaude(historicoSaude);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar prontuario por id {" + id + "}: " + e.getMessage());
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
        return prontuario;
    }

    public void update(Prontuario prontuario) {
        String sql = "UPDATE prontuario SET observacao = ?, motivoOncologico = ? WHERE Id_Prontuario=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, prontuario.getObservacao());
            pst.setString(2, prontuario.getMotivoOncologico());
            pst.setLong(3, prontuario.getId());
            pst.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar prontuario: " + e.getMessage());
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

    public void delete(Prontuario prontuario) {
        String sql = "DELETE FROM prontuario WHERE Id_Prontuario=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setLong(1, prontuario.getId());
            pst.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar prontuario: " + e.getMessage());
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
