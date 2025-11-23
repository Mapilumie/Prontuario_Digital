package br.com.prontuario.dao;

import br.com.prontuario.db.ConnectionFactory;
import br.com.prontuario.model.Enfermeiro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnfermeiroDAO implements IEnfermeiroDAO {
    @Override
    public void save(Enfermeiro enfermeiro) {
        String sql = "INSERT INTO enfermeiro (COREN,nome,email,senha) VALUES (?,?,?,?)";
        PreparedStatement pst = null;
        Connection conexao = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, enfermeiro.getCoren());
            pst.setString(2, enfermeiro.getNome());
            pst.setString(3, enfermeiro.getEmail());
            pst.setString(4, enfermeiro.getSenha());
            pst.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao salvar enfermeiro: " + e.getMessage());
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

    @Override
    public List<Enfermeiro> findAll() {
        String sql = "SELECT * FROM enfermeiro";
        PreparedStatement pst = null;
        Connection conexao = null;
        ResultSet rs = null;
        List<Enfermeiro> enfermeiros = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs != null) {
                enfermeiros = new ArrayList<Enfermeiro>();
                while (rs.next()) {
                    Enfermeiro enfermeiro = new Enfermeiro();
                    enfermeiro.setCoren(rs.getString("COREN"));
                    enfermeiro.setNome(rs.getString("nome"));
                    enfermeiro.setEmail(rs.getString("email"));
                    enfermeiro.setSenha(rs.getString("senha"));
                    enfermeiros.add(enfermeiro);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar enfermeiros: " + e.getMessage());
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
        return enfermeiros;
    }

    @Override
    public Enfermeiro findByCoren(String coren) {
        String sql = "SELECT * FROM enfermeiro WHERE COREN=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        ResultSet rs = null;
        Enfermeiro enfermeiro = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, coren);
            rs = pst.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    enfermeiro = new Enfermeiro();
                    enfermeiro.setCoren(rs.getString("COREN"));
                    enfermeiro.setNome(rs.getString("nome"));
                    enfermeiro.setEmail(rs.getString("email"));
                    enfermeiro.setSenha(rs.getString("senha"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar enfermeiro por coren \"" + coren + "\": " + e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar ResultSet:" + e.getMessage());
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
        return enfermeiro;
    }

    @Override
    public void update(Enfermeiro enfermeiro) {
        String sql = "UPDATE enfermeiro SET nome=?,email=?,senha=? WHERE coren=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, enfermeiro.getNome());
            pst.setString(2, enfermeiro.getEmail());
            pst.setString(3, enfermeiro.getSenha());
            pst.setString(4, enfermeiro.getCoren());
            pst.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar enfermeiro: " + e.getMessage());
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

    @Override
    public void delete(Enfermeiro enfermeiro) {
        String sql = "DELETE FROM enfermeiro where coren=?";
        PreparedStatement pst = null;
        Connection conexao = null;
        try {
            conexao = new ConnectionFactory().getConnection();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, enfermeiro.getCoren());
            pst.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar enfermeiro: " + e.getMessage());
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
