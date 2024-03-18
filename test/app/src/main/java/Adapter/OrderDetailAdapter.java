package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fatscompany.bookseftonline.AppDatabase;
import com.fatscompany.bookseftonline.Entitis.OrderDetail;
import com.fatscompany.bookseftonline.Entitis.SaleOrder;
import com.fatscompany.bookseftonline.Entitis.User;
import com.fatscompany.bookseftonline.R;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {

    User user;
    Context context;

    AppDatabase db;

    public OrderDetailAdapter(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    @NonNull
    @Override
    public OrderDetailAdapter.OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_adminbook, parent, false);
        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.OrderDetailViewHolder holder, int position) {
        if (user == null){
            return;
        }
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db = AppDatabase.getInstance(context);
                db.runInTransaction(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            List<SaleOrder> saleOrdersList = db.saleOrderDao().getAllSaleOrderByUserId(user.getId());
                            for (SaleOrder saleOrder : saleOrdersList) {
                                List<OrderDetail> orderDetails = db.orderDetailDao().getAllOrderDetailBySaleOrderId(saleOrder.getId());
                                for (OrderDetail orderDetail : orderDetails) {
                                    if (orderDetail.getAmount() == 0){
                                        break;
                                    }

                                }

                            }
                            /*holder.itemView.post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });*/
                        } catch (Exception ex) {

                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {

        return 0;
    }

    public class OrderDetailViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout booktLayout;
        private TextView txtBookName;
        private TextView txtBookPrice;


        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBookName = itemView.findViewById(R.id.adminBookName);
            txtBookPrice = itemView.findViewById(R.id.adminPriceText);
        }


    }
}
