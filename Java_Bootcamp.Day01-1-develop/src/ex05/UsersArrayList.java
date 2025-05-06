package ex05;

import java.util.Arrays;

public class UsersArrayList implements UsersList {
    private int numberOfUsers = 0;
    private int maxNumberOfUsers = 10;
    private User[] users = new User[maxNumberOfUsers];

    @Override
    public void addUser(User newUser) {
        if (numberOfUsers == maxNumberOfUsers) {
            maxNumberOfUsers *= 2;
            User[] tempUsers = new User[maxNumberOfUsers];
            System.arraycopy(users, 0, tempUsers, 0, numberOfUsers - 1);
            users = tempUsers;
        }
        users[numberOfUsers++] = newUser;
    }

    @Override
    public User getUserById(int id) {
        for (int i = 0; i < numberOfUsers; i++) {
            if (users[i].getIdentifier() == id) {
                return users[i];
            }
        }
        throw new UserNotFoundException("User  with ID " + id + " not found.");
    }

    @Override
    public User getUserByIndex(int index) {
        if (index < 0 || index >= numberOfUsers) {
            throw new UserNotFoundException("Index with index " + index + " is not found");
        }
        return users[index];
    }

    @Override
    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    @Override
    public String toString() {
        return "UsersArrayList:" +
                "maxUsers= " + maxNumberOfUsers +
                ", numberUsers= " + numberOfUsers +
                ", usersArray=" + Arrays.toString(users);
    }
}