import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;

public class Database {

    private static Connection connection;
    private static Statement statement;
    private final String URL;

    public Database(String path) {
        this.URL = "jdbc:sqlite:" + path;
        connect();
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(this.URL);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void executeUpdate(String query) {
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAllTable(String tableName) {
        return executeQuery("SELECT * FROM " + tableName);
    }


    public <T> ArrayList<T> getAllColumn(String tableName, String columnName) {
        ArrayList<T> column = new ArrayList<>();
        ResultSet table = getAllTable(tableName);
        try {
            while (table.next())
                column.add((T) table.getObject(columnName));
            return column;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean columnContains(String tableName, String columnName, Object target) {
        ResultSet table = getAllTable(tableName);
        try {
            while (table.next()) {
                if (table.getObject(columnName).equals(target))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean inSameTuple(String tableName, Object... args) {
        ResultSet table = getAllTable(tableName);
        try {
            ResultSetMetaData meta = table.getMetaData();
            int counter;
            while (table.next()) {
                counter = 0;
                for (Object arg : args)
                    for (int i = 1; i <= meta.getColumnCount(); ++i)
                        if (arg.equals(table.getObject(i))) {
                            ++counter;
                            if (counter == args.length)
                                return true;
                            break;
                        }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet selectFromWhere(String columnName, String tableName, String condition) {
        return executeQuery(String.format("SELECT %s FROM %s WHERE %s", columnName, tableName, condition));
    }

    public void deleteFromWhere(String tableName, String condition) {
        executeUpdate(String.format("DELETE FROM %s WHERE %s", tableName, condition));
    }

    public void insertTuple(String tableName, Object... values) {
        StringBuilder fullValues = new StringBuilder();
        for (Object value : values)
            fullValues.append('\'').append(value).append('\'').append(", ");
        fullValues.delete(fullValues.lastIndexOf(","), fullValues.length());
        executeUpdate(String.format("INSERT INTO %s VALUES(%s)", tableName, fullValues.toString()));
    }
}
