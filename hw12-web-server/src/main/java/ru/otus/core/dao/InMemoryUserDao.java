package ru.otus.core.dao;

import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.*;

public class InMemoryUserDao implements UserDao {

    private final Map<Long, User> users;

    public InMemoryUserDao() {
        users = new HashMap<>();
        users.put(1L, new User(1L, "Крис Гир"));
        users.put(2L, new User(2L, "Ая Кэш"));
        users.put(3L, new User(3L, "Десмин Боргес"));
        users.put(4L, new User(4L, "Кетер Донохью"));
        users.put(5L, new User(5L, "Стивен Шнайдер"));
        users.put(6L, new User(6L, "Джанет Вэрни"));
        users.put(7L, new User(7L, "Брэндон Смит"));
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public long insertUser(User user) {
        return 0;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void insertOrUpdate(User user) {

    }

    @Override
    public SessionManager getSessionManager() {
        return null;
    }

    @Override
    public List<Long> getAllIds() {
        return null;
    }

    //    @Override
//    public Optional<User> findRandomUser() {
//        Random r = new Random();
//        return users.values().stream().skip(r.nextInt(users.size() - 1)).findFirst();
//    }
//
//    @Override
//    public Optional<User> findByLogin(String login) {
//        return users.values().stream().filter(v -> v.getLogin().equals(login)).findFirst();
//    }
}
