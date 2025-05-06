package ex02;

public class UsersArrayList implements UsersList {
    private User[] users;
    private int size;

    public UsersArrayList() {
        users = new User[10];
        size = 0;
    }

    @Override
    public void addUser(User user) {
        if (size == users.length) {
            User[] newUsers = new User[users.length + users.length / 2];
            System.arraycopy(users, 0, newUsers, 0, users.length);
            users = newUsers;
        }
        users[size++] = user;
    }

    @Override
    public User getUserById(int id) {
        for (int i = 0; i < size; i++) {
            if (users[i].getIdentifier() == id) {
                return users[i];
            }
        }
        throw new UserNotFoundException("User  with ID " + id + " not found.");
    }

    @Override
    public User getUserByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new UserNotFoundException("Index " + index + " not found.");
        }
        return users[index];
    }

    @Override
    public int getNumberOfUsers() {
        return size;
    }
}