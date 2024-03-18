package com.fatscompany.bookseftonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fatscompany.bookseftonline.DAO.PaymentHistoryDao;
import com.fatscompany.bookseftonline.Entitis.Book;
import com.fatscompany.bookseftonline.Entitis.OrderDetail;
import com.fatscompany.bookseftonline.Entitis.PaymentHistory;
import com.fatscompany.bookseftonline.Entitis.SaleOrder;
import com.fatscompany.bookseftonline.databinding.ActivityCartLayoutBinding;
import com.fatscompany.bookseftonline.databinding.ActivityCategoryBookResultBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Adapter.OrderedBookAdapter;


public class CartLayout extends AppCompatActivity {
    private ActivityCartLayoutBinding binding;
    private RecyclerView rcvCart;
    private List<OrderDetail> orderDetailList;
    private OrderedBookAdapter orderedBookAdapter;
    private UserSessionManager sessionManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartLayoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        rcvCart = findViewById(R.id.rcvCart);
        UserSessionManager sessionManager = new UserSessionManager(this);
        int currentUser = sessionManager.getUserId();
        AppDatabase database = AppDatabase.getInstance(CartLayout.this);
        LinearLayoutManager cartLayoutManager = new LinearLayoutManager(CartLayout.this, LinearLayoutManager.VERTICAL, false);
        rcvCart.setLayoutManager(cartLayoutManager);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {


                UserSessionManager sessionManager = new UserSessionManager(CartLayout.this);
                int userCurrent = sessionManager.getUserId();
                List<SaleOrder> saleOrders = database.saleOrderDao().getAllSaleOrderByUserId(userCurrent);

                List<OrderDetail> orderDetailList = new ArrayList<>(); // Create a list to store order details

                // Duyệt qua từng đơn hàng và lấy các chi tiết đơn hàng liên quan
                for (SaleOrder saleOrder : saleOrders) {
                    List<OrderDetail> orderDetails = database.orderDetailDao().getAllOrderDetailBySaleOrderId(saleOrder.getId());
                    orderDetailList.addAll(orderDetails);
                }

                CartLayout.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        orderedBookAdapter = new OrderedBookAdapter(CartLayout.this, orderDetailList);
                        rcvCart.setAdapter(orderedBookAdapter);


                    }
                });
            }
        });
        binding.btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Integer> listCheckBuy = orderedBookAdapter.getSelectedOrderDetailIds();
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        for (int checkBuyOfOrder : listCheckBuy) {
                            OrderDetail orderDetailCheckBuy = database.orderDetailDao().getOrderDetailById(checkBuyOfOrder);
                            Book book = database.bookDao().findBookByID(orderDetailCheckBuy.getBookId());

                            double total = orderDetailCheckBuy.getAmount() * book.getPrice();
                            Date currentDate = DateTypeConverter.toDate(new Date().getTime());


                            PaymentHistory paymentHistory = new PaymentHistory(currentUser, total, orderDetailCheckBuy.getId(), currentDate);
                            database.paymentHistoryDao().insertPaymentHistory(paymentHistory);
                            if (paymentHistory != null) {
                                database.orderDetailDao().deleteOrderDetail(orderDetailCheckBuy);

                            }

                        }
                        CartLayout.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            rcvCart.getAdapter().notifyDataSetChanged();
                            }
                        });
                    }
                });

            }
        });
        binding.btnPaymentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartLayout.this, PaymentLayout.class);
                startActivity(i);
            }
        });
    }


}