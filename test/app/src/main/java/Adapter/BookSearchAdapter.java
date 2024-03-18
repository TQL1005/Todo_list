package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fatscompany.bookseftonline.Entitis.Book;
import com.fatscompany.bookseftonline.R;

import java.text.DecimalFormat;
import java.util.List;

public class BookSearchAdapter extends RecyclerView.Adapter<BookSearchAdapter.BookSearchViewHolder> implements View.OnClickListener {
    List<Book> myListBook;
    private int currentQuantity = 1;

    Context context;
    public iClickItemUserListener itemOnClickListener;

    public BookSearchAdapter(Context context, List<Book> myListBook) {

        this.context = context;
        this.myListBook = myListBook;
    }


    @Override
    public void onClick(View v) {

    }

    @NonNull
    @Override
    public BookSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book_result, parent, false);
        return new BookSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookSearchViewHolder holder, int position) {
        Book myBook = myListBook.get(position);
        if (myBook == null)
            return;
        holder.txtTitle.setText(myBook.getTitle());
        holder.txtAuthor.setText(myBook.getAuthors());
        double price = myBook.getPrice();

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String formattedPrice = decimalFormat.format(price);

        holder.txtPrice.setText("Price: $" + formattedPrice);


//        holder.txtPrice.setText("Price: $" + String.valueOf(myBook.getPrice()));


        Glide.with(holder.itemView.getContext())
                .load(myBook.getImage())
                .placeholder(R.drawable.favorite)
                .error(R.drawable.logo)
                .into(holder.imgVBook1);
        holder.edtQuantity.setText(String.valueOf(myBook.getCurrentQuantity()));

        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myBook.getCurrentQuantity() > 1) {
                    myBook.setCurrentQuantity(myBook.getCurrentQuantity() - 1);
                    holder.edtQuantity.setText(String.valueOf(myBook.getCurrentQuantity()));
                }
            }
        });

        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBook.setCurrentQuantity(myBook.getCurrentQuantity() + 1);
                holder.edtQuantity.setText(String.valueOf(myBook.getCurrentQuantity()));
            }
        });

    }


    @Override
    public int getItemCount() {
        if (myListBook != null)
            return myListBook.size();
        return 0;
    }

    class BookSearchViewHolder extends RecyclerView.ViewHolder {
        //// trong này thì sẽ ánh xạ view
        private ImageView imgVBook1; /// khai báo cái này để thằng viewHolder biết để show cái quần què gì ra rồi dùng nó ánh xạ vào cái viewholder
        private TextView txtTitle;
        private TextView txtAuthor;
        private TextView txtPrice;
        private CardView layoutResult;
        private Button btnIncrease;
        private Button btnDecrease;
        private EditText edtQuantity;

        public BookSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            imgVBook1 = itemView.findViewById(R.id.imgVBook);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            layoutResult = itemView.findViewById(R.id.layoutResult);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            edtQuantity = itemView.findViewById(R.id.edtQuantity);

        }
    }
}
