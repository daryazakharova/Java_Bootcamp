package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.repositories.DatabaseLoader;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.school21.chat.repositories.NotSavedSubEntityException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/day05.ex01"); 
        config.setUsername("postgres"); 
        config.setPassword("123"); 

        initializeDatabase(config);

        DataSource dataSource = new HikariDataSource(config);

        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);

        User creator = new User(3L, "user3", "password3", new ArrayList<>(), new ArrayList<>());
        Chatroom room = new Chatroom(2L, "Chatroom 2", creator, new ArrayList<>());
        Message message = new Message(0L, creator, room, "Hello! Testing ex02!", LocalDateTime.now());

        messagesRepository.save(message);
        System.out.println("Message ID: " + message.getId());

        // Test with invalid author room(null ID)
        try {
            User invalidAuthor = new User(null, "invalidUser ", "password", new ArrayList<>(), new ArrayList<>());
            Message invalidMessage = new Message(0L, invalidAuthor, room, "This should fail!", LocalDateTime.now());
            messagesRepository.save(invalidMessage);
            //Chatroom room2 = new Chatroom(null, "Chatroom 2", creator, new ArrayList<>());
            //Message invalidMessage2 = new Message(0L, creator, room2, "This should fail!", LocalDateTime.now());
            //messagesRepository.save(invalidMessage2);
        } catch (NotSavedSubEntityException e) {
            System.out.println("Exception: " + e.getMessage());
        }
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