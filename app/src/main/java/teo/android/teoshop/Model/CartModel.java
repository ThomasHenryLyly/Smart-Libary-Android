package teo.android.teoshop.Model;

public class CartModel {
    private String image;
    private String datechoose;
    private String bookname;
    private String author;
    private String code;

    public CartModel() {
    }

    public CartModel(String image, String datechoose, String bookname, String author, String code) {
        this.image = image;
        this.datechoose = datechoose;
        this.bookname = bookname;
        this.author = author;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDatechoose() {
        return datechoose;
    }

    public void setDatechoose(String datechoose) {
        this.datechoose = datechoose;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
