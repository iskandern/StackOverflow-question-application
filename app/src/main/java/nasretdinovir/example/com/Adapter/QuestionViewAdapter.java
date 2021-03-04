package nasretdinovir.example.com.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nasretdinovir.example.com.DataDefinition.Question;
import nasretdinovir.example.com.FullQuestionActivity;
import nasretdinovir.example.com.R;

public class QuestionViewAdapter extends RecyclerView.Adapter<QuestionViewAdapter.ViewHolder> {

    private List<Question> questionList_;

    public QuestionViewAdapter(List<Question> questionList) {
        questionList_ = questionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForQuestionItem = R.layout.question_partial;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForQuestionItem, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question questionItem = questionList_.get(position);
        holder.bind(questionItem);
    }

    @Override
    public int getItemCount() {
        return questionList_.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView score_;
        private TextView ownerName_;
        private TextView title_;

        private Question questionItem_;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            score_ = itemView.findViewById(R.id.questionScoreView);
            ownerName_ = itemView.findViewById(R.id.questionOwnerNameView);
            title_ = itemView.findViewById(R.id.questionTitleView);

            itemView.setOnClickListener(this);
        }

        public void bind(Question questionItem) {
            score_.setText(String.valueOf(questionItem.getScore()));
            ownerName_.setText(questionItem.getOwner().getDisplayName());
            title_.setText(questionItem.getTitle());

            questionItem_ = questionItem;
        }

        @Override
        public void onClick(View view) {
//            Toast.makeText(view.getContext(), ownerName_.getText(), Toast.LENGTH_LONG).show();
            openFullQuestionActivity(view);
        }

        private void openFullQuestionActivity(View view) {
            Intent intent = new Intent(view.getContext(), FullQuestionActivity.class);
            intent.putExtra("Question", questionItem_);
            view.getContext().startActivity(intent);
        }
    }
}
