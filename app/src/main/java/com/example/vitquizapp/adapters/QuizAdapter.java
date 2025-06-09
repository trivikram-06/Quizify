package com.example.vitquizapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

import com.example.vitquizapp.R;

import java.util.List;

public class QuizAdapter extends BaseAdapter {
    private Context context;
    private List<String> quizTitles;

    public QuizAdapter(Context context, List<String> quizTitles) {
        this.context = context;
        this.quizTitles = quizTitles;
    }

    @Override
    public int getCount() {
        return quizTitles.size();
    }

    @Override
    public Object getItem(int position) {
        return quizTitles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_quiz, parent, false);
        }

        TextView quizTitle = convertView.findViewById(R.id.quizTitle);
        quizTitle.setText(quizTitles.get(position));

        return convertView;
    }
}
