package com.example.dt170g_kitchen;

/*

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter();
        recyclerView.setAdapter(orderAdapter);

        // Initialize Retrofit10.82.242.206
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.82.242.206:8080/projektDT170G-1.0-SNAPSHOT/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Fetch orders from API
        fetchOrders();
    }

    private void fetchOrders() {
        Call<List<OrderDTO>> call = apiService.getOrders();
        call.enqueue(new Callback<List<OrderDTO>>() {
            @Override
            public void onResponse(Call<List<OrderDTO>> call, Response<List<OrderDTO>> response) {
                if (response.isSuccessful()) {
                    List<OrderDTO> orders = response.body();
                    orderAdapter.setOrders(orders);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch orders", Toast.LENGTH_SHORT).show();
                    Log.e("MainActivity", "Failed to fetch orders");
                }
            }

            @Override
            public void onFailure(Call<List<OrderDTO>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "Error: " + t.getMessage());
            }
        });
    }
}

*/


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements OrderAdapter.OrderUpdateListener {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private ApiService apiService;
    private List<OrderDTO> orders = new ArrayList<>();
    private Handler handler = new Handler();
    private final int INTERVAL = 10000; // Interval in milliseconds (10 seconds)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(this); // Pass 'this' as the listener
        recyclerView.setAdapter(orderAdapter);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.82.242.206:8080/projektDT170G-1.0-SNAPSHOT/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Fetch orders from API
        fetchOrders();
        // Schedule fetching orders every 10 seconds
        handler.postDelayed(fetchOrdersRunnable, INTERVAL);
    }
    // Runnable to fetch orders from API
    private Runnable fetchOrdersRunnable = new Runnable() {
        @Override
        public void run() {
            fetchOrders();
            // Schedule the next execution after INTERVAL milliseconds
            handler.postDelayed(this, INTERVAL);
        }
    };

    private void fetchOrders() {
        Call<List<OrderDTO>> call = apiService.getOrders();
        call.enqueue(new Callback<List<OrderDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<OrderDTO>> call, @NonNull Response<List<OrderDTO>> response) {
                if (response.isSuccessful()) {
                    orders = response.body();
                    orderAdapter.setOrders(orders);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch orders", Toast.LENGTH_SHORT).show();
                    Log.e("MainActivity", "Failed to fetch orders");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<OrderDTO>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "Error: " + t.getMessage());
            }
        });
    }
    @Override
    protected void onDestroy() {
        //super.onDestroy();
        // Remove any pending fetch requests when the activity is destroyed
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void sendUpdatedOrder(OrderDTO order) {
        // Handle API call to update the order
        // Call the API service method to update the order
        Call<Void> call = apiService.updateOrder(order.getOrder_ID(), order);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                fetchOrders();
                if (response.isSuccessful()) {
                  // fetchOrders();
                    //List<OrderDTO> orders = response.body();
                  //  orderAdapter.setOrders(orders);
                    // Handle successful response
                    // For example, show a toast message
                   for (int i = 0; i < orders.size(); i++) {
                        if (orders.get(i).getOrder_ID() == order.getOrder_ID()) {
                            orders.set(i, order); // Update the order in the list
                            Log.d("Order Update", "Updated order position: " + i);
                            orderAdapter.notifyItemChanged(i); // Notify adapter of the change
                            Log.d("Order Update", "Notify item changed position: " + i);
                            break;
                        }
                    }
                    Toast.makeText(MainActivity.this, "Order updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle unsuccessful response
                    // For example, show an error message
                    Toast.makeText(MainActivity.this, "Failed to update order", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

                // Handle failure
                // For example, show an error message
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "Error updating order: " + t.getMessage());
            }
        });
    }
}
