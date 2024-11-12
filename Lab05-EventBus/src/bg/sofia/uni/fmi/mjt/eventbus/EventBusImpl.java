package bg.sofia.uni.fmi.mjt.eventbus;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.events.EventComparator;
import bg.sofia.uni.fmi.mjt.eventbus.exception.MissingSubscriptionException;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.Subscriber;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class EventBusImpl implements EventBus {

    private final Map<Class<? extends Event<?>>, Set<Subscriber<?>>> subscriptions;
    private final Map<Class<? extends Event<?>>, List<Event<?>>> eventLogs;

    public EventBusImpl() {
        subscriptions = new HashMap<>();
        eventLogs = new HashMap<>();
    }

    @Override
    public <T extends Event<?>> void subscribe(Class<T> eventType, Subscriber<? super T> subscriber) {
        if (subscriber == null) {
            throw new IllegalArgumentException("Subscriber cannot be null");
        }
        if (eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null");
        }

        subscriptions.computeIfAbsent(eventType, k -> new HashSet<>()).add(subscriber);
    }

    @Override
    public <T extends Event<?>> void unsubscribe(Class<T> eventType, Subscriber<? super T> subscriber)
        throws MissingSubscriptionException {
        if (subscriber == null) {
            throw new IllegalArgumentException("Subscriber cannot be null");
        }
        if (eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null");
        }

        Set<Subscriber<?>> subscribers = subscriptions.get(eventType);
        if (subscribers == null) {
            throw new MissingSubscriptionException("Event type " + eventType + " does not exist");
        }
        if (!subscribers.contains(subscriber)) {
            throw new MissingSubscriptionException("Event type " + eventType + " is not subscribed to " + subscriber);
        }

        subscribers.remove(subscriber);
    }

    @Override
    public <T extends Event<?>> void publish(T event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }

        Class<? extends Event<?>> eventType = (Class<? extends Event<?>>) event.getClass();
        eventLogs.computeIfAbsent(eventType, k -> new ArrayList<>()).add(event);

        //make to inform all subscribers
        Set<Subscriber<?>> subscribers = subscriptions.get(eventType);
        if (subscribers != null) {
            for (Subscriber<?> subscriber : subscribers) {
                ((Subscriber<T>) subscriber).onEvent(event);
            }
        }
    }

    @Override
    public void clear() {
        subscriptions.clear();
        eventLogs.clear();
    }

    @Override
    public Collection<? extends Event<?>> getEventLogs(Class<? extends Event<?>> eventType, Instant from, Instant to) {
        if (eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null");
        }
        if (from == null) {
            throw new IllegalArgumentException("From cannot be null");
        }
        if (to == null) {
            throw new IllegalArgumentException("To cannot be null");
        }

        List<Event<?>> events = eventLogs.get(eventType);
        Set<Event<?>> filteredEvents = new TreeSet<>(new EventComparator());

        if (events != null) {
            for (Event<?> event : events) {
                if (!event.getTimestamp().isBefore(from) && event.getTimestamp().isBefore(to)) {
                    filteredEvents.add(event);
                }
            }
        }

        return Collections.unmodifiableSet(filteredEvents);
    }

    @Override
    public <T extends Event<?>> Collection<Subscriber<?>> getSubscribersForEvent(Class<T> eventType) {
        if (eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null");
        }
        if (!subscriptions.containsKey(eventType)) {
            return Collections.unmodifiableList(new ArrayList<>());
        }

        return Collections.unmodifiableSet(subscriptions.get(eventType));
    }

}
