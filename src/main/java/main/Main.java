package main;

import static persistence.config.ConnectionConfig.getConnection;
import persistence.migration.MigrationStrat;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (var connection = getConnection()) {
            System.out.println("Conexão estabelecida com sucesso!");
            new MigrationStrat(connection).executeMigration();
            System.out.println("Migração concluída com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro geral: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
