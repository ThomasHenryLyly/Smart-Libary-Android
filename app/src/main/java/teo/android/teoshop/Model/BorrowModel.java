package teo.android.teoshop.Model;

public class BorrowModel {
    private String product_image;
    private String product_name;
    private String product_tacgia;
    private String id;
    private String mfg;
    private String exp;

    public BorrowModel() {
    }

    public BorrowModel(String product_image, String product_name, String product_tacgia, String id, String mfg, String exp) {
        this.product_image = product_image;
        this.product_name = product_name;
        this.product_tacgia = product_tacgia;
        this.id = id;
        this.mfg = mfg;
        this.exp = exp;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMfg() {
        return mfg;
    }

    public void setMfg(String mfg) {
        this.mfg = mfg;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }
}
