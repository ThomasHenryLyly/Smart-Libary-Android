package teo.android.teoshop.Model;

public class ButtonProfileModel {
    private int image;
    private String function;
    private int stt;



    public ButtonProfileModel() {
    }

    public ButtonProfileModel(int image, String function, int stt) {
        this.image = image;
        this.function = function;
        this.stt = stt;
    }
    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}
