package edu.avans.hartigehap.domain.notifications;

import edu.avans.hartigehap.domain.NotificationAdapter;
import edu.avans.hartigehap.domain.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains a magnificent caching system
 *
 * @author JKetelaar
 */
public class NotificationAdapterImpl extends NotificationAdapter {

    private static Map<Type, NotificationAdapter> notificationAdapterCache = new HashMap<>();

    private User user;

    public NotificationAdapterImpl(User user) {
        super(user.getType());

        this.fillNotificationAdapterCache();
        this.user = user;
    }

    /**
     * Sending the message with just a message, as the constructor already contains the user, which is the receiver
     *
     * @param message Message to be sent
     *
     * @return True if sent correctly, false if not
     *
     * @throws Exception Could throw an exception for some notification adapter implementations
     */
    public boolean send(String message) throws Exception {
        return user.getType() != null && send(user.getType() == Type.EMAIL ? user.getEmail() : user.getPhone(), message);
    }

    @Override
    public boolean send(String receiver, String message) throws Exception {
        if (user.getType() != null) {
            NotificationAdapter notificationAdapter = notificationAdapterCache.get(user.getType());

            return notificationAdapter.send(receiver, message);
        }
        return false;
    }

    /**
     * Filling the notification cache
     */
    public final void fillNotificationAdapterCache() {
        if (notificationAdapterCache.size() <= 0) {
            notificationAdapterCache.put(Type.EMAIL, new EmailNotification());
        }
    }

    /**
     * Custom option to apply extra notification adapters, from the outside!
     * Also allows you to override the basic ones
     *
     * @param type Type of the notification adapter
     * @param notificationAdapter The adapter to be added
     */
    public void setNotificationAdapterCacheValue(Type type, NotificationAdapter notificationAdapter) {
        notificationAdapterCache.put(type, notificationAdapter);
    }
}
