package app;

import manager.OrmManager;
import model.User;


public class Program {
    public static void main(String[] args) {
        OrmManager ormManager = new OrmManager();
        try {
            // Initialize the ORM manager with the User class
            ormManager.initialize();
            // Create a new User instance
            User user1 = new User(1L, "firstName1", "lastName1", 37);
            User user2 = new User(2L, "firstName2", "lastName2", 35);
            // Save the user
            ormManager.save(user1);
            ormManager.save(user2);
            // Update the user
            user1.setAge(100);
            ormManager.update(user1);
            user2.setFirstName("User2");
            ormManager.update(user2);
            // Find user by ID
            System.out.println(ormManager.findById(1L, User.class));
            System.out.println(ormManager.findById(2L, User.class));
            System.out.println(ormManager.findById(3L, User.class));
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            System.exit(-1);

        } finally {
            ormManager.close();
            System.exit(0);
        }
    }
}
