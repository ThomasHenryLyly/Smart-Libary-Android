package teo.android.teoshop.Model;

public class LockModel {
    private String lockerpw;
    private String lockernumber;

    public LockModel() {
    }

    public LockModel(String lockerpw, String lockernumber) {
        this.lockerpw = lockerpw;
        this.lockernumber = lockernumber;
    }

    public String getLockerpw() {
        return lockerpw;
    }

    public void setLockerpw(String lockerpw) {
        this.lockerpw = lockerpw;
    }

    public String getLockernumber() {
        return lockernumber;
    }

    public void setLockernumber(String lockernumber) {
        this.lockernumber = lockernumber;
    }
}
