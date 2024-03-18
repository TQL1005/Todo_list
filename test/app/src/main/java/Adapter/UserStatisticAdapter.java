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

public class UserStatisticAdapter extends RecyclerView.Adapter<UserStatisticAdapter.UserStatisticViewHolder> {

    //atabaseController db;


    List<User> userList;

    Context context;
    List<OrderDetail> orderDetails;
    List<SaleOrder> saleOrdersList;
    int total = 0;

    AppDatabase db;

    public UserStatisticAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserStatisticAdapter.UserStatisticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_statisic_layout, parent, false);
        return new UserStatisticViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserStatisticAdapter.UserStatisticViewHolder holder, int position) {
        User user = userList.get(position);

        if(user == null){
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
                            saleOrdersList = db.saleOrderDao().getAllSaleOrderByUserId(user.getId());
                            for (SaleOrder saleOrder : saleOrdersList) {
                                List<OrderDetail> orderDetails = db.orderDetailDao().getAllOrderDetailBySaleOrderId(saleOrder.getId());
                                for (OrderDetail orderDetail : orderDetails) {
                                    if (orderDetail.getAmount() == 0){
                                        break;
                                    }
                                    total += orderDetail.getAmount();
                                    Log.d("thanhcong", String.valueOf(total));
                                }
                                Log.d("thanhcongluu", String.valueOf(total));
                            }
                            Log.d("thanhcongluu1", String.valueOf(total));
                            holder.txtFullName.setText(user.getFirstName() + " " +user.getLastName());
                            holder.txtTotalOrders.setText("Orders: " + String.valueOf(total));
                            total = 0;
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
        //holder.txtTotalOrders.setText((String.valueOf(total)));

        //holder.txtTotalOrders.setText((String.valueOf(total)));
    }

    @Override
    public int getItemCount() {
        if (userList != null)
            return userList.size();
        return 0;
    }

    public class UserStatisticViewHolder extends RecyclerView.ViewHolder {

        private TextView txtFullName;

        private TextView txtTotalOrders;

        //private LineChart lineChart;

        private ConstraintLayout userStatictisLayout;

        public UserStatisticViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFullName = itemView.findViewById(R.id.txtFullName);
            txtTotalOrders = itemView.findViewById(R.id.txtTotalOrders);
            //lineChart = itemView.findViewById(R.id.chartUser);
            userStatictisLayout = itemView.findViewById(R.id.userStatisticLayout);
        }
    }
}
