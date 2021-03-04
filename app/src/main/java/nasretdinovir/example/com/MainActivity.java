package nasretdinovir.example.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import nasretdinovir.example.com.Adapter.QuestionViewAdapter;
import nasretdinovir.example.com.DataDefinition.Question;
import nasretdinovir.example.com.Services.ItemListWrapper;
import nasretdinovir.example.com.Services.StackOverflowAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    static final String API_URL_ = "https://api.stackexchange.com";
    private StackOverflowAPI stackOverflowAPI_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        createStackOverflowAPI();

        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        stackOverflowAPI_.getQuestions().enqueue(questionsCallback);

    }

    private void createStackOverflowAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL_)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        stackOverflowAPI_ = retrofit.create(StackOverflowAPI.class);
    }

    Callback<ItemListWrapper<Question>> questionsCallback = new Callback<ItemListWrapper<Question>>() {
        @Override
        public void onResponse(Call<ItemListWrapper<Question>> call, Response<ItemListWrapper<Question>> response) {
            if (!response.isSuccessful()) {
                Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                closeProgressBar();
                return;
            }
            ItemListWrapper<Question> questionsWrapper = response.body();
            List<Question> questions = questionsWrapper.getItems();

            if (questions == null) {
                Toast.makeText(getApplicationContext(), "Parsing error", Toast.LENGTH_SHORT).show();
                closeProgressBar();
            }

            makeQuestionsView(questions);
            saveQuestionList(questions);

        }

        @Override
        public void onFailure(Call<ItemListWrapper<Question>> call, Throwable t) {
            List<Question> questions = readQuestionList();

            if (questions == null) {
                Toast.makeText(getApplicationContext(), "No connection and no cache", Toast.LENGTH_SHORT).show();
                closeProgressBar();
                t.printStackTrace();
                return;
            }

            Toast.makeText(getApplicationContext(), "No connection\n Data from cache", Toast.LENGTH_SHORT).show();
            makeQuestionsView(questions);

        }
    };

    private void makeQuestionsView(List<Question> questions) {

        closeProgressBar();
        QuestionViewAdapter adapter = new QuestionViewAdapter(questions);

        RecyclerView recyclerView = findViewById(R.id.questionRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    private void closeProgressBar() {
        ProgressBar pb = findViewById(R.id.questionsProgressBar);
        pb.setVisibility(View.GONE);
    }

    private List<Question> readQuestionList() {
        FileInputStream fileIn;
        ObjectInputStream in;
        try {
            String filename = "question.bin";
            File cacheFile = new File(getCacheDir(), filename);

            fileIn = new FileInputStream(cacheFile);
//            fileIn = new FileInputStream("questionsx.ser");
            in = new ObjectInputStream(fileIn);

            List<Question> object = (List<Question>) in.readObject();
            if (object == null) {
                Toast.makeText(getApplicationContext(), "Reading error", Toast.LENGTH_SHORT).show();
            }

            in.close();
            fileIn.close();

            return object;
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return null;
        }
    }

    private void saveQuestionList(List<Question> questions) {
        FileOutputStream fileOut;
        ObjectOutputStream out;
        try {
            String filename = "question.bin";
            File cacheFile = new File(getCacheDir(), filename);

            Toast.makeText(this, "Saved in " + cacheFile.getCanonicalPath(), Toast.LENGTH_LONG).show();

            fileOut = new FileOutputStream(cacheFile);
            out = new ObjectOutputStream(fileOut);

            out.writeObject(questions);

            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}