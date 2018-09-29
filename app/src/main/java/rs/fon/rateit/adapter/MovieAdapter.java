package rs.fon.rateit.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rs.fon.rateit.R;

/**
 * Created by Deepak kolhe on 2/7/2017.
 */

public class MovieAdapter extends ArrayAdapter<MovieItem> {

    Context context;
    int layoutResourceId;
    List<MovieItem> data=null;


    public MovieAdapter(Context context, int resource, List<MovieItem> objects) {
        super(context, resource, objects);

        this.layoutResourceId = resource;
        this.context= context;
        this.data=objects;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DataHolder holder =null;

        if(convertView==null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();

            convertView = inflater.inflate(layoutResourceId,parent,false);

            holder = new DataHolder();
            holder.setIvFlag((ImageView)convertView.findViewById(R.id.ivMovie));
            holder.setTvMovieName((TextView)convertView.findViewById(R.id.tvMovieTitle));
            holder.setTvMovieYear((TextView)convertView.findViewById(R.id.tvMovieYear));

            convertView.setTag(holder);
        }
        else
        {
            holder = (DataHolder)convertView.getTag();
        }

        MovieItem movieItem = data.get(position);

        holder.getTvMovieName().setText(movieItem.movieName);
        holder.getIvFlag().setImageResource(movieItem.resIdThumbnail);
        holder.getTvMovieYear().setText(movieItem.movieYear);

        return  convertView;
    }
}
