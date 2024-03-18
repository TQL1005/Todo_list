package Adapter;

import android.app.Activity;
import android.app.AlertDialog;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fatscompany.bookseftonline.AppDatabase;
import com.fatscompany.bookseftonline.Entitis.Book;
import com.fatscompany.bookseftonline.Entitis.OrderDetail;
import com.fatscompany.bookseftonline.Entitis.SaleOrder;
import com.fatscompany.bookseftonline.R;
import com.fatscompany.bookseftonline.UserSessionManager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.os.Handler;


public class CategoryBookResult extends RecyclerView.Adapter<CategoryBookResult.CategoryBookResultViewHolder> {
    private List<Book> myListBook;
    private Context context;
    private ItemOnClickListener itemOnClickListener;
    AppDatabase database;

    public CategoryBookResult(Context context, List<Book> myListBook) {
        this.context = context;
        this.myListBook = myListBook;
        database = AppDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public CategoryBookResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book_category_result, parent, false);
        return new CategoryBookResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryBookResultViewHolder holder, int position) {
        Book myBook = myListBook.get(position);
        if (myBook == null)
            return;

        holder.txtTitle.setText(myBook.getTitle());
        holder.txtAuthor.setText(myBook.getAuthors());

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String formattedPrice = decimalFormat.format(myBook.getPrice());
        holder.txtPrice.setText("Price: $" + formattedPrice);

        Glide.with(holder.itemView.getContext())
                .load(myBook.getImage())
                .into(holder.imgVBook);

        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = myBook.getCurrentQuantity();
                if (currentQuantity > 1) {
                    currentQuantity--;
                    myBook.setCurrentQuantity(currentQuantity);
                    holder.edtQuantity.setText(String.valueOf(currentQuantity));
                }
            }
        });

        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = myBook.getCurrentQuantity();
                currentQuantity++;
                myBook.setCurrentQuantity(currentQuantity);
                holder.edtQuantity.setText(String.valueOf(currentQuantity));
            }
        });


        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSessionManager sessionManager = new UserSessionManager(context);
                String savedUsername = sessionManager.getUsername();
                String savedEmail = sessionManager.getEmail();
                int savedUserId = sessionManager.getUserId();
                Date currentDate = new Date();
                String quantityText = holder.edtQuantity.getText().toString();
                int quantity = Integer.parseInt(quantityText);
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
                        OrderDetail orderDetail = new OrderDetail(nameOrderDeltail, myBook.getId(), saleOrderSave.getId(), quantity);

                        database.orderDetailDao().insertOrderDetail(orderDetail);

                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Inflate the layout for the "Add to Cart" success message
                                View successView = LayoutInflater.from(context).inflate(R.layout.add_cart_success, null);

                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setView(successView);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                                Handler handler = new Handler(context.getMainLooper());
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialog.dismiss();
                                    }
                                }, 3000); // Dismiss after 3 seconds (adjust the time as needed)

                            }
                        });
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        if (myListBook != null)
            return myListBook.size();
        return 0;
    }

    public interface ItemOnClickListener {
        void onAddToCartClicked(Book book);
    }

    public class CategoryBookResultViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgVBook;
        private TextView txtTitle;
        private TextView txtAuthor;
        private TextView txtPrice;
        private Button btnDecrease;
        private EditText edtQuantity;
        private Button btnIncrease;
        private Button btnAddToCart;

        public CategoryBookResultViewHolder(@NonNull View itemView) {
            super(itemView);
            imgVBook = itemView.findViewById(R.id.imgVBook);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            edtQuantity = itemView.findViewById(R.id.edtQuantity);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
