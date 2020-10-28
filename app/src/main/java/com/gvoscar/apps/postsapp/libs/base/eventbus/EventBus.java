package com.gvoscar.apps.postsapp.libs.base.eventbus;


public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
