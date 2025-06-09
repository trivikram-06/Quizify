package com.example.vitquizapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import android.widget.BaseAdapter;
import com.example.vitquizapp.R;
import com.example.vitquizapp.models.Question;
import java.util.ArrayList;

public class QuestionAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Question> questions;

    public QuestionAdapter(Context context, ArrayList<Question> questions) {
        this.context = context;
        this.questions = questions != null ? questions : new ArrayList<>();
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int position) {
        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;  // Create a new variable to avoid the lambda issue
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        }

        Question question = questions.get(position);
        if (question == null) return view;

        TextView questionText = view.findViewById(R.id.questionText);
        RadioGroup optionsGroup = view.findViewById(R.id.optionsGroup);
        RadioButton option1 = view.findViewById(R.id.option1);
        RadioButton option2 = view.findViewById(R.id.option2);
        RadioButton option3 = view.findViewById(R.id.option3);
        RadioButton option4 = view.findViewById(R.id.option4);

        questionText.setText(question.getQuestionText());
        option1.setText(question.getOption1());
        option2.setText(question.getOption2());
        option3.setText(question.getOption3());
        option4.setText(question.getOption4());

        // ✅ Fix: Declare `view` as final to avoid lambda error
        final View finalView = view;

        optionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedOption = finalView.findViewById(checkedId);
            if (selectedOption != null) {
                Toast.makeText(context, "Selected: " + selectedOption.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
