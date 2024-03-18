package Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fatscompany.bookseftonline.AppDatabase;
import com.fatscompany.bookseftonline.Entitis.Book;
import com.fatscompany.bookseftonline.Entitis.SaleOrder;
import com.fatscompany.bookseftonline.R;

import com.fatscompany.bookseftonline.Entitis.OrderDetail;
import com.fatscompany.bookseftonline.UserSessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class OrderedBookAdapter extends RecyclerView.Adapter<OrderedBookAdapter.OrderedBookViewHolder> {

    private Context context;
    private List<OrderDetail> orderDetailList;
    AppDatabase database;
    UserSessionManager sessionManager;
    public List<Integer> selectedOrderDetailIds = new ArrayList<>();

    public List<Integer> getSelectedOrderDetailIds() {
        return selectedOrderDetailIds;
    }

    public OrderedBookAdapter(Context context, List<OrderDetail> orderDetailList) {
        this.context = context;
        this.orderDetailList = orderDetailList;
        database = AppDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public OrderedBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book_cart, parent, false);
        return new OrderedBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderedBookViewHolder holder, int position) {
        Executor executor = Executors.newSingleThreadExecutor();
        sessionManager = new UserSessionManager(context);
        int userCurrent = sessionManager.getUserId();
        OrderDetail orderDetail = orderDetailList.get(position);
        int currentQuantity = orderDetail.getAmount();
        final int[] newQuantity = {currentQuantity + 1};

        executor.execute(new Runnable() {
            @Override
            public void run() {
                SaleOrder saleOrder = database.saleOrderDao().findSaleOrderById(orderDetail.getSaleOrderId());
                Book book = database.bookDao().findBookByID(orderDetail.getBookId());

                if (userCurrent == saleOrder.getUserId()) {
                    holder.itemView.post(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(holder.itemView.getContext())
                                    .load(book.getImage())
                                    .into(holder.imgOrderedBook);
                            holder.txtOrderedBookTitle.setText(book.getTitle());
                            holder.txtOrderedAuthorBook.setText(book.getAuthors());
                            holder.txtOrderedBookPrice.setText(String.format("%.2f", book.getPrice()));
                            holder.edtQuantity.setText(String.valueOf(orderDetail.getAmount()));
                        }
                    });
                }
                holder.checkBuy.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        holder.addSelectedOrderDetailId(orderDetail.getId());
                    } else {
                        holder.removeSelectedOrderDetailId(orderDetail.getId());
                    }
                });


            }
        });
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        database.orderDetailDao().deleteOrderDetail(orderDetail);


                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                });
            }
        });

        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = orderDetail.getAmount();
                final int[] newQuantity = {currentQuantity + 1}; // Tăng lên 1

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        orderDetail.setAmount(newQuantity[0]);
                        database.orderDetailDao().updateOrderDetail(orderDetail);

                        holder.itemView.post(new Runnable() {
                            @Override
                            public void run() {
                                holder.edtQuantity.setText(String.valueOf(newQuantity[0]));
                            }
                        });
                    }
                });
            }
        });

        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = orderDetail.getAmount();
                final int[] newQuantity = {currentQuantity}; // Khởi tạo với giá trị ban đầu

                if (currentQuantity > 1) {
                    newQuantity[0] = currentQuantity - 1; // Thay đổi giá trị mới nếu điều kiện thỏa mãn
                }

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        orderDetail.setAmount(newQuantity[0]);
                        database.orderDetailDao().updateOrderDetail(orderDetail);

                        holder.itemView.post(new Runnable() {
                            @Override
                            public void run() {
                                holder.edtQuantity.setText(String.valueOf(newQuantity[0]));
                            }
                        });
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    public class OrderedBookViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderedBookTitle, txtOrderedAuthorBook, txtOrderedBookPrice;
        ImageButton btnDecrease, btnIncrease;
        EditText edtQuantity;
        Button btnRemove;
        CheckBox checkBuy;
        ImageView imgOrderedBook;
        Button btnPayment, btnPaymentHistory;
        ConstraintLayout cartContainer;

        public OrderedBookViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderedBookTitle = itemView.findViewById(R.id.txtOrderedBookTitle);
            txtOrderedAuthorBook = itemView.findViewById(R.id.txtOrderedAuthorBook);
            txtOrderedBookPrice = itemView.findViewById(R.id.txtOrderedBookPrice);
            imgOrderedBook = itemView.findViewById(R.id.imgOrderedBook);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            edtQuantity = itemView.findViewById(R.id.edtQuantity);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            checkBuy = itemView.findViewById(R.id.checkBuy);
            cartContainer = itemView.findViewById(R.id.cartContainer);

        }


        public void addSelectedOrderDetailId(int orderDetailId) {
            selectedOrderDetailIds.add(orderDetailId);
        }

        public void removeSelectedOrderDetailId(int orderDetailId) {
            selectedOrderDetailIds.remove((Integer) orderDetailId);
        }
    }
}
