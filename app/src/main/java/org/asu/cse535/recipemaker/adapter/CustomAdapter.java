package org.asu.cse535.recipemaker.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;


import org.asu.cse535.recipemaker.R;
import org.asu.cse535.recipemaker.model.response.Restaurant;

import java.util.List;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Restaurant> dataList;
    private Context context;

    public CustomAdapter(Context context,List<Restaurant> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtTitle;
        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.title);
            coverImage = mView.findViewById(R.id.coverImage);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Restaurant restaurant = dataList.get(position);
        String html1 =     "<h2><b>" +  restaurant.getRestaurantName()+  "</b></h2>"
                        + "<p>" + restaurant.getAddress().getFormattedAddress() + "</p>"
                        + "<p>" + restaurant.getRestaurantPhone() + "</p>";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.txtTitle.setText(Html.fromHtml(html1, Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.txtTitle.setText(Html.fromHtml(html1));
        }

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getThumbnailUrl())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public Restaurant getItem(int position) {
        return dataList.get(position);
    }
}

