package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.cachehw.MyCache;

import java.util.Optional;

public class DbServiceUserCachedImpl implements DBServiceUser {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserCachedImpl.class);

    private final UserDao userDao;
    private final MyCache<String, User> cache = new MyCache<>();

    public DbServiceUserCachedImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public long saveUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userDao.insertOrUpdate(user);
                long userId = user.getId();
                sessionManager.commitSession();

                logger.info("created user: {}", userId);
                cache.put(String.valueOf(userId), user);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }


    @Override
    public Optional<User> getUser(long id) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {

                Optional<User> cachedUser = Optional.ofNullable(cache.get(String.valueOf(id)));
                if(cachedUser.isPresent()){
                    logger.info("user: {}", cachedUser);
                    return cachedUser;
                }

                Optional<User> userOptional = userDao.findById(id);
                cache.put(String.valueOf(id), userOptional.get());
                logger.info("user: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}


