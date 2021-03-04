package nasretdinovir.example.com.Services;

import nasretdinovir.example.com.DataDefinition.Answer;
import nasretdinovir.example.com.DataDefinition.Question;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StackOverflowAPI {

    @GET("/2.2/questions?pagesize=100&order=desc&sort=votes&tagged=android&site=stackoverflow&filter=!9_bDDx5MI")
    Call<ItemListWrapper<Question>> getQuestions();

    @GET("/2.2/questions/{id}/answers?order=desc&sort=votes&site=stackoverflow&filter=!9_bDE(S6I")
    Call<ItemListWrapper<Answer>> getAnswers(@Path("id") int id);
}
