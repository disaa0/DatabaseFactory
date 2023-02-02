public class Databases {
    public static void main(String[] args) {
        // Set the connection parameters for the database
        String database = "test";
        String mssql_username = "sa";
        String mysql_username = "root";
        String mssql_password = "M9MBc2t5";
        String mysql_password = "123";
        
        // MSSQL
        System.out.println("MSSQL");
        Database mssql_db = DatabaseFactory.getDatabase("sql", "test", mssql_username, mssql_password);
        mssql_db.connect();
        // Leer información        
        mssql_db.read("SELECT * FROM equipo");
        
        // Escribir a la base de datos
        mssql_db.write("INSERT INTO equipo  VALUES ('Pepe')");
        
        // Disconnect from the database
        mssql_db.disconnect();

        // MySQL
        System.out.println("MYSQL");
        Database mysql_db = DatabaseFactory.getDatabase("mysql", "test", mysql_username, mysql_password);
        mysql_db.connect();
        // Leer información        
        mysql_db.read("SELECT * FROM equipo");
        
        // Escribir a la base de datos
        mysql_db.write("INSERT INTO equipo  VALUES ('Pepe')");
        
        // Disconnect from the database
        mysql_db.disconnect();
    }
}
