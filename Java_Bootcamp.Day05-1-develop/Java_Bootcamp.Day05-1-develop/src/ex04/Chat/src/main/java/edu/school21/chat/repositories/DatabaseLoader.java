package edu.school21.chat.repositories;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseLoader {
    private String dbUrl;
    private String dbUser;
    private String dbPass;

    public DatabaseLoader(String dbUrl, String dbUser, String dbPass) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
    }

    public void executeSqlFile(String path) {
        try(Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            BufferedReader br = new BufferedReader(new FileReader(path));
            Statement stmt = conn.createStatement()) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            stmt.execute(sb.toString());
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}