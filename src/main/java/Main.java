import persistence.migration.MigrationStrat;
import ui.MainMenu;

import java.sql.SQLException;

import static persistence.config.ConnectionConfig.getConnection;


public class Main {

    public static void main(String[] args) throws SQLException {
        try(var connection = getConnection()){
            new MigrationStrat(connection).executeMigration();
        }
        new MainMenu().execute();
    }

}