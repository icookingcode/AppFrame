package com.hnhy.epapp.mvp.ui.activity.vpn;

import java.util.LinkedHashMap;

/**
 * Created by guc on 2019/10/9.
 * 描述：
 */

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private int maxSize;
    private CleanupCallback callback;

    public LRUCache(int maxSize, CleanupCallback callback) {
        super(maxSize + 1, 1, true);

        this.maxSize = maxSize;
        this.callback = callback;
    }

    @Override
    protected boolean removeEldestEntry(Entry<K, V> eldest) {
        if (size() > maxSize) {
            callback.cleanup(eldest);
            return true;
        }
        return false;
    }

    public static interface CleanupCallback<K, V> {
        public void cleanup(Entry<K, V> eldest);
    }
}
