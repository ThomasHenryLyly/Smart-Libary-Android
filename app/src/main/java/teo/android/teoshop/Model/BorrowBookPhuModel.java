package teo.android.teoshop.Model;

public class BorrowBookPhuModel {
    private String product_image;
    private String product_name;
    private String product_tacgia;

    public BorrowBookPhuModel() {
    }

    public BorrowBookPhuModel(String product_image, String product_name, String product_tacgia) {
        this.product_image = product_image;
        this.product_name = product_name;
        this.product_tacgia = product_tacgia;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_tacgia() {
        return product_tacgia;
    }

    public void setProduct_tacgia(String product_tacgia) {
        this.product_tacgia = product_tacgia;
    }
}
