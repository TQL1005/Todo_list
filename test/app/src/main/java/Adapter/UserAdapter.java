package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fatscompany.bookseftonline.Entitis.SaleOrder;
import com.fatscompany.bookseftonline.Entitis.User;
import com.fatscompany.bookseftonline.R;
import com.fatscompany.bookseftonline.databinding.UpdateUserBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private final iClickItemUserListener iClickItemUserListener;
    List<User> listUser;


    int mount;
    Context context;

    private iClickItemUserListener listener;

    private UpdateUserBinding updateUserBinding;

    public UserAdapter(List<User> listUser, Context context, iClickItemUserListener listener) {
        this.listUser = listUser;
        this.context = context;
        this.iClickItemUserListener = listener;
    }

    public UserAdapter(Adapter.iClickItemUserListener iClickItemUserListener, List<User> listUser, Context context, int mount) {
        this.iClickItemUserListener = iClickItemUserListener;
        this.listUser = listUser;
        this.context = context;
        this.mount = mount;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = listUser.get(position);
        if (user == null)
            return;
        holder.imgUser.setImageResource(R.drawable.baseline_account_circle_24);
        holder.txtName.setText(user.getLastName() + " " +user.getFirstName());
        holder.userRole.setText((user.getUserRole()));

        holder.itemUserLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemUserListener.onClickItemUser(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listUser != null)
            return listUser.size();
        return 0;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUser;
        private TextView txtName;
        private TextView userRole;
        private ConstraintLayout itemUserLayout;



        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.imgUser);
            txtName = itemView.findViewById(R.id.txtName);
            userRole = itemView.findViewById(R.id.txtUserRole);
            itemUserLayout = itemView.findViewById(R.id.item_user_layout);
            //userCardView = itemView.findViewById(R.id.itemUser);
        }
    }


}

