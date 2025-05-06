package ex02;

public class Program {
    public static void main(String[] args) {
        UsersList usersList = new UsersArrayList();

        User alice = new User("Alice", 10000);
        User bob = new User("Bob", 3000);
        User mike = new User("Mike", 5000);

        usersList.addUser(alice);
        usersList.addUser(bob);
        usersList.addUser(mike);

        System.out.println("Users in the list:");
        for (int i = 0; i < usersList.getNumberOfUsers(); i++) {
            System.out.println(usersList.getUserByIndex(i));
        }
        try {
            User user = usersList.getUserById(usersList.getUserByIndex(0).getIdentifier());
            System.out.println("Retrieved user on index 0: " + user);
            User user1 = usersList.getUserById(usersList.getUserByIndex(1).getIdentifier());
            System.out.println("Retrieved user on index 1: " + user1);
            User user2 = usersList.getUserById(usersList.getUserByIndex(2).getIdentifier());
            System.out.println("Retrieved user on index 2: " + user2);
            User user3 = usersList.getUserById(usersList.getUserByIndex(3).getIdentifier());
            System.out.println("Retrieved user on index 3: " + user3);
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
        }

        try {
            System.out.println("Retrieved user on ID 1: " + usersList.getUserById(1));
            System.out.println("Retrieved user on ID 2: " + usersList.getUserById(2));
            System.out.println("Retrieved user on ID 3: " + usersList.getUserById(3));
            System.out.println("Retrieved user on ID 27: " + usersList.getUserById(27));
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}