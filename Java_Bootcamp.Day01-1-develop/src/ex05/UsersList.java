package ex05;

public interface UsersList {
    public void addUser(User newUser);

    public User getUserById(int id);

    public User getUserByIndex(int index);

    public int getNumberOfUsers();
}