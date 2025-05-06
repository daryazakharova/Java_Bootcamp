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
import java.util.Optional;

public class Program {
    public static void main(String[] args) {
    
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/day05.ex01"); 
        config.setUsername("postgres"); 
        config.setPassword("123"); 

        initializeDatabase(config); 

        DataSource dataSource = new HikariDataSource(config);

        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);

        // Find a message by ID
        Optional<Message> messageOptional = messagesRepository.findById(1L); 
        if (messageOptional.isPresent()) {
            User newAuthor = new User(2L, "newAuthor", "newAuthor", new ArrayList(), new ArrayList());
            Chatroom room = new Chatroom(4L, "ChatRoom", newAuthor, new ArrayList());
            Message message = messageOptional.get();
            System.out.println("Current message:");  
            System.out.println(message); 

            message.setText("Bye!!!");
            message.setCreatedAt(null);
            message.setAuthor(newAuthor);
            message.setRoom(room);

            // Update the message in the database
            messagesRepository.update(message);
            System.out.println("Message updated successfully!!!");  
            System.out.println(message);  
                       
        } else {
            System.out.println("Message not found.");
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