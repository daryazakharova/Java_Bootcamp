package edu.school21.chat.repositories;

import edu.school21.chat.models.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size) {
    String query = 
        "WITH user_data AS (" +
        "    SELECT u.id, u.login, u.password, " +
        "           ARRAY_AGG(DISTINCT cr.id) AS created_rooms, " +
        "           ARRAY_AGG(DISTINCT ur.id) AS chat_rooms " +
        "    FROM chat.users u " +
        "    LEFT JOIN chat.chatrooms cr ON u.id = cr.owner " +
        "    LEFT JOIN chat.chatrooms ur ON ur.owner IS NOT NULL " +
        "    GROUP BY u.id " +
        "    ORDER BY u.id " +
        "    LIMIT ? OFFSET ? " +
        ") " +
        "SELECT id, login, password, created_rooms, chat_rooms " +
        "FROM user_data;";

    List<User> users = new ArrayList<>();
    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, size);
        preparedStatement.setInt(2, page * size);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");

            List<Chatroom> createdRooms = new ArrayList<>();
            List<Chatroom> chatRooms = new ArrayList<>();

            // Change to Integer[] to match the database type
            Integer[] createdRoomIds = (Integer[]) resultSet.getArray("created_rooms").getArray();
            for (Integer roomId : createdRoomIds) {
                createdRooms.add(new Chatroom(Long.valueOf(roomId), null, null, null));
            }

            Integer[] chatRoomIds = (Integer[]) resultSet.getArray("chat_rooms").getArray();
            for (Integer roomId : chatRoomIds) {
                chatRooms.add(new Chatroom(Long.valueOf(roomId), null, null, null));
            }

            User user = new User(id, login, password, createdRooms, chatRooms);
            users.add(user);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return users;
}
}
