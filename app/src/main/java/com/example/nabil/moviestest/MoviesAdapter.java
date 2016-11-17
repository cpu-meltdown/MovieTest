package com.example.nabil.moviestest;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by Nabil on 2/19/2016.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {

    ArrayList<Movie> moviesList;
    int adapterResource;
    Context adapterContext;

    public MoviesAdapter(Context context, int resource, ArrayList<Movie> objects) {
        super(context, resource, objects);

        moviesList = objects;
        adapterResource = resource;
        adapterContext = context;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Movie getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(adapterResource, null, false);
            viewHolder = new ViewHolder();

            viewHolder.movieImage = (ImageView) convertView.findViewById(R.id.movieImage);
            viewHolder.movieName = (TextView) convertView.findViewById(R.id.movieName);
            viewHolder.movieRate = (RatingBar) convertView.findViewById(R.id.movieRate);
            viewHolder.releaseDate = (TextView) convertView.findViewById(R.id.releaseDate);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(adapterContext).load(moviesList.get(position).getImageURL()).into(viewHolder.movieImage);
        viewHolder.movieName.setText(moviesList.get(position).getName());
        viewHolder.movieRate.setRating(moviesList.get(position).getVotes());
        viewHolder.releaseDate.setText(moviesList.get(position).getReleaseDate());
        return convertView;
    }


    public static class ViewHolder{
        public ImageView movieImage;
        public TextView movieName;
        public RatingBar movieRate;
        public TextView releaseDate;
    }


}
