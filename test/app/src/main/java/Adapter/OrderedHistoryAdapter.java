package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatscompany.bookseftonline.AppDatabase;
import com.fatscompany.bookseftonline.DateTypeConverter;
import com.fatscompany.bookseftonline.Entitis.Book;
import com.fatscompany.bookseftonline.Entitis.PaymentHistory;
import com.fatscompany.bookseftonline.R;
import com.fatscompany.bookseftonline.UserSessionManager;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

public class OrderedHistoryAdapter extends RecyclerView.Adapter<OrderedHistoryAdapter.OrderedHistoryViewHolder> {

    private Context context;
    private List<PaymentHistory> paymentHistoryList;
    AppDatabase database;

    public OrderedHistoryAdapter(Context context, List<PaymentHistory> paymentHistoryList) {
        this.context = context;
        this.paymentHistoryList = paymentHistoryList;
    }

    @NonNull
    @Override
    public OrderedHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history_payment, parent, false);
        return new OrderedHistoryViewHolder(view);
    }

    @NonNull


    @Override
    public void onBindViewHolder(@NonNull OrderedHistoryViewHolder holder, int position) {
        PaymentHistory paymentHistory = paymentHistoryList.get(position);
        UserSessionManager sessionManager = new UserSessionManager(context);
        int currentUser = sessionManager.getUserId();
        database = AppDatabase.getInstance(context);

        Date selectedDate = paymentHistory.getPaymentDate();
        String day = DateTypeConverter.fromDate(selectedDate);
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        String formattedTotalAmount = decimalFormat.format(paymentHistory.getTotalAmount());
        holder.txtOrderDate.setText("Order Date: " + day);
        holder.txtTotalAmount.setText(String.format("Total Amount: " + formattedTotalAmount));
    }

    @Override
    public int getItemCount() {
        return paymentHistoryList.size();
    }

    public class OrderedHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderDate, txtTotalAmount;

        public OrderedHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtOrderDate = itemView.findViewById(R.id.txtOrderDate);
            txtTotalAmount = itemView.findViewById(R.id.txtTotalAmount);
        }
    }
}
