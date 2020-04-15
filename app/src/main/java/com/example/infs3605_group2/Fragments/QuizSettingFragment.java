/*
package com.example.infs3605_group2.Fragments;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class QuizSettingFragment extends Fragment implements QuizDelegate {
    private int quizID;
    private String quizDescriptionString;
    private String quizNameString;
    private String quizCategory;
    private Button btnSelect;

    int numQuestionsCreated;
    Question question = new Question();
    ArrayList<Question> questionArrayList = new ArrayList<>();

    public QuizSettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quizID = getArguments().getInt("QUIZ_ID");
            quizNameString = getArguments().getString("QUIZ_NAME");
            quizCategory = getArguments().getString("QUIZ_CAT");
            quizDescriptionString = getArguments().getString("QUIZ_DESCRIPTION");
        }
        numQuestionsCreated = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceBundle) {
        TextView quizName = view.findViewById(R.id.quizName);
        TextView quizDescription = view.findViewById(R.id.quizDescription);
        quizName.setText(quizNameString);
        quizDescription.setText(quizDescriptionString);

        final TextView loadMsg = view.findViewById(R.id.loadMsg);
        loadMsg.setVisibility(View.GONE);
        Button startQuizButton = view.findViewById(R.id.startQuizButton);
        startQuizButton.setVisibility(View.GONE);
        final ProgressBar progressBar=view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        final Spinner spinner = view.findViewById(R.id.numQuestionsSpinner);
        btnSelect = view.findViewById(R.id.selectNumQuestions);

        Integer[] numQuestions = new Integer[]{5, 10, 15, 20};

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>
                (getContext(), android.R.layout.simple_spinner_item, numQuestions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numQuestions = (Integer.parseInt(String.valueOf(spinner.getSelectedItem())));
                loadMsg.setVisibility(View.VISIBLE);
                loadMsg.setText("Please wait while we load your questions.");
                progressBar.setVisibility(View.VISIBLE);
                createQuestions(numQuestions, quizCategory);
            }
        });

    }
*/
/*
The way this part works:
    1) create question options by randomly generating 4 drinks.
        1a) the first drink's name & image will be used as the answer & question.
        1b) the 2nd-4th drinks' names will be used as options.
            This is done as a loop of the volley query which checks how many options have already been made
    2) after question options are created (i.e. looped 3 times to create 3 other options),
        load the question into an array list
    3) repeat above 2 steps until all questions have arrived in the array list.
        asynctask will insert the questions into the database
    4) once this operation is done, go to quiz activity with the quizID (which will retrieve those questions)
Unfortunately, it does take a long time to go through this process which is why we included the
loading text & progress bar.
Possible improvements could be to upload question by question instead of waiting for the whole
list of questions, and start the quiz once 2-3 questions have already been uploaded. However, there is
the risk of the user completing those questions too quickly so the other questions won't load in time.
 *//*

    public void createQuestions(final int numQuestions, String quizCategory) {
        createQuestionAnswer(numQuestions, quizCategory);
    }

    private void createQuestionAnswer(final int numQuestions, String quizCategory) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //set answer, option1, option 2, etc.
                //create question
                //insert questions async task
                DrinksImport drinksImport = new Gson().fromJson(response, DrinksImport.class);
                ArrayList<Drinks> drinksList = drinksImport.getDrinks();
                Collections.shuffle(drinksList);
                Drinks selectedDrink = drinksList.get(0);
                question.setAnswer(selectedDrink.getStrDrink());
                String drinkImage = selectedDrink.getStrDrinkThumb();
                question.setImageUrl(drinkImage);
                createOptions(numQuestions);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Request failed");
            }
        };
        String url = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?c=" + quizCategory;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        requestQueue.add(stringRequest);
    }

    int numOptions=1;
    private void createOptions(final int numQuestions) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DrinksImport drinksImport = new Gson().fromJson(response, DrinksImport.class);
                ArrayList<Drinks> drinksList = drinksImport.getDrinks();
                Collections.shuffle(drinksList);
                Drinks selectedDrink = drinksList.get(0);
                switch (numOptions) {
                    case 1:
                        question.setOption2(selectedDrink.getStrDrink());
                        break;
                    case 2:
                        question.setOption3(selectedDrink.getStrDrink());
                        break;
                    case 3:
                        question.setOption4(selectedDrink.getStrDrink());
                        break;
                }

                if(numOptions>=4){
                    question.setQuizId(quizID);
                    question.setQuestion("What is this drink?");
                    question.setQuestionId(0);
                    questionArrayList.add(question);
                    question = new Question();
                    numOptions=1;
                    numQuestionsCreated++;
                    if(numQuestionsCreated>=numQuestions){
                        Question[] arrayQuestions = new Question[numQuestions];
                        for(int i=0;i<arrayQuestions.length;i++){
                            arrayQuestions[i]=questionArrayList.get(i);
                        }
                        AppDatabase db = AppDatabase.getInstance(getContext());
                        InsertQuestionAsyncTask insertQuestionAsyncTask = new InsertQuestionAsyncTask();
                        insertQuestionAsyncTask.setDatabase(db);
                        insertQuestionAsyncTask.setDelegate(QuizSettingFragment.this);
                        insertQuestionAsyncTask.setQuizID(quizID);
                        insertQuestionAsyncTask.execute(arrayQuestions);
                    }
                    else{
                        createQuestionAnswer(numQuestions, quizCategory);
                    }
                }
                else{
                    numOptions++;
                    createOptions(numQuestions);
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Request failed");
            }
        };
        String url = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?c=" + quizCategory;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        requestQueue.add(stringRequest);
    }

    @Override
    public void handleQuestionResult(List<Question> questionList) {
        Button button = getView().findViewById(R.id.startQuizButton);
        TextView loadMsg = getView().findViewById(R.id.loadMsg);
        loadMsg.setText("Your quiz is now ready!");
        ProgressBar progressBar = getView().findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), QuizActivity.class);
                intent.putExtra("quizId", quizID);
                startActivity(intent);
            }
        });
    }
}
*/
