package edu.avans.hartigehap.domain;

public abstract class NotificationAdapter {

    public enum Type {
        EMAIL("email"),
        SMS("sms");

        private String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private Type type;

    public NotificationAdapter(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    /**
     * Sends a message to the user
     *
     * @param receiver The user that will receive the message
     * @param message The message being send to the user
     *
     * @return true if sent, false if something failed
     */
    public abstract boolean send(String receiver, String message) throws Exception;
}