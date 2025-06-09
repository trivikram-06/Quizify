package com.example.vitquizapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.widget.ArrayAdapter;
import androidx.cardview.widget.CardView;

import com.example.vitquizapp.R;
import com.example.vitquizapp.models.ReportModel;

import java.util.List;

public class ReportAdapter extends ArrayAdapter<ReportModel> {

    private final Context context;
    private final List<ReportModel> reportList;

    public ReportAdapter(Context context, List<ReportModel> reports) {
        super(context, 0, reports);
        this.context = context;
        this.reportList = reports;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_report, parent, false);
        }

        ReportModel report = getItem(position);

        // Find views
        TextView txtQuizName = convertView.findViewById(R.id.txtQuizName);
        TextView txtStudentEmail = convertView.findViewById(R.id.txtStudentEmail);
        TextView txtScore = convertView.findViewById(R.id.txtScore);
        CardView reportCard = convertView.findViewById(R.id.reportCard);

        // Set data
        txtQuizName.setText(report.getQuiz());
        txtStudentEmail.setText(report.getEmail());
        txtScore.setText("Score: " + report.getScore() + "/10");

        // Change text color based on score
        if (report.getScore() >= 7) {
            txtScore.setTextColor(ContextCompat.getColor(context, R.color.green)); // High Score
            reportCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_green));
        } else {
            txtScore.setTextColor(ContextCompat.getColor(context, R.color.red)); // Low Score
            reportCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_red));
        }

        return convertView;
    }
}
