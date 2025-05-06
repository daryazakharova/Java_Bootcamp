-- schema.sql

-- Drop the chat schema if it exists and create a new one
DROP SCHEMA IF EXISTS chat CASCADE;
CREATE SCHEMA IF NOT EXISTS chat;

-- Create the users table
CREATE TABLE IF NOT EXISTS chat.users (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    login TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL
);

-- Create the chatrooms table
CREATE TABLE IF NOT EXISTS chat.chatrooms (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name TEXT UNIQUE NOT NULL,
    owner INTEGER NOT NULL REFERENCES chat.users(id) 
);

-- Create the messages table
CREATE TABLE IF NOT EXISTS chat.messages (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    author INTEGER NOT NULL REFERENCES chat.users(id),
    chatroom INTEGER NOT NULL REFERENCES chat.chatrooms(id),
    text TEXT NOT NULL,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
