package ex01;

public class Program {
    public static void main(String[] args) {

        User user1 = new User("Harry", 10000);
        User user2 = new User("Sally", 15000);
        User user3 = new User("John", 20000);
        User user4 = new User("Mike", 200);

        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
        System.out.println(user4);
    }
}