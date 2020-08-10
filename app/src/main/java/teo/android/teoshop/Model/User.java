package teo.android.teoshop.Model;

public class User {
    private String email;
    private String name;
    private String password;
    private String major;
    private String image;


    public User() {
    }

    public User(String email, String name, String password, String major, String image) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.major = major;
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
