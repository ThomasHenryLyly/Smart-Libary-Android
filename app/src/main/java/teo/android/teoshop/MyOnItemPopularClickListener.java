package teo.android.teoshop;

import teo.android.teoshop.Model.ButtonProfileModel;
import teo.android.teoshop.Model.Category;
import teo.android.teoshop.Model.Popular;

public interface MyOnItemPopularClickListener {
    void onClick(Popular popular);
    void onClick(Category category);
}
