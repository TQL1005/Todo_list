package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.fatscompany.bookseftonline.Entitis.Book;
import com.fatscompany.bookseftonline.Entitis.Category;
import com.fatscompany.bookseftonline.R;

import java.util.List;

public class ProducAdapter extends RecyclerView.Adapter<ProducAdapter.ProductViewHolder> {
    private List<Category> myCate;

    Context context;

    private final iClickItemBookListener iClickItemBookListener;
    //private final iClickItemCateListener iClickItemCateListener;

    public ProducAdapter(Context context, List<Category> myCate, iClickItemBookListener listener) {
        this.context = context;
        this.myCate = myCate;
        this.iClickItemBookListener = listener;
    }
    /*public ProducAdapter(Context context, List<Category> myCate, iClickItemCateListener listener) {
        this.context = context;
        this.setMyCate(myCate);
        this.iClickItemCateListener = listener;
    }*/


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Category cate = myCate.get(position);
        //Book book = myBook.get(position);
        if (cate == null)
            return;
        holder.txtCatName.setText(cate.getName());
        holder.btnEditProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemBookListener.onClickItemBook(cate);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (myCate != null)
            return myCate.size();
        return 0;
    }

    /*public List<Category> getMyCate() {
        return myCate;
    }

    public void setMyCate(List<Category> myCate) {
        this.myCate = myCate;
    }*/


    class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCatName;
        private Button btnEditProd;
        private ConstraintLayout itemBooklayout;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            btnEditProd = itemView.findViewById(R.id.btnEditProd);
            txtCatName = itemView.findViewById(R.id.txtCatName);
            itemBooklayout = itemView.findViewById(R.id.item_product_layout);
        }
    }
}
