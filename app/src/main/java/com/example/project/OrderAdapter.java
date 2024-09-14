package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.bookNameTextView.setText(order.getBookName());
        holder.orderDateTextView.setText(order.getOrderDate());
        holder.priceTextView.setText(String.format("₹%.2f", order.getPrice()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView bookNameTextView;
        TextView orderDateTextView;
        TextView priceTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            bookNameTextView = itemView.findViewById(R.id.bookNameTextView);
            orderDateTextView = itemView.findViewById(R.id.orderDateTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
        }
    }
}
