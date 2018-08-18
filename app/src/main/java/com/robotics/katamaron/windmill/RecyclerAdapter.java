package com.robotics.katamaron.windmill;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Listitem> listitems;
    private Context context;

    public RecyclerAdapter(List<Listitem> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_log_item, parent,false);
        return new  ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Listitem listitem = listitems.get(position);

        holder.log.setText(listitem.getLog());
        holder.datime.setText(listitem.getDatime());


    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView log;
        public TextView datime;

        public ViewHolder(View itemView) {
            super(itemView);
            log = (TextView) itemView.findViewById(R.id.log_text);
            datime = (TextView) itemView.findViewById(R.id.log_date);

        }
    }
}
