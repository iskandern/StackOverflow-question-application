package nasretdinovir.example.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import nasretdinovir.example.com.Adapter.AnswerViewAdapter;
import nasretdinovir.example.com.DataDefinition.Answer;
import nasretdinovir.example.com.DataDefinition.Question;
import nasretdinovir.example.com.Services.ItemListWrapper;
import nasretdinovir.example.com.Services.StackOverflowAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FullQuestionActivity extends AppCompatActivity {

    private StackOverflowAPI stackOverflowAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_question);

        Question selectedQuestion = (Question) getIntent().getSerializableExtra("Question");
        displayQuestion(selectedQuestion);

        createStackOverflowAPI();
        stackOverflowAPI.getAnswers(selectedQuestion.getQuestionId()).enqueue(answersCallback);
    }

    private void displayQuestion(Question selectedQuestion) {
        TextView score = findViewById(R.id.questionScoreView);
        TextView ownerName = findViewById(R.id.questionOwnerNameView);
        TextView title = findViewById(R.id.questionTitleView);
        TextView body = findViewById(R.id.questionBodyView);

        score.setText(String.valueOf(selectedQuestion.getScore()));
        ownerName.setText(selectedQuestion.getOwner().getDisplayName());
        title.setText(selectedQuestion.getTitle());
        body.setText(selectedQuestion.getBody());
        body.setMovementMethod(new ScrollingMovementMethod());
    }

    private void createStackOverflowAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.API_URL_)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        stackOverflowAPI = retrofit.create(StackOverflowAPI.class);
    }

    Callback<ItemListWrapper<Answer>> answersCallback = new Callback<ItemListWrapper<Answer>>() {
        @Override
        public void onResponse(Call<ItemListWrapper<Answer>> call, Response<ItemListWrapper<Answer>> response) {
            if (!response.isSuccessful()) {
                Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                closeProgressBar();
                return;
            }
            ItemListWrapper<Answer> answers = response.body();
            AnswerViewAdapter adapter = new AnswerViewAdapter(answers.getItems());

            closeProgressBar();

            RecyclerView recyclerView = findViewById(R.id.answerRecycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(adapter);

        }

        @Override
        public void onFailure(Call<ItemListWrapper<Answer>> call, Throwable t) {
            Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();
            closeProgressBar();
            t.printStackTrace();
        }
    };

    private void closeProgressBar() {
        ProgressBar pb = findViewById(R.id.answersProgressBar);
        pb.setVisibility(View.GONE);
    }
}