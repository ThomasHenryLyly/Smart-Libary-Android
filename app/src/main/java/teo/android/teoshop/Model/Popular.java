package teo.android.teoshop.Model;

public class Popular {
    private int product_soluong;
    private String product_name;
    private String product_tacgia;
    private String product_image;
    private String product_image1;
    private String product_image2;
    private String product_image3;
    private String map;
    private String code;

    public Popular() {
    }

    public Popular(int product_soluong, String product_name, String product_tacgia, String product_image, String product_image1, String product_image2, String product_image3, String map, String code) {
        this.product_soluong = product_soluong;
        this.product_name = product_name;
        this.product_tacgia = product_tacgia;
        this.product_image = product_image;
        this.product_image1 = product_image1;
        this.product_image2 = product_image2;
        this.product_image3 = product_image3;
        this.map = map;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public int getProduct_soluong() {
        return product_soluong;
    }

    public void setProduct_soluong(int product_soluong) {
        this.product_soluong = product_soluong;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setPoduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_tacgia() {
        return product_tacgia;
    }

    public void setProduct_tacgia(String product_tacgia) {
        this.product_tacgia = product_tacgia;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_image1() {
        return product_image1;
    }

    public void setProduct_image1(String product_image1) {
        this.product_image1 = product_image1;
    }

    public String getProduct_image2() {
        return product_image2;
    }

    public void setProduct_image2(String product_image2) {
        this.product_image2 = product_image2;
    }

    public String getProduct_image3() {
        return product_image3;
    }

    public void setProduct_image3(String product_image3) {
        this.product_image3 = product_image3;
    }
}
