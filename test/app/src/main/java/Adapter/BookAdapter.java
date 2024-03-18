package Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fatscompany.bookseftonline.AppDatabase;
import com.fatscompany.bookseftonline.Entitis.Book;
import com.fatscompany.bookseftonline.Entitis.User;
import com.fatscompany.bookseftonline.Entitis.OrderDetail;
import com.fatscompany.bookseftonline.Entitis.SaleOrder;
import com.fatscompany.bookseftonline.R;
import com.fatscompany.bookseftonline.UserSessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    List<Book> myListBook;
    Context context;
    public iClickItemBookListener itemOnClickListener;

    public BookAdapter(Context context, List<Book> myListBook) {
        this.context = context;
        this.myListBook = myListBook;
    }
    /// định nghĩa những view đã khai báo trong item


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book myBook = myListBook.get(position);
        if (myBook == null)
            return;


        Glide.with(holder.itemView.getContext())
                .load(myBook.getImage())
                .placeholder(R.drawable.favorite) // Tùy chọn: Ảnh tạm thời trong quá trình tải
                .error(R.drawable.logo) // Tùy chọn: Ảnh hiển thị khi xảy ra lỗi tải
                .into(holder.imgVBook1);
        holder.itemBookLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheetDetail(myBook);
            }
        });

    }

    private void openBottomSheetDetail(Book book) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.book_detail, null);
        ImageView imageView = view.findViewById(R.id.imgBookDetail);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtAuthors = view.findViewById(R.id.txtAuthors);
        TextView txtCateBook = view.findViewById(R.id.txtCateBook);
        TextView txtPrice = view.findViewById(R.id.txtPrice);
        TextView txtDescription = view.findViewById(R.id.txtDescription);
        Button buttonAddtoCart = view.findViewById(R.id.btnAddCart);
        // Gán dữ liệu của sách vào các view trong bottom sheet
        txtTitle.setText(book.getTitle());
        txtAuthors.setText(book.getAuthors());

        Glide.with(context)
                .load(book.getImage())
                .placeholder(R.drawable.favorite)
                .error(R.drawable.logo)
                .into(imageView);
        txtDescription.setText(book.getDescription());
        double price = book.getPrice();

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String formattedPrice = decimalFormat.format(price);

        txtPrice.setText("Price: $" + formattedPrice);
        buttonAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase database = AppDatabase.getInstance(context);
                UserSessionManager sessionManager = new UserSessionManager(context);
                String savedUsername = sessionManager.getUsername();
                String savedEmail = sessionManager.getEmail();
                int savedUserId = sessionManager.getUserId();
                Date currentDate = new Date();
//                String quantityText = holder.edtQuantity.getText().toString();
//                int quantity = Integer.parseInt(quantityText);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String formattedDate = dateFormat.format(currentDate); // Chuyển Date thành chuỗi theo định dạng
                String nameOrderDeltail = "Order " + formattedDate;
                String saleOrderName = "saleOrderName" + savedUsername;
                SaleOrder saleOrder = new SaleOrder(currentDate, savedUserId, saleOrderName);

                Executor executor = Executors.newSingleThreadExecutor();

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        database.saleOrderDao().insertSaleOrder(saleOrder);
                        SaleOrder saleOrderSave = database.saleOrderDao().getSaleOrder(saleOrder.getName());
                        OrderDetail orderDetail = new OrderDetail(nameOrderDeltail, book.getId(), saleOrderSave.getId(), 1);

                        database.orderDetailDao().insertOrderDetail(orderDetail);

                        if (!((Activity) context).isFinishing()) {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    View successView = LayoutInflater.from(context).inflate(R.layout.add_cart_success, null);
                                    builder.setView(successView);
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            alertDialog.dismiss();
                                        }
                                    }, 3000); // Dismiss after 3 seconds (adjust the time as needed)
                                }
                            });
                        }


                    }
                });

            }
        });
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }


    @Override
    public int getItemCount() {
        if (myListBook != null)
            return myListBook.size();
        return 0;
    }


    public void setOnClick(iClickItemBookListener ic) {
        this.itemOnClickListener = ic;
    }


    class BookViewHolder extends RecyclerView.ViewHolder {
        //// trong này thì sẽ ánh xạ view
        private ImageView imgVBook1; /// khai báo cái này để thằng viewHolder biết để show cái quần què gì ra rồi dùng nó ánh xạ vào cái viewholder
        private TextView txtTitle;
        private TextView txtAuthor;
        private CardView itemBookLayout;


        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            itemBookLayout = itemView.findViewById(R.id.itemBookLayout);
            imgVBook1 = itemView.findViewById(R.id.imgVBook);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
        }
    }
}
