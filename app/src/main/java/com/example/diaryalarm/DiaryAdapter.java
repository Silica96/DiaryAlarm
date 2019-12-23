package com.example.diaryalarm;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.diaryalarm.Database.DbBitmapUtility;
import com.example.diaryalarm.Database.Model;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.MyViewHolder> {

    Context dcontext;
    LayoutInflater dinflater;
    List<Model> ddata;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView diary;
        public TextView date;
        public ImageView img;

        public MyViewHolder(View view) {
            super(view);
            diary = view.findViewById(R.id.context);
            date = view.findViewById(R.id.diary_title);
            img = view.findViewById(R.id.image);
        }
    }

    public DiaryAdapter(Context context, List<Model> data){
        dcontext = context;
        ddata = data;
        dinflater = LayoutInflater.from(dcontext);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diary_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model note = ddata.get(position);

        holder.diary.setText(note.getNote());
        holder.date.setText(note.getDate());
        holder.img.setImageBitmap(DbBitmapUtility.getImage(note.getImage()));
    }

    @Override
    public int getItemCount() {
        return ddata.size();
    }


}
