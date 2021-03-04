package nasretdinovir.example.com.DataDefinition;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Question implements Serializable {

    private static final long serialVersionUID = 6125064027896934732L;

    @SerializedName("title")
    private String title_;

    @SerializedName("body_markdown")
    private String body_;

    @SerializedName("owner")
    private Owner owner_;

    @SerializedName("score")
    private int score_;

    @SerializedName("question_id")
    private int questionId_;

    public Question(String title, String body, Owner owner, int score, int questionId) {
        title_ = title;
        body_ = body;
        owner_ = owner;
        score_ = score;
        questionId_ = questionId;
    }

    public String getTitle() {
        return title_;
    }

    public String getBody() {
        return body_;
    }

    public Owner getOwner() {
        return owner_;
    }

    public int getScore() {
        return score_;
    }

    public int getQuestionId() {
        return questionId_;
    }
}
