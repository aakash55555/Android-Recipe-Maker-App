package org.asu.cse535.recipemaker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.asu.cse535.recipemaker.R;
import org.asu.cse535.recipemaker.config.Config;
import org.asu.cse535.recipemaker.model.response.Recipe;
import org.asu.cse535.recipemaker.model.response.RecipeResponse;
import org.asu.cse535.recipemaker.rest.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailActivity extends AppCompatActivity {

    private final static String TAG = RecipeDetailActivity.class.getSimpleName();

    String recipeId;
    ProgressDialog progressDialog;
    Recipe recipe;

    private CollapsingToolbarLayout appBarLayout;
    private ImageView recipeImageView;
    private TextView recipeTitleTextView;
    private TextView recipeSourceLTextView;
    private TextView recipeIngredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        progressDialog = new ProgressDialog(RecipeDetailActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        Intent intent = getIntent();
        recipeId = intent.getStringExtra("rid");

        recipeTitleTextView = (TextView) findViewById(R.id.recipe_detail_title);
        recipeSourceLTextView = (TextView) findViewById(R.id.recipe_detail_source);
        recipeIngredientsTextView = (TextView) findViewById(R.id.recipe_detail_ingredients);
        recipeImageView = (ImageView) findViewById(R.id.recipe_detail_image);

        getRecipeDetail();
    }

    public void getRecipeDetail(){
        System.out.println("recipe id is : "+recipeId);
        Call<RecipeResponse> call = API.recipes().searchRecipe(Config.RECIPE_API_KEY,recipeId);
        call.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                recipe = response.body().getRecipe();
                progressDialog.dismiss();
                generateDataList(response.body());
            }
            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                progressDialog.dismiss();
                Toast.makeText(RecipeDetailActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(RecipeResponse body) {

        String ingredientsAsString = "";
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            String ingredient = recipe.getIngredients().get(i);
            ingredientsAsString = ingredientsAsString + "\u2022 " + ingredient + "\n";
        }

        recipeImageView.setVisibility(View.VISIBLE);

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));
        builder.build().load(recipe.getImageURL())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(recipeImageView);

        recipeSourceLTextView.setText(recipe.getSourceURL());
        recipeSourceLTextView.setMovementMethod(LinkMovementMethod.getInstance());
        recipeIngredientsTextView.setText(ingredientsAsString);
        recipeTitleTextView.setText(recipe.getTitle());
        recipeTitleTextView.setContentDescription(getString(R.string.content_desc_recipe_title));
    }
}
