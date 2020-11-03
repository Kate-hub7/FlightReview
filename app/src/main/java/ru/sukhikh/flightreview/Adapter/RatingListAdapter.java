package ru.sukhikh.flightreview.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.sukhikh.flightreview.Entity.Rating;
import ru.sukhikh.flightreview.Enum.Parameter;
import ru.sukhikh.flightreview.Enum.ViewType;
import ru.sukhikh.flightreview.R;


public class RatingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Rating> ratingList;
    private final RatingListListener listener;

    public RatingListAdapter(List<Rating> ratingList, RatingListListener listener) {
        this.ratingList = ratingList.subList(0, ratingList.size()-1);
        this.listener = listener;
    }

    public interface RatingListListener {
        void updateRating(Parameter parameter, int newRating);
    }

    public static class ViewType2 extends RecyclerView.ViewHolder{

        final TextView question;
        final RatingBar ratingBar;
        final CheckBox checkBox;

        public ViewType2(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.question);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
    public static class ViewType1 extends RecyclerView.ViewHolder {

        final TextView question;
        final RatingBar ratingBar;

        public ViewType1(View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.question);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType) {

        ViewType type = ViewType.values()[viewType];
        switch (type){
            case OTHER:
                return new ViewType1(LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item_review,
                        parent, false));
            case FOOD:
                return new ViewType2(LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item_review_food,
                        parent, false));
            case PERSON:
                return new ViewType1(LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item_review_person,
                        parent, false));
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {

       // return ratingList.get(position).getParameter().ordinal();
        if(position==0)
            return 2;
        return (position!=(getItemCount()-1))? 0 : 1;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final Rating currentRating = ratingList.get(position);
        ViewType type = ViewType.values()[holder.getItemViewType()];

        switch (type) {
            case PERSON:
            case OTHER:
                ViewType1 holder1 = (ViewType1)holder;
                holder1.question.setText(currentRating.getParameter().getQuestion());
                holder1.ratingBar.setRating(currentRating.getRating());

                holder1.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating,
                                                boolean fromUser) {

                        listener.updateRating(currentRating.getParameter(), (int)rating);
                     //   currentRating.setRating((int)rating);
                    }
                });
                break;

            case FOOD:
                final ViewType2 holder2 = (ViewType2)holder;
                holder2.question.setText(currentRating.getParameter().getQuestion());
                holder2.checkBox.setChecked(currentRating.isFoodChecked());
                if(holder2.checkBox.isChecked())
                    holder2.ratingBar.setIsIndicator(true);

                holder2.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(holder2.checkBox.isChecked()){
                            holder2.ratingBar.setIsIndicator(true);
                            currentRating.setFoodChecked(true);
                            holder2.ratingBar.setRating(0);
                        }
                        else{
                            holder2.ratingBar.setIsIndicator(false);
                            currentRating.setFoodChecked(false);
                            holder2.ratingBar.setRating(0);

                        }
                    }
                });

                holder2.ratingBar.setRating(currentRating.getRating());

                holder2.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating,
                                                boolean fromUser) {

                        listener.updateRating(currentRating.getParameter(), (int)rating);
                    //    currentRating.setRating((int)rating);
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
