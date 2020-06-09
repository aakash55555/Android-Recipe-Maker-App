package org.asu.cse535.recipemaker.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.asu.cse535.recipemaker.R;
import org.asu.cse535.recipemaker.adapter.ItemClickSupport;
import org.asu.cse535.recipemaker.adapter.RecipePublisherAdapter;
import org.asu.cse535.recipemaker.config.Config;
import org.asu.cse535.recipemaker.model.response.Recipe;
import org.asu.cse535.recipemaker.model.response.RecipePublisherResponse;
import org.asu.cse535.recipemaker.rest.API;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipePublisherActivity extends AppCompatActivity {
    private final static String TAG = RecipePublisherActivity.class.getSimpleName();

    String foodItem ;

    private RecipePublisherAdapter adapter;
    private static RecyclerView recyclerView;
    ProgressDialog progressDialog;
    Location userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_publisher);

        Intent intent = getIntent();
        foodItem = intent.getStringExtra("selectedFoodItem");
        userLocation = intent.getParcelableExtra("userLocation");

        progressDialog = new ProgressDialog(RecipePublisherActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        getRecipePublisher();
    }

    public void getRecipePublisher(){
        Call<RecipePublisherResponse> call = API.recipes().searchRecipePublisher(Config.RECIPE_API_KEY,foodItem);
        call.enqueue(new Callback<RecipePublisherResponse>() {
            @Override
            public void onResponse(Call<RecipePublisherResponse> call, Response<RecipePublisherResponse> response) {
                progressDialog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<RecipePublisherResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                progressDialog.dismiss();
                Toast.makeText(RecipePublisherActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(RecipePublisherResponse body) {
        List<Recipe> recipePublisherList = body.getRecipes();
        if(recipePublisherList== null){
            recipePublisherList = new ArrayList<>();
        }
        recyclerView = findViewById(R.id.recipePublisherList);

        adapter = new RecipePublisherAdapter(this,recipePublisherList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecipePublisherActivity.this);
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

                Recipe selectedRecipePublisher = adapter.getItem(position);

                Intent intent = new Intent(getApplicationContext(), RecipeDetailActivity.class);
                intent.putExtra("rid",selectedRecipePublisher.getId());

                startActivity(intent);
            }
        });
    }

    public void onClickHomeRecipePublisher(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void onClickBackRecipePublisher(View view){
        finish();
    }
}
