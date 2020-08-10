package teo.android.teoshop.Model;

public class NotificationModel {
    private int image;
    private String context;

    public NotificationModel() {
    }

    public NotificationModel(int image, String context) {
        this.image = image;
        this.context = context;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
