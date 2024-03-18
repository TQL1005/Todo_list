package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fatscompany.bookseftonline.Entitis.Book;
import com.fatscompany.bookseftonline.Entitis.Category;
import com.fatscompany.bookseftonline.R;

import java.text.DecimalFormat;
import java.util.List;

public class AdminBookAdapter extends RecyclerView.Adapter<AdminBookAdapter.AdminBookViewHolder> {
    private List<Book> myBook;
    Context context;

    private final iClickItemBookListener iClickItemBookListener;
    //private final iClickItemCateListener iClickItemCateListener;

    public AdminBookAdapter(Context context, List<Book> myBook, iClickItemBookListener listener) {
        this.context = context;
        this.myBook = myBook;
        this.iClickItemBookListener = listener;
    }
    /*public ProducAdapter(Context context, List<Category> myCate, iClickItemCateListener listener) {
        this.context = context;
        this.setMyCate(myCate);
        this.iClickItemCateListener = listener;
    }*/


    @NonNull
    @Override
    public AdminBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_adminbook, parent, false);
        return new AdminBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminBookViewHolder holder, int position) {
        Book book = myBook.get(position);
        if (book == null)
            return;
        holder.txtName.setText("Name: $" + book.getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String formattedPrice = decimalFormat.format(book.getPrice());

        holder.txtPrice.setText("Price: $" + formattedPrice);
        Glide.with(holder.itemView.getContext())
                .load(book.getImage())
                .placeholder(R.drawable.favorite) // Tùy chọn: Ảnh tạm thời trong quá trình tải
                .error(R.drawable.logo) // Tùy chọn: Ảnh hiển thị khi xảy ra lỗi tải
                .into(holder.imgView);

        /*holder.btnEditProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemBookListener.onClickItemBook(cate);
            }
        });*/
        holder.itemBooklayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemBookListener.onClickItemBookEdit(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (myBook != null)
            return myBook.size();
        return 0;
    }

    /*public List<Category> getMyCate() {
        return myCate;
    }

    public void setMyCate(List<Category> myCate) {
        this.myCate = myCate;
    }*/


    class AdminBookViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtPrice;

        private ImageView imgView;
        private ConstraintLayout itemBooklayout;

        public AdminBookViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.adminBookName);
            txtPrice = itemView.findViewById(R.id.adminPriceText);
            imgView = itemView.findViewById(R.id.imgAdminBook);
            itemBooklayout = itemView.findViewById(R.id.item_book_admin_layout);
        }
    }
}
