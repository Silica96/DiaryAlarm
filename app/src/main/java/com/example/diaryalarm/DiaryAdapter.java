package com.example.diaryalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DiaryAdapter extends BaseAdapter {

    Context dcontext = null;
    LayoutInflater dinflater = null;
    ArrayList<DiaryData> ddata;

    public DiaryAdapter(Context context, ArrayList<DiaryData> data){
        dcontext = context;
        ddata = data;
        dinflater = LayoutInflater.from(dcontext);
    }

    @Override
    public int getCount() {
        return ddata.size();
    }

    @Override
    public DiaryData getItem(int i) {
        return ddata.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = dinflater.inflate(R.layout.diary_list,null);

        ImageView imageView = (ImageView)view.findViewById(R.id.image);
        TextView diaryTitle = (TextView)view.findViewById(R.id.diary_title);

        imageView.setImageResource(ddata.get(i).getImage());
        diaryTitle.setText(ddata.get(i).getDiary_title());

        return view1;
    }
}
