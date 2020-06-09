package org.asu.cse535.recipemaker.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.asu.cse535.recipemaker.R;
import org.asu.cse535.recipemaker.adapter.CustomAdapter;
import org.asu.cse535.recipemaker.adapter.ItemClickSupport;
import org.asu.cse535.recipemaker.config.Config;
import org.asu.cse535.recipemaker.model.response.Restaurant;
import org.asu.cse535.recipemaker.model.response.SearchResult;
import org.asu.cse535.recipemaker.rest.API;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class RestaurantListActivity extends AppCompatActivity {

    private final static String TAG = RestaurantListActivity.class.getSimpleName();

    String foodItem ;

    private CustomAdapter adapter;
    private static RecyclerView recyclerView;
    ProgressDialog progressDialog;
    Location userLocation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        Intent intent = getIntent();
        foodItem = intent.getStringExtra("selectedFoodItem");
        userLocation = intent.getParcelableExtra("userLocation");

        progressDialog = new ProgressDialog(RestaurantListActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        getRestaurantList();

    }



    public void getRestaurantList(){
        //Call<SearchResult> call = API.restaurants().searchMenu(Config.RESTAURANT_API_KEY,userLocation.getLatitude(),
          //      userLocation.getLongitude(),1.0d,"{\"menu_item_description\":\""+foodItem+"\"}");

        Call<SearchResult> call = API.restaurants().searchRestaurant(Config.RESTAURANT_API_KEY,userLocation.getLatitude(),
                userLocation.getLongitude(),1.0d,foodItem);
                call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                progressDialog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Log.e(TAG, t.toString());
                progressDialog.dismiss();
                Toast.makeText(RestaurantListActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(SearchResult body) {
        List<Restaurant> restaurantList = body.getRestaurantResponseItem().getData();

        Map<Integer, Restaurant> map = new HashMap<>();

        for(Restaurant r : restaurantList){
            map.put(r.getRestaurantId(),r);
        }

        restaurantList = new ArrayList<>(map.values());

        if(restaurantList== null){
            restaurantList = new ArrayList<>();
        }
        Collections.shuffle(restaurantList);
        recyclerView = findViewById(R.id.restaurantList);

        adapter = new CustomAdapter(this,restaurantList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RestaurantListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            private View lastSelectedView = null;

            @SuppressLint("ResourceAsColor")
            public void clearSelection() {
                if(lastSelectedView != null) lastSelectedView.setBackgroundColor(android.R.color.white);
            }
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                clearSelection();
                lastSelectedView = view;
                view.setBackgroundDrawable(view.getContext().getResources().getDrawable(R.drawable.blue_color));

                Restaurant selectedRestaurant = adapter.getItem(position);
                System.out.println(selectedRestaurant.restaurantName);

                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("userLat",userLocation.getLatitude());
                intent.putExtra("userLon",userLocation.getLongitude());
                intent.putExtra("restaurantLat",selectedRestaurant.getGeo().getLat());
                intent.putExtra("restaurantLon",selectedRestaurant.getGeo().getLon());
                intent.putExtra("title",selectedRestaurant.getRestaurantName());
                startActivity(intent);
            }
        });
    }

    public void onClickHomeRestaurant(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void onClickBackRestaurant(View view){
        finish();
    }

}


