package com.example.dt170g_kitchen;
/*
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.widget.Spinner;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<OrderDTO> orders = new ArrayList<>();

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    private OrderUpdateListener orderUpdateListener;

    public interface OrderUpdateListener {
        void sendUpdatedOrder(OrderDTO order);
    }

    public OrderAdapter(OrderUpdateListener listener) {
        this.orderUpdateListener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderDTO order = orders.get(position);
        holder.bind(order);
        // Disable the Spinner/Button if the order status is "COMPLETED"
        if (order.getStatusAppetizer().equals("COMPLETED")) {
            holder.spinnerAppetizerStatus.setVisibility(View.GONE);
            holder.textViewAppetizerStatus.setVisibility(View.GONE);
            //holder.spinnerAppetizerStatus.setEnabled(false);
        } else {
            holder.spinnerAppetizerStatus.setEnabled(true);
        }

        if (order.getStatusMain().equals("COMPLETED")) {
            //holder.spinnerMainStatus.setEnabled(false);
            holder.spinnerMainStatus.setVisibility(View.GONE);
            holder.textViewMainStatus.setVisibility(View.GONE);
        } else {
            holder.spinnerMainStatus.setEnabled(true);
        }

        if (order.getStatusDessert().equals("COMPLETED")) {
            //holder.spinnerDessertStatus.setEnabled(false);
            holder.spinnerDessertStatus.setVisibility(View.GONE);
            holder.textViewDessertStatus.setVisibility(View.GONE);
        } else {
            holder.spinnerDessertStatus.setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTableNumber;
        private TextView textViewTime;
        private TextView textViewComment;
        private TextView textViewAppetizerStatus;
        private TextView textViewMainStatus;
        private TextView textViewDessertStatus;
        private Spinner spinnerAppetizerStatus;
        private Spinner spinnerMainStatus;
        private Spinner spinnerDessertStatus;
        private Button buttonSendOrder;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTableNumber = itemView.findViewById(R.id.textViewTableNumber);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewComment = itemView.findViewById(R.id.textViewComment);
            textViewAppetizerStatus = itemView.findViewById(R.id.textViewAppetizerStatus);
            textViewMainStatus = itemView.findViewById(R.id.textViewMainStatus);
            textViewDessertStatus = itemView.findViewById(R.id.textViewDessertStatus);
            spinnerAppetizerStatus = itemView.findViewById(R.id.spinnerAppetizerStatus);
            spinnerMainStatus = itemView.findViewById(R.id.spinnerMainStatus);
            spinnerDessertStatus = itemView.findViewById(R.id.spinnerDessertStatus);
            buttonSendOrder = itemView.findViewById(R.id.buttonSendOrder);

            // Initialize Spinners with status options
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(itemView.getContext(), R.array.status_options, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAppetizerStatus.setAdapter(adapter);
            spinnerMainStatus.setAdapter(adapter);
            spinnerDessertStatus.setAdapter(adapter);

            // Set OnClickListener for the button
            buttonSendOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Retrieve selected statuses from Spinners
                    String appetizerStatus = spinnerAppetizerStatus.getSelectedItem().toString();
                    String mainStatus = spinnerMainStatus.getSelectedItem().toString();
                    String dessertStatus = spinnerDessertStatus.getSelectedItem().toString();

                    // Update the corresponding status in the OrderDTO object
                    OrderDTO order = orders.get(getAdapterPosition());
                    order.setStatusAppetizer(appetizerStatus);
                    order.setStatusMain(mainStatus);
                    order.setStatusDessert(dessertStatus);

                    // Send the updated order back to the API
                    orderUpdateListener.sendUpdatedOrder(order);
                    // Call a method in the adapter or pass the order to the activity to handle the API call
                    // For example: ((MainActivity)itemView.getContext()).sendUpdatedOrder(order);
                }
            });
        }

        public void bind(OrderDTO order) {
            textViewTableNumber.setText("Table Number: " + order.getRestaurantTableId());
            textViewTime.setText("Time: " + order.getTime());
            textViewComment.setText("Comment: " + order.getComment());
            textViewAppetizerStatus.setText("Status Appetizer: " + order.getStatusAppetizer());
            textViewMainStatus.setText("Status Main: " + order.getStatusMain());
            textViewDessertStatus.setText("Status Dessert: " + order.getStatusDessert());
            // Set selected status for each course
            spinnerAppetizerStatus.setSelection(getIndex(spinnerAppetizerStatus, order.getStatusAppetizer()));
            spinnerMainStatus.setSelection(getIndex(spinnerMainStatus, order.getStatusMain()));
            spinnerDessertStatus.setSelection(getIndex(spinnerDessertStatus, order.getStatusDessert()));
        }

        // Helper method to get the index of an item in the Spinner's adapter
        private int getIndex(Spinner spinner, String item) {
            ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
            return adapter.getPosition(item);
        }



    }

}*/

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.widget.Spinner;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<OrderDTO> orders = new ArrayList<>();
    private OrderUpdateListener orderUpdateListener;

    public interface OrderUpdateListener {
        void sendUpdatedOrder(OrderDTO order);
    }

    public OrderAdapter(OrderUpdateListener listener) {
        this.orderUpdateListener = listener;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderDTO order = orders.get(position);
        holder.bind(order);
        // Disable the Spinner/Button if the order status is "COMPLETED"
        holder.updateStatusViews(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTableNumber;
        private TextView textViewTime;
        private TextView textViewComment;
        private TextView textViewAppetizerStatus;
        private TextView textViewMainStatus;
        private TextView textViewDessertStatus;
        private Spinner spinnerAppetizerStatus;
        private Spinner spinnerMainStatus;
        private Spinner spinnerDessertStatus;
        private Button buttonSendOrder;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTableNumber = itemView.findViewById(R.id.textViewTableNumber);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewComment = itemView.findViewById(R.id.textViewComment);
            textViewAppetizerStatus = itemView.findViewById(R.id.textViewAppetizerStatus);
            textViewMainStatus = itemView.findViewById(R.id.textViewMainStatus);
            textViewDessertStatus = itemView.findViewById(R.id.textViewDessertStatus);
            spinnerAppetizerStatus = itemView.findViewById(R.id.spinnerAppetizerStatus);
            spinnerMainStatus = itemView.findViewById(R.id.spinnerMainStatus);
            spinnerDessertStatus = itemView.findViewById(R.id.spinnerDessertStatus);
            buttonSendOrder = itemView.findViewById(R.id.buttonSendOrder);

            // Initialize Spinners with status options
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(itemView.getContext(), R.array.status_options, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAppetizerStatus.setAdapter(adapter);
            spinnerMainStatus.setAdapter(adapter);
            spinnerDessertStatus.setAdapter(adapter);

            // Set OnClickListener for the button
          /*  buttonSendOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check if the orders list is null or empty
                    if (orders == null || orders.isEmpty()) {
                        // Handle the case where orders list is null or empty
                        return;
                    }

                    // Check if getAdapterPosition() returns a valid position
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition == RecyclerView.NO_POSITION) {
                        // Handle the case where adapter position is invalid
                        return;
                    }

                    // Retrieve selected statuses from Spinners
                    String appetizerStatus = spinnerAppetizerStatus.getSelectedItem().toString();
                    String mainStatus = spinnerMainStatus.getSelectedItem().toString();
                    String dessertStatus = spinnerDessertStatus.getSelectedItem().toString();

                    // Get the order at the adapter position
                    OrderDTO order = orders.get(adapterPosition);

                    // Check if the order is null
                    if (order == null) {
                        // Handle the case where order is null
                        return;
                    }

                    // Update the corresponding status in the OrderDTO object
                    order.setStatusAppetizer(appetizerStatus);
                    order.setStatusMain(mainStatus);
                    order.setStatusDessert(dessertStatus);

                    // Send the updated order back to the API
                    orderUpdateListener.sendUpdatedOrder(order);
                }
            });*/



            buttonSendOrder.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Retrieve selected statuses from Spinners
                    String appetizerStatus = spinnerAppetizerStatus.getSelectedItem().toString();
                    String mainStatus = spinnerMainStatus.getSelectedItem().toString();
                    String dessertStatus = spinnerDessertStatus.getSelectedItem().toString();

                    // Update the corresponding status in the OrderDTO object
                    OrderDTO order = orders.get(getAdapterPosition());
                    order.setStatusAppetizer(appetizerStatus);
                    order.setStatusMain(mainStatus);
                    order.setStatusDessert(dessertStatus);

                    // Send the updated order back to the API
                    orderUpdateListener.sendUpdatedOrder(order);
                }
            });
        }

        public void bind(OrderDTO order) {
            textViewTableNumber.setText("Table Number: " + order.getRestaurantTableId());
            textViewTime.setText("Time: " + order.getTime());
            textViewComment.setText("Comment: " + order.getComment());
            textViewAppetizerStatus.setText("Status Appetizer: " + order.getStatusAppetizer());
            textViewMainStatus.setText("Status Main: " + order.getStatusMain());
            textViewDessertStatus.setText("Status Dessert: " + order.getStatusDessert());
            // Set selected status for each course
            spinnerAppetizerStatus.setSelection(getIndex(spinnerAppetizerStatus, order.getStatusAppetizer()));
            spinnerMainStatus.setSelection(getIndex(spinnerMainStatus, order.getStatusMain()));
            spinnerDessertStatus.setSelection(getIndex(spinnerDessertStatus, order.getStatusDessert()));
        }

        // Helper method to update the status views
        public void updateStatusViews(OrderDTO order) {
            if (order.getStatusAppetizer().equals("COMPLETED")) {
                spinnerAppetizerStatus.setVisibility(View.GONE);
                textViewAppetizerStatus.setVisibility(View.GONE);
            } else {
                spinnerAppetizerStatus.setEnabled(true);
            }

            if (order.getStatusMain().equals("COMPLETED")) {
                spinnerMainStatus.setVisibility(View.GONE);
                textViewMainStatus.setVisibility(View.GONE);
            } else {
                spinnerMainStatus.setEnabled(true);
            }

            if (order.getStatusDessert().equals("COMPLETED")) {
                spinnerDessertStatus.setVisibility(View.GONE);
                textViewDessertStatus.setVisibility(View.GONE);
            } else {
                spinnerDessertStatus.setEnabled(true);
            }
        }

        // Helper method to get the index of an item in the Spinner's adapter
        private int getIndex(Spinner spinner, String item) {
            ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
            return adapter.getPosition(item);
        }
    }
}

