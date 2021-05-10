package db;

import com.sun.rowset.CachedRowSetImpl;
import model.Contact;

import java.sql.*;

public class Database {
    private static final String JDBC_DRIVER = "org.sqlite.JDBC";
    private static Connection connect = null;

    private static final String DATABASE_NAME = "jdbc:sqlite:contacts.db";

    private static void dbDisconnect() {
        try {
            if (connect != null && !connect.isClosed()) {
                connect.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dbConnect() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException error) {
            System.err.println("error didn't found");
            throw error;
        }

        try {
            connect = DriverManager.getConnection(DATABASE_NAME);
        } catch (SQLException error) {
            System.err.println("failed: " + error);
            throw error;
        }
    }

    public void contactCreate(Contact contact) {
        String name = contact.getName();
        String phone = contact.getPhone();

        //запрос
        String sql = "INSERT INTO peoples(name, phone) VALUES(?, ?)";

        execQuery(sql, name, phone);
    }

    public void contactUpdate(Contact contact) {
        String name = contact.getName();
        String phone = contact.getPhone();

        String sql = "UPDATE peoples SET name = ?  WHERE phone = ?";

        execQuery(sql, name, phone);
    }


    public void execQuery(String sql, String phone) {
        try {
            dbConnect();

            PreparedStatement st = connect.prepareStatement(sql);
            st.setString(1, phone);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (connect != null) {
            dbDisconnect();
        }
    }

    public static ResultSet dbExecute(String slqQuery) throws SQLException, ClassNotFoundException {
        CachedRowSetImpl cachedRowSet = null;

        dbConnect();
        Statement statement = connect.createStatement();
        try (ResultSet res = statement.executeQuery(slqQuery)) {
            cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(res);
        } catch (SQLException error) {
            System.out.println("Error dbExecute" + error);
        } finally {
            dbDisconnect();
        }
        return cachedRowSet;
    }

    public void execQuery(String sql, String name, String phone) {
        try {
            dbConnect();

            PreparedStatement st = connect.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, phone);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (connect != null) {
            dbDisconnect();
        }
    }

}