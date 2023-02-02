import java.sql.*;
import java.util.*;
abstract class Database {
    protected String connectionString;
    protected Connection connection;
    protected String username;
    protected String password;
    protected String database;
    
    public abstract void connect();

    public List<Map<String, Object>> read(String stringStatement) {

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(stringStatement);

            int columnNo = (resultSet.getMetaData().getColumnCount());
            List<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= columnNo; i++) {
                columnNames.add(resultSet.getMetaData().getColumnName(i));
            }

            // Store query result data
            List<Map<String, Object>> resultData = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (String columnName : columnNames) {
                    row.put(columnName, resultSet.getObject(columnName));
                }
                resultData.add(row);
            }

            // Print result data
            System.out.println("Result data: ");
            for (Map<String, Object> row : resultData) {
                System.out.println(row);
            }
            return resultData;
        } catch (SQLException e) {
            System.out.println("Error reading from the database: " + e.getMessage());
        }
        return null;
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error disconnecting from the database: " + e.getMessage());
        }
    }

    public void write(String stringStatement) {
        try {
            PreparedStatement statement = connection.prepareStatement(stringStatement);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error writing to the database: " + e.getMessage());
        }
    }
}

class MySQL extends Database {
    public MySQL(String database, String username, String password) {
        this.connectionString = "jdbc:mysql://localhost:3306/" + database;
        this.username = username;
        this.password = password;
        this.database = database;
    }
    
    public void connect() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(connectionString, username, password);
            } catch (ClassNotFoundException e) {
                System.out.println("MySQL driver not found");
            } catch (SQLException e) {
                System.out.println("Error connecting to the database: " + e.getMessage());
            }
    }
}

class SQL extends Database {
    public SQL(String database, String username, String password) {
        this.connectionString = "jdbc:sqlserver://localhost:1433" + "; database= " + database + "; user=" + username + "; password=" + password + "; encrypt=false; trustServerCertificate=true;";
        this.username = username;
        this.password = password;
        this.database = database;
    }
    
    public void connect() {
        // Connect to SQL database
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class DatabaseFactory {
    public static Database getDatabase(String type, String database, String username, String password) {
        if (type.equalsIgnoreCase("mysql")) {
            return new MySQL(database, username, password);
        } else if (type.equalsIgnoreCase("sql")) {
            return new SQL(database, username, password);
        }
        
        return null;
    }
}
