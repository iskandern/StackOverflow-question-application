package nasretdinovir.example.com.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nasretdinovir.example.com.DataDefinition.Answer;
import nasretdinovir.example.com.R;

public class AnswerViewAdapter extends RecyclerView.Adapter<AnswerViewAdapter.ViewHolder> {

    private List<Answer> answerList_;

    public AnswerViewAdapter(List<Answer> answerList) {
        answerList_ = answerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForQuestionItem = R.layout.answer_partial;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForQuestionItem, parent, false);

        return new AnswerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Answer answerItem = answerList_.get(position);
        holder.bind(answerItem);
    }

    @Override
    public int getItemCount() {
        return answerList_.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView score_;
        private TextView body_;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            score_ = itemView.findViewById(R.id.answerScoreView);
            body_ = itemView.findViewById(R.id.answerBodyView);
        }

        public void bind(Answer answerItem) {
            score_.setText(String.valueOf(answerItem.getScore()));
            body_.setText(answerItem.getBody());
        }
    }
}
