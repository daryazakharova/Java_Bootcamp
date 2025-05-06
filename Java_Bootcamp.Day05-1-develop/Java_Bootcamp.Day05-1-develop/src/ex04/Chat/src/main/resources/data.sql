-- data.sql

-- Insert sample users
INSERT INTO chat.users (login, password) VALUES
('user1', 'password1'),
('user2', 'password2'),
('user3', 'password3'),
('user4', 'password4'),
('user5', 'password5');

-- Insert sample chatrooms
INSERT INTO chat.chatrooms (name, owner) VALUES
('Chatroom 1', 1),
('Chatroom 2', 2),
('Chatroom 3', 3),
('Chatroom 4', 4),
('Chatroom 5', 5);

-- Insert sample messages
INSERT INTO chat.messages (author, chatroom, text, createdAt) VALUES
(1, 1, 'Hello from user1!', CURRENT_TIMESTAMP),
(2, 1, 'Hi user1! This is user2.', CURRENT_TIMESTAMP),
(3, 2, 'Welcome to chatroom 2!', CURRENT_TIMESTAMP),
(4, 3, 'User  4 has joined chatroom 3.', CURRENT_TIMESTAMP),
(5, 4, 'User  5 says hi!', CURRENT_TIMESTAMP);
