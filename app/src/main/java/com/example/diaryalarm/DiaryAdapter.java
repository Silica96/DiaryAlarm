package com.example.diaryalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
