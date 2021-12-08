package mx.edu.utez.database;

import java.sql.*;

public class ConnectionMysql {

    public static Connection getConnection() throws SQLException {
        String host = "127.0.0.1";
        String port = "3306";
        String database = "ventas";
        String useSSL = "false";
        String timezone = "UTC";
        String url = String.format("jdbc:mysql://%s:%s/%s?useSSL=%s&serverTimezone=%s", host, port, database, useSSL, timezone);
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        return DriverManager.getConnection(url, "root", "");
    }

    public static void closeConnections(Connection con, PreparedStatement pstm, ResultSet rs){
        try{
            if(rs != null){ rs.close(); }

            if(pstm != null){ pstm.close(); }

            if(con != null){ con.close(); }

        }catch(SQLException e){ }
    }

    public static void closeConnections(Connection con, PreparedStatement pstm){
        try{
            if(pstm != null){ pstm.close(); }

            if(con != null){ con.close(); }

        }catch(SQLException e){ }
    }

    public static void main(String[] args) {
        try {
            Connection con = ConnectionMysql.getConnection();
            System.out.println("Conexión Exitosa");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("No se encontró la base");
        }
    }
}
