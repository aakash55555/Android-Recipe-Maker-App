package org.asu.cse535.recipemaker.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.asu.cse535.recipemaker.R;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class FoodListActivity extends AppCompatActivity {

     ArrayList<String> foodList = null;

    LocationManager locationManager;
    LocationListener locationListener;
    Location userLocation;

    ListView foodListView ;
    String selectedFoodItem;

    ProgressDialog progressDialog;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        progressDialog = new ProgressDialog(FoodListActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        initializeUserLocation();

        foodListView = findViewById(R.id.foodList);

        foodList = (ArrayList<String>) getIntent().getSerializableExtra("foodList");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, foodList);

        foodListView.setAdapter(arrayAdapter);

        foodListView.setSelection(0);
        foodListView.setItemChecked(0, true);
        if(foodListView.getSelectedView() != null) {
            foodListView.getSelectedView().setSelected(true);
        }

        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private View lastSelectedView = null;

            @SuppressLint("ResourceAsColor")
            public void clearSelection() {
                if(lastSelectedView != null) lastSelectedView.setBackgroundColor(android.R.color.white);
            }
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedFoodItem = foodListView.getItemAtPosition(i).toString();
                clearSelection();
                lastSelectedView = view;
                view.setBackgroundDrawable(view.getContext().getResources().getDrawable(R.drawable.blue_color));
            }
        });

    }

    public void initializeUserLocation(){
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                userLocation = location;
                progressDialog.dismiss();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) { }

            @Override
            public void onProviderEnabled(String s) { }

            @Override
            public void onProviderDisabled(String s) { }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    public void onClickRecipe(View view){
        Intent intent = new Intent(getApplicationContext(), RecipePublisherActivity.class);
        System.out.println("Selected item is : " + selectedFoodItem);
        intent.putExtra("selectedFoodItem",selectedFoodItem);
        intent.putExtra("userLocation",userLocation);
        startActivity(intent);
    }

    public void onClickSearchRestaurant(View view){
        Intent intent = new Intent(getApplicationContext(),RestaurantListActivity.class);
        System.out.println("Selected item is : " + selectedFoodItem);
        intent.putExtra("selectedFoodItem",selectedFoodItem);
        intent.putExtra("userLocation",userLocation);
        startActivity(intent);
    }

    public void onClickHomeFood(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void onClickBackFood(View view){
        finish();
    }

}
