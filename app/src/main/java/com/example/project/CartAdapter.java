package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItemList;
    private OnItemRemovedListener onItemRemovedListener;

    public CartAdapter(List<CartItem> cartItemList, OnItemRemovedListener onItemRemovedListener) {
        this.cartItemList = cartItemList;
        this.onItemRemovedListener = onItemRemovedListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItemList.get(position);
        holder.productNameTextView.setText(item.getName());
        holder.productPriceTextView.setText(String.format("Price: ₹%.2f", item.getPrice()));
        holder.quantityTextView.setText(String.format("Quantity: %d", item.getQuantity()));
        holder.totalPriceTextView.setText(String.format("Total: ₹%.2f", item.getPrice() * item.getQuantity()));

        holder.removeButton.setOnClickListener(v -> {
            cartItemList.remove(position);
            notifyItemRemoved(position);
            if (onItemRemovedListener != null) {
                onItemRemovedListener.onItemRemoved();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public interface OnItemRemovedListener {
        void onItemRemoved();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView productPriceTextView;
        TextView quantityTextView;
        TextView totalPriceTextView;
        Button removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }
}
