package com.example.dt170g_kitchen;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("order/activeOrders")
    Call<List<OrderDTO>> getOrders();
   /* @POST("order/update")
    Call<Void> updateOrder(@Body OrderDTO updatedOrder);*/
    @PUT("order/{order_ID}")
    Call<Void> updateOrder(@Path("order_ID") int order_ID, @Body OrderDTO order);
}


