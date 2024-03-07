package com.example.dt170g_kitchen;

import android.animation.ValueAnimator;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
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
    private static final int PERMISSION_REQUEST_CODE = 100;

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
                    // Process food items and organize them based on statuses
                    processFoodItems();
                    orderAdapter.setOrders(orders);
                    showNotification();
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
    private void onNewOrderReceived() {
        // Show a notification to notify the user about the new order
        showNotification();
    }
    // Process food items and remove completed items
    private void processFoodItems() {
        for (OrderDTO order : orders) {
            order.removeCompletedFoods();
        }
    }

    @Override
    protected void onDestroy() {
        // Remove any pending fetch requests when the activity is destroyed
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void sendUpdatedOrder(OrderDTO order) {
        // Handle API call to update the order
        Call<Void> call = apiService.updateOrder(order.getOrder_ID(), order);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                fetchOrders();
                if (response.isSuccessful()) {
                    // Handle successful response
                    Toast.makeText(MainActivity.this, "Order updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(MainActivity.this, "Failed to update order", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                // Handle failure
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "Error updating order: " + t.getMessage());
            }
        });
    }
    // Inside your MainActivity class

    // 1. Define Notification Channel
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.channel_id), name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

// 2. Create Notification Layout (No code here, just create notification_layout.xml)

    // 3. Animate Notification Background (Optional)
    private void animateNotificationBackground(RemoteViews notificationLayout) {
        int initialColor = ContextCompat.getColor(this, R.color.initial_color);
        int flashColor = ContextCompat.getColor(this, R.color.flash_color);
        int[] colors = new int[]{initialColor, flashColor};
        ValueAnimator colorAnimator = ValueAnimator.ofArgb(colors);
        colorAnimator.setDuration(500);
        colorAnimator.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimator.setRepeatMode(ValueAnimator.REVERSE);

        colorAnimator.addUpdateListener(animation -> {
            int animatedColor = (int) animation.getAnimatedValue();
            notificationLayout.setInt(R.id.notification_background, "setBackgroundColor", animatedColor);
        });

        colorAnimator.start();
    }
    private static final int NOTIFICATION_ID = 1;

    // 4. Build and Show Notification
    private void showNotification() {
        // Inflate custom layout for notification
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_layout);

        // Animate notification background (if desired)
        animateNotificationBackground(notificationLayout);

        // Build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setSmallIcon(R.drawable.notification_icon)
                .setContent(notificationLayout)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        notificationManager.notify(1, builder.build());
    }

}