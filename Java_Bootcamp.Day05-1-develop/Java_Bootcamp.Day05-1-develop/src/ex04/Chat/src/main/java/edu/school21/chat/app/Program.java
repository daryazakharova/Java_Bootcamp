package edu.school21.chat.app;

import edu.school21.chat.models.User;
import edu.school21.chat.repositories.*;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.List;


public class Program {
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/day05.ex01"); 
        config.setUsername("postgres"); 
        config.setPassword("123"); 

        initializeDatabase(config); 

        DataSource dataSource = new HikariDataSource(config);       
        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource);

        int page = 0; 
        int size = 2; // Number of users per page
    
        System.out.println("Users on page " + page + ":"); 
        List<User> users = usersRepository.findAll(page, size);
        users.forEach(System.out::println);     
    }
    
    private static void initializeDatabase(HikariConfig config) {
        String dbUrl = config.getJdbcUrl();
        String user = config.getUsername();
        String password = config.getPassword();
        
        DatabaseLoader databaseLoader = new DatabaseLoader(dbUrl, user, password);
        databaseLoader.executeSqlFile("src/main/resources/schema.sql");
        databaseLoader.executeSqlFile("src/main/resources/data.sql");
    }
}
