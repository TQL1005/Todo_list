package Adapter;

import com.fatscompany.bookseftonline.Entitis.Category;

public interface iClickItemCateListener {
    public void onClickItemCate(Category cate);

    void onPositiveClick(String title, String description);

    void onNegativeClick();
}
