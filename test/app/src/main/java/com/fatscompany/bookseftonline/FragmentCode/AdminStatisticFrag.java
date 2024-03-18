package com.fatscompany.bookseftonline.FragmentCode;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatscompany.bookseftonline.AdminActivity;
import com.fatscompany.bookseftonline.AppDatabase;
import com.fatscompany.bookseftonline.Entitis.Book;
import com.fatscompany.bookseftonline.Entitis.OrderDetail;
import com.fatscompany.bookseftonline.Entitis.SaleOrder;
import com.fatscompany.bookseftonline.Entitis.User;
import com.fatscompany.bookseftonline.Entitis.UserData;
import com.fatscompany.bookseftonline.R;
import com.fatscompany.bookseftonline.databinding.FragmentAdminStatisticBinding;
import com.fatscompany.bookseftonline.databinding.UserStatisicLayoutBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Adapter.UserStatisticAdapter;


public class AdminStatisticFrag extends Fragment {

    //private DatabaseAdapter db;
    private FragmentAdminStatisticBinding binding;
    private List<User> userList;

    private List<UserData> userDataList;

    private UserStatisticAdapter statisticAdapter;

    private UserStatisicLayoutBinding userStatisicBinding;


    List<Book> bookList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AdminActivity adminActivity = (AdminActivity) getActivity();
        binding = FragmentAdminStatisticBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerView rcv = view.findViewById(R.id.rcvUserStatistic);
        rcv.setLayoutManager(linearLayoutManager);

        AppDatabase database = AppDatabase.getInstance(getActivity());
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.runInTransaction(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            PieChart pieChart = binding.chartBestSaler;
                            ArrayList<PieEntry> pieEntries = new ArrayList<>();
                            userList = database.userDao().selectAll();
                            bookList = database.bookDao().getAllBook();
                            List<OrderDetail> orderDetails = null;
                            //List<OrderDetail> orderDetails = database.orderDetailDao().getAllOrderDetailByBookId();
                            for (Book book : bookList){
                                int i  = database.orderDetailDao().getTotalSoldAmountByBookId(book.getId());
                                String s = book.getTitle();
                                if(i > 0){
                                    pieEntries.add(new PieEntry((float) i, s));
                                }
                            }
                            PieDataSet dataSet = new PieDataSet(pieEntries, "Best Sellers");

                            dataSet.setValueTextSize(12f);
                            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            dataSet.setValueTextColor(Color.BLACK);
                            PieData pieData = new PieData(dataSet);

                            pieChart.getDescription().setEnabled(false);
                            pieChart.setData(pieData);
                            pieChart.invalidate(); // Refresh the chart
                            pieChart.animate();

                            if(userList==null){
                                return;
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    statisticAdapter = new UserStatisticAdapter(getContext(), userList);
                                    rcv.setAdapter(statisticAdapter);


                                }
                            });

                        }catch (Exception ex){

                        }
                    }
                });
            }
        });
        return view;
    }
}