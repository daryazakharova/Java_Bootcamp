package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.DatabaseLoader;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
    
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/day05.ex01"); 
        config.setUsername("postgres"); 
        config.setPassword("123"); 

        initializeDatabase(config);

        DataSource dataSource = new HikariDataSource(config);

        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a message ID\n-> ");
            if (!scanner.hasNextLong()) {
                System.out.println("Invalid ID format");
                System.exit(-1);
            }
        Long messageId = scanner.nextLong();

        Optional<Message> messageOptional = messagesRepository.findById(messageId);

        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            System.out.println(message);
        } else {
            System.out.println("Message not found.");
        }

        scanner.close();
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