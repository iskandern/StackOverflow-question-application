package nasretdinovir.example.com.DataDefinition;

import com.google.gson.annotations.SerializedName;

public class Answer {

    @SerializedName("body_markdown")
    private String body_;

    @SerializedName("score")
    private int score_;

    public Answer(String body, int score) {
        body_ = body;
        score_ = score;
    }

    public String getBody() {
        return body_;
    }

    public int getScore() {
        return score_;
    }
}
