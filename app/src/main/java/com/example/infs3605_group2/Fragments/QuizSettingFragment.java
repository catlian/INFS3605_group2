package com.example.infs3605_group2.Fragments;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.infs3605_group2.Activities.QuizActivity;
import com.example.infs3605_group2.Activities.SavingsActivity;
import com.example.infs3605_group2.Activities.SavingsActivityBase;
import com.example.infs3605_group2.Models.Question;
import com.example.infs3605_group2.R;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class QuizSettingFragment extends Fragment {
    private Button btnStart;
    private TypedArray quizImageArray;

    private String[] quizObjectArray;
    private String[] quizPriceArray;

    ArrayList<Question> questionArrayList = new ArrayList<>();

    public QuizSettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceBundle) {
        quizImageArray = getResources().obtainTypedArray(R.array.quiz_image_array);
        quizObjectArray = getResources().getStringArray(R.array.quiz_object_array);
        quizPriceArray = getResources().getStringArray(R.array.quiz_price_array);

        btnStart = view.findViewById(R.id.btnStart);
        btnStart.setEnabled(false);
        createQuestions();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), QuizActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("questions",(Serializable)questionArrayList);
                intent.putExtra("BUNDLE",args);
                startActivity (intent);
            }
        });

    }
    public void createQuestions(){
        List<Integer> intArray = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);
        Collections.shuffle(intArray);
        int numQuestions = 8;
        List<Integer> randomSeries = intArray.subList(0, numQuestions);
        for(int i= 0; i<numQuestions-1; i++){
            Question question = new Question();
            question.setImage(quizImageArray.getResourceId(randomSeries.get(i), -1));
            question.setQuestion(getResources().getString(R.string.question_string) + " " +
                    quizObjectArray[randomSeries.get(i)] + "?");
            question.setAnswer("$"+quizPriceArray[randomSeries.get(i)]+".00");
            ArrayList<Integer> optionsArray = createOptions(Integer.parseInt(quizPriceArray[randomSeries.get(i)]));
            question.setOption2("$"+ optionsArray.get(0) +".00");
            question.setOption3("$"+ optionsArray.get(1) +".00");
            question.setOption4("$"+ optionsArray.get(2) +".00");
            questionArrayList.add(question);
        }
        btnStart.setEnabled(true);
    }

    public ArrayList<Integer> createOptions(int answer){
        int increment = 0;
        if(answer>=100){
            increment = 30;
        }
        else if (answer >=20){
            increment = 15;
        }
        else if (answer<20){
            increment = 10;
        }
        int minOption;
        int maxOption = answer + increment;
        if (answer-increment < 1){
            minOption = 1;
        } else {minOption = answer-increment;}

        ArrayList<Integer> optionsArrayList = new ArrayList<>();
        Random random = new Random();
        while (optionsArrayList.size() < 4 ){
            int option = random.nextInt((maxOption - minOption) + 1) + minOption;
            if (!optionsArrayList.contains(option) && (option != answer) ){
                optionsArrayList.add(option);
            }
        }
        return optionsArrayList;
    }
}
