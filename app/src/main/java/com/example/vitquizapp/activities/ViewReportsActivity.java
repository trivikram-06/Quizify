package com.example.vitquizapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vitquizapp.R;
import com.example.vitquizapp.adapters.ReportAdapter;
import com.example.vitquizapp.models.ReportModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class ViewReportsActivity extends AppCompatActivity {

    private ListView reportListView;
    private ReportAdapter reportAdapter;
    private List<ReportModel> reportList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        reportListView = findViewById(R.id.reportListView);
        reportList = new ArrayList<>();
        reportAdapter = new ReportAdapter(this, reportList);
        reportListView.setAdapter(reportAdapter);

        db = FirebaseFirestore.getInstance();
        fetchReports();
    }

    private void fetchReports() {
        db.collection("quiz_results").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Firestore", "Error fetching reports", error);
                    return;
                }

                if (snapshots == null || snapshots.isEmpty()) {
                    Log.d("Firestore", "No reports found");
                    return;
                }

                reportList.clear();

                for (QueryDocumentSnapshot document : snapshots) {
                    String email = document.getString("email");
                    String quiz = document.getString("quiz");

                    // Handle score data type safely
                    Object scoreObject = document.get("score");
                    int score = 0;
                    if (scoreObject instanceof Number) {
                        score = ((Number) scoreObject).intValue();
                    }

                    reportList.add(new ReportModel(email, quiz, score));
                }

                reportAdapter.notifyDataSetChanged();
            }
        });
    }

}
