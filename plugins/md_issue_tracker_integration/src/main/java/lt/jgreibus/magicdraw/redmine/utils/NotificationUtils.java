package lt.jgreibus.magicdraw.redmine.utils;

import com.nomagic.magicdraw.ui.notification.Notification;
import com.nomagic.magicdraw.ui.notification.NotificationManager;
import com.nomagic.magicdraw.ui.notification.NotificationSeverity;
import lt.jgreibus.magicdraw.redmine.exception.NotifiedException;

public class NotificationUtils {
    public static void showNotification(NotifiedException e) {
        showNotification(e, NotificationSeverity.ERROR);
    }

    public static void showNotification(NotifiedException e, NotificationSeverity severity) {
        NotificationManager.getInstance().showNotification(buildNotification(e, severity));
    }

    public static Notification buildNotification(NotifiedException e, NotificationSeverity severity) {
        return new Notification(e.getId(), e.getTitle(), e.getMessage(), severity);
    }
}
