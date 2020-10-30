package ru.sukhikh.flightreviewscreen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.sukhikh.flightreviewscreen.Entity.Rating;
import ru.sukhikh.flightreviewscreen.R;

public class RatingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Rating> ratingList = new ArrayList<>();


    public static class ViewType2 extends RecyclerView.ViewHolder{

        TextView question;
        RatingBar ratingBar;
        CheckBox checkBox;

        public ViewType2(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.question);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
    public static class ViewType1 extends RecyclerView.ViewHolder {

        TextView question;
        RatingBar ratingBar;

        public ViewType1(View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.question);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }

    public void setData(List<Rating> ratingList){
        this.ratingList = ratingList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType) {

        switch (viewType){
            case 0:
                return new ViewType1(LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item_review,
                        parent, false));
            case 1:
                return new ViewType2(LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item_review_food,
                        parent, false));
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return (position!=(getItemCount()-1))? 0 : 1;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final Rating currentRating = ratingList.get(position);
        switch (holder.getItemViewType()) {
            case 0:
                ViewType1 holder1 = (ViewType1)holder;
                holder1.question.setText(currentRating.getParameter().getQuestion());
                holder1.ratingBar.setRating(currentRating.getRating());

                holder1.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating,
                                                boolean fromUser) {

                        currentRating.setRating((int)rating);
                    }
                });
                break;

            case 1:
                final ViewType2 holder2 = (ViewType2)holder;
                holder2.question.setText(currentRating.getParameter().getQuestion());

                holder2.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(holder2.checkBox.isChecked())
                            holder2.ratingBar.setVisibility(View.INVISIBLE);
                        else
                            holder2.ratingBar.setVisibility(View.VISIBLE);
                    }
                });
                if(holder2.checkBox.isChecked())
                    holder2.ratingBar.setIsIndicator(true);

                holder2.ratingBar.setRating(currentRating.getRating());

                holder2.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating,
                                                boolean fromUser) {

                        currentRating.setRating((int)rating);
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }

}
