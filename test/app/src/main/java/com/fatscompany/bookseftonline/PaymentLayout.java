package com.fatscompany.bookseftonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fatscompany.bookseftonline.Entitis.PaymentHistory;
import com.fatscompany.bookseftonline.databinding.ActivityItemPaymentHistoryBinding;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Adapter.OrderedHistoryAdapter;

public class PaymentLayout extends AppCompatActivity {
    List<PaymentHistory> paymentHistoryList;
    private ActivityItemPaymentHistoryBinding binding;
    OrderedHistoryAdapter orderedHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemPaymentHistoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        RecyclerView rcv = binding.rcvPaymentHistory;

        LinearLayoutManager layoutManager = new LinearLayoutManager(PaymentLayout.this, LinearLayoutManager.VERTICAL, false);
        UserSessionManager sessionManager = new UserSessionManager(PaymentLayout.this);
        int currentUser = sessionManager.getUserId();
        rcv.setLayoutManager(layoutManager);
        AppDatabase database = AppDatabase.getInstance(this);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                paymentHistoryList = database.paymentHistoryDao().getAllPaymentsSortedByDateForUser(currentUser);
                Log.d("Adapter", "Size: " + paymentHistoryList.size());
                orderedHistoryAdapter = new OrderedHistoryAdapter(PaymentLayout.this, paymentHistoryList);
                rcv.setAdapter(orderedHistoryAdapter);
            }
        });

    }
}