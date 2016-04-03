package edu.avans.hartigehap.domain.notifications;

import edu.avans.hartigehap.domain.NotificationAdapter;
import edu.avans.hartigehap.domain.User;

import java.util.HashMap;
import java.util.Map;

/**
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

    public final void fillNotificationAdapterCache() {
        if (notificationAdapterCache.size() <= 0) {
            notificationAdapterCache.put(Type.EMAIL, new EmailNotification());
        }
    }

    public void setNotificationAdapterCacheValue(Type type, NotificationAdapter notificationAdapter) {
        notificationAdapterCache.put(type, notificationAdapter);
    }
}
