package com.wax_tadpole_games.android.asteroids;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class HighscoreAdapter extends RecyclerView.Adapter<HighscoreAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<String> list;

    public HighscoreAdapter(Context context, List<String> strings) {
        list = strings;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_highscore, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HighscoreAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(list.get(i));
        switch (Math.round((float)Math.random() * 3)) {
            case 0:
                viewHolder.icon.setImageResource(R.drawable.asteroid1);
                break;
            case 1:
                viewHolder.icon.setImageResource(R.drawable.asteroid2);
                break;
            case 2:
                viewHolder.icon.setImageResource(R.drawable.asteroid3);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle;
        public ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.hs_title);
            subtitle = itemView.findViewById(R.id.hs_subtitle);
            icon = itemView.findViewById(R.id.hs_icon);
        }
    }
}
