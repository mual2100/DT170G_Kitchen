package com.example.dt170g_kitchen;



import android.view.LayoutInflater;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        private CheckBox checkBoxAppetizerStatus;
        private CheckBox checkBoxMainStatus;
        private CheckBox checkBoxDessertStatus;
        private Button buttonSendOrder;
        private RecyclerView recyclerViewAppetizers;
        private RecyclerView recyclerViewMainCourse;
        private RecyclerView recyclerViewDessert;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTableNumber = itemView.findViewById(R.id.textViewTableNumber);
          //  textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewComment = itemView.findViewById(R.id.textViewComment);
            checkBoxAppetizerStatus = itemView.findViewById(R.id.checkBoxAppetizerStatus);
            checkBoxMainStatus = itemView.findViewById(R.id.checkBoxMainStatus);
            checkBoxDessertStatus = itemView.findViewById(R.id.checkBoxDessertStatus);
            buttonSendOrder = itemView.findViewById(R.id.buttonSendOrder);
            recyclerViewAppetizers = itemView.findViewById(R.id.recyclerViewAppetizers);
            recyclerViewMainCourse = itemView.findViewById(R.id.recyclerViewMainCourse);
            recyclerViewDessert = itemView.findViewById(R.id.recyclerViewDessert);


            View.OnTouchListener checkBoxTouchListener = new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Allow RecyclerView to scroll if CheckBox is not clicked
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            };

            checkBoxAppetizerStatus.setOnTouchListener(checkBoxTouchListener);
            checkBoxMainStatus.setOnTouchListener(checkBoxTouchListener);
            checkBoxDessertStatus.setOnTouchListener(checkBoxTouchListener);

            buttonSendOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderDTO order = orders.get(getAdapterPosition());
                    order.setStatusAppetizer(checkBoxAppetizerStatus.isChecked() ? "COMPLETED" : "PENDING");
                    order.setStatusMain(checkBoxMainStatus.isChecked() ? "COMPLETED" : "PENDING");
                    order.setStatusDessert(checkBoxDessertStatus.isChecked() ? "COMPLETED" : "PENDING");
                    orderUpdateListener.sendUpdatedOrder(order);
                    //updateStatusViews(order);
                }
            });
        }

        public void bind(OrderDTO order) {
            textViewTableNumber.setText("Table Number: " + order.getRestaurantTableId());
            //textViewTime.setText("Time: " + order.getTime());
            textViewComment.setText("Comment: " + order.getComment());
            checkBoxAppetizerStatus.setChecked(order.getStatusAppetizer().equals("COMPLETED"));
            checkBoxMainStatus.setChecked(order.getStatusMain().equals("COMPLETED"));
            checkBoxDessertStatus.setChecked(order.getStatusDessert().equals("COMPLETED"));

            // Set up adapters for each food type
            Map<String, List<Food>> foodMap = groupFoodsByType(order.getFoods());
            recyclerViewAppetizers.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            recyclerViewAppetizers.setAdapter(new FoodAdapter(foodMap.get("förrätt")));

            recyclerViewMainCourse.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            recyclerViewMainCourse.setAdapter(new FoodAdapter(foodMap.get("huvudrätt")));

            recyclerViewDessert.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            recyclerViewDessert.setAdapter(new FoodAdapter(foodMap.get("efterrätt")));
        }
        private Map<String, List<Food>> groupFoodsByType(List<Food> foods) {
            Map<String, List<Food>> foodMap = new HashMap<>();
            for (Food food : foods) {
                if (!foodMap.containsKey(food.getType())) {
                    foodMap.put(food.getType(), new ArrayList<>());
                }
                foodMap.get(food.getType()).add(food);
            }
            return foodMap;
        }

        public void updateStatusViews(OrderDTO order) {
            if (order.getStatusAppetizer().equals("COMPLETED")) {
                checkBoxAppetizerStatus.setVisibility(View.GONE);
                checkBoxAppetizerStatus.getParent().requestLayout();
                recyclerViewAppetizers.setVisibility(View.GONE);
                //checkBoxAppetizerStatus.setEnabled(false);


            } else {
                checkBoxAppetizerStatus.setVisibility(View.VISIBLE);
                recyclerViewAppetizers.setVisibility(View.VISIBLE);
                //checkBoxAppetizerStatus.setEnabled(true);
            }

            if (order.getStatusMain().equals("COMPLETED")) {
                checkBoxMainStatus.setVisibility(View.GONE);
                checkBoxMainStatus.getParent().requestLayout();
            } else {
                checkBoxMainStatus.setVisibility(View.VISIBLE);
            }

            if (order.getStatusDessert().equals("COMPLETED")) {
                checkBoxDessertStatus.setVisibility(View.GONE);
                checkBoxDessertStatus.getParent().requestLayout();
            } else {
                checkBoxDessertStatus.setVisibility(View.VISIBLE);
            }
        }
    }

    private class FoodAdapter extends RecyclerView.Adapter<OrderAdapter.FoodAdapter.FoodViewHolder> {
            private List<Food> foods;

            public FoodAdapter(List<Food> foods) {
                this.foods = foods;
            }

            @NonNull
        @Override
        public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
            return new FoodViewHolder(itemView);
        }

      @Override
        public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
            Food food = foods.get(position);
            holder.bind(food);

        }

        @Override
        public int getItemCount() {
            return foods != null ? foods.size() : 0;
        }

        /*@Override
        public int getItemCount() {
            return foods.size();
        }*/

        public class FoodViewHolder extends RecyclerView.ViewHolder {
            private TextView textViewFoodName;
            private TextView textViewFoodDescription;
            private TextView textViewFoodPrice;

            public FoodViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewFoodName = itemView.findViewById(R.id.textViewFoodName);
                textViewFoodDescription = itemView.findViewById(R.id.textViewFoodDescription);
                //textViewFoodPrice = itemView.findViewById(R.id.textViewFoodPrice);
            }

            public void bind(Food food) {
                textViewFoodName.setText(food.getName());
                textViewFoodDescription.setText(food.getDescription());
                //textViewFoodPrice.setText(String.valueOf(food.getPrice()));
            }
        }

    }

}