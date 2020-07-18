package hw11.cache;

import org.junit.Test;
import org.junit.Assert;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
import ru.otus.core.model.User;

public class MyCacheTest {
    @Test
    public void crateTest() {
        var cacheStr = new MyCache<String, User>();
        var cacheInteger = new MyCache<Integer, User>();
        var cacheChar = new MyCache<Character, User>();
    }

    @Test
    public void putUser() {
        var cache = new MyCache<String, User>();
        User user = new User();
        user.setName("hero");
        cache.put("1", user);
    }

    @Test
    public void putGetUser() {
        var cache = new MyCache<String, User>();
        User user = new User();
        user.setName("hero");
        cache.put("1", user);
        User obtained = cache.get("1");
        Assert.assertEquals(user, obtained);
    }

    @Test
    public void putRemoveUser() {
        var cache = new MyCache<String, User>();
        User user = new User();
        user.setName("hero");
        cache.put("1", user);
        cache.remove("1");
        Assert.assertNull(cache.get("1"));
    }

    @Test
    public void addOneListener(){
        Logger logger = LoggerFactory.getLogger(MyCache.class);
        HwListener<String, User> listenerOne = (String key, User value, String action) -> {
            logger.info("ONE key:{}, value:{}, action: {}", key, value, action);
        };

        var cache = new MyCache<String, User>();
        cache.addListener(listenerOne);
        User user = new User();
        user.setName("hero");
        cache.put("1", user);
    }


    /**
     * Оповещения в консоли есть
     */
    @Test
    public void addSeveralListener(){
        Logger logger = LoggerFactory.getLogger(MyCache.class);
        HwListener<String, User> listenerOne = (String key, User value, String action) -> {
            logger.info("ONE key:{}, value:{}, action: {}", key, value, action);
        };

        HwListener<String, User> listenerTwo = (String key, User value, String action) -> {
            logger.info("TWO key:{}, value:{}, action: {}", key, value, action);
        };
        var cache = new MyCache<String, User>();
        cache.addListener(listenerOne);
        cache.addListener(listenerTwo);
        User user = new User();
        user.setName("hero");
        cache.put("1", user);
    }

    /**
     * Нет оповещений в консоли
     */
    @Test
    public void removeSeveralListener(){
        Logger logger = LoggerFactory.getLogger(MyCache.class);
        HwListener<String, User> listenerOne = (String key, User value, String action) -> {
            logger.info("ONE key:{}, value:{}, action: {}", key, value, action);
        };

        HwListener<String, User> listenerTwo = (String key, User value, String action) -> {
            logger.info("TWO key:{}, value:{}, action: {}", key, value, action);
        };
        var cache = new MyCache<String, User>();

        cache.addListener(listenerOne);
        cache.addListener(listenerTwo);
        cache.removeListener(listenerOne);
        cache.removeListener(listenerTwo);

        User user = new User();
        user.setName("hero");
        cache.put("1", user);
    }
}
