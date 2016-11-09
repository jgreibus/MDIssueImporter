package lt.jgreibus.magicdraw.redmine.exception;

public class NotifiedException extends RuntimeException {

    private final String id;
    private final String title;

    public NotifiedException(Throwable cause) {
        this("PLUGIN EXCEPTION", "EXCEPTION", cause.getClass().getSimpleName() + " " + cause.getMessage());
    }

    public NotifiedException(String id, String title, String message) {
        super(message);
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
