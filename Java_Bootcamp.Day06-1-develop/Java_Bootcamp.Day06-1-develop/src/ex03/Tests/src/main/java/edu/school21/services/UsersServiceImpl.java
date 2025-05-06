package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {
    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean authenticate(String login, String password) {
        User user = usersRepository.findByLogin(login);

        if (user == null) {
            throw new EntityNotFoundException("Invalid login");
        }

        if (user.isAuthenticated()) {
            throw new AlreadyAuthenticatedException("User is already authenticated");
        }

        if (!user.getPassword().equals(password)) {
            throw new EntityNotFoundException("Invalid password");
        }

        user.setAuthenticated(true);
        usersRepository.update(user);
        return true;
    }
}