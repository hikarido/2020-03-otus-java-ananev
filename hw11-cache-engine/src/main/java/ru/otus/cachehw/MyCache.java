package ru.otus.cachehw;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwListener;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {

    private final Map<K, V> storage = new WeakHashMap<>();
    private final List<WeakReference<HwListener<K, V>>> listeners = new ArrayList<>();
    private final static String PUT_OPERATION_NAME = "put";
    private final static String REMOVE_OPERATION_NAME = "remove";
    private final static String GET_OPERATION_NAME = "get";
    private final static Logger LOGGER = LoggerFactory.getLogger(MyCache.class);

    @Override
    public void put(K key, V value) {
        storage.put(key, value);
        notifyAllListeners(key, value, PUT_OPERATION_NAME);
    }

    @Override
    public void remove(K key) {
        V value = storage.remove(key);
        notifyAllListeners(key, value, REMOVE_OPERATION_NAME);
    }

    @Override
    public V get(K key) {
        V value = storage.get(key);
        notifyAllListeners(key, value, GET_OPERATION_NAME);
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        WeakReference<HwListener<K, V>> refOnListener = new WeakReference<>(listener);
        listeners.add(refOnListener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        List<WeakReference<HwListener<K, V>>> sameListeners = listeners.stream()
                .filter(ref -> Objects.equals(ref.get(), listener))
                .collect(Collectors.toList());
        listeners.removeAll(sameListeners);
    }

    private void notifyAllListeners(K key, V value, String actionName) {
        for (var listenerRef : listeners) {
            var listener = listenerRef.get();
            if (listener != null) {
                listener.notify(key, value, actionName);
            }
        }
    }
}
