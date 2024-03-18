package com.fatscompany.bookseftonline.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.fatscompany.bookseftonline.Entitis.PaymentHistory;

import java.util.Date;
import java.util.List;

@Dao
public interface PaymentHistoryDao {

    @Insert
    void insertPaymentHistory(PaymentHistory paymentHistory);

    @Query("SELECT * FROM payment_history WHERE user_id = :userId")
    List<PaymentHistory> getPaymentHistoryForUser(int userId);

    @Query("SELECT * FROM payment_history WHERE user_id = :userId ORDER BY payment_date ASC")
    List<PaymentHistory> getAllPaymentsSortedByDateForUser(int userId);


}
