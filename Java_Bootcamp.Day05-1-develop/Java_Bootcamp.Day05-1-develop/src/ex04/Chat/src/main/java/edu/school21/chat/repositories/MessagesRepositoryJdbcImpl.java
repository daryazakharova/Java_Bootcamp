package edu.school21.chat.repositories;

import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.models.Chatroom;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        String query = "SELECT m.id, m.text, m.createdAt, " +
                       "u.id AS author_id, u.login AS author_login, u.password AS author_password, " +
                       "c.id AS chatroom_id, c.name AS chatroom_name, c.owner AS chatroom_author " +
                       "FROM chat.messages m " +
                       "JOIN chat.users u ON m.author = u.id " +
                       "JOIN chat.chatrooms c ON m.chatroom = c.id " +
                       "WHERE m.id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User author = new User(
                resultSet.getLong("author_id"),
                resultSet.getString("author_login"),
                resultSet.getString("author_password"),
                null, 
                null
                );

                Chatroom chatroom = new Chatroom(
                resultSet.getLong("chatroom_id"),
                resultSet.getString("chatroom_name"),
                null,
                null 
                );

                Message message = new Message(
                resultSet.getLong("id"),
                author,
                chatroom,
                resultSet.getString("text"),
                resultSet.getTimestamp("createdAt") != null ? resultSet.getTimestamp("createdAt").toLocalDateTime() : null // Handle potential null
                );
                
            return Optional.of(message);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

     @Override
    public void save(Message message) {
        User author = message.getAuthor();
        Chatroom room = message.getRoom();

        // Check if the author and room have valid IDs
        if (author == null || author.getId() == null) {
            throw new NotSavedSubEntityException("Author ID is null or does not exist.");
        }

         if (room == null || room.getId() == null) {
            throw new NotSavedSubEntityException("Chatroom ID is null or does not exist.");
        }

        String query = "INSERT INTO chat.messages (author, chatroom, text, createdAt) VALUES (?, ?, ?, ?) RETURNING id";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, author.getId());
            preparedStatement.setLong(2, room.getId());
            preparedStatement.setString(3, message.getText());
            preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(message.getCreatedAt()));

            //insert and retrieve the generated ID
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long generatedId = resultSet.getLong("id");
                message.setId(generatedId); // Set the generated ID to the message
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Message message) {
        String query = "UPDATE chat.messages SET text = ?, createdAt = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, message.getText()); 
            preparedStatement.setTimestamp(2, message.getCreatedAt() != null ? 
            java.sql.Timestamp.valueOf(message.getCreatedAt()) : null); // Set the datetime, allow null
            preparedStatement.setLong(3, message.getId());

            // Execute the update
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}