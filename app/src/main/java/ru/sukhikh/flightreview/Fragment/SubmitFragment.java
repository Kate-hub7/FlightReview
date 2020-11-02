package ru.sukhikh.flightreview.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;

import ru.sukhikh.flightreview.Adapter.RatingListAdapter;
import ru.sukhikh.flightreview.Entity.Feedback;
import ru.sukhikh.flightreview.Entity.Rating;
import ru.sukhikh.flightreview.FeedbackViewModel;
import ru.sukhikh.flightreview.R;
import ru.sukhikh.flightreview.ReviewViewModel;


public class SubmitFragment extends Fragment {

    private RatingListAdapter adapter;
    View FragmentView;
    FeedbackViewModel feedbackModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentView = inflater.inflate(R.layout.submit_fragment, container, false);



        Toolbar toolbar = FragmentView.findViewById(R.id.toolbar);
       // toolbar.setLogo(R.drawable.reward);
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


        final EditText editText = FragmentView.findViewById(R.id.feedback);

        feedbackModel = ViewModelProviders.of(getActivity()).get(FeedbackViewModel.class);
        feedbackModel.getMutableLiveData().observe(getActivity(), new Observer<Feedback>() {
            @Override
            public void onChanged(Feedback feedback) {

                editText.setText(feedback.getFeedbackStr());
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {

                if(s.toString().equals(feedbackModel.getLiveData().getFeedbackStr()))
                    return;

                feedbackModel.updateViewModel(new Feedback(s.toString()));
            }

            @Override
            public void afterTextChanged(final Editable s) {

            }
        });

        final RatingBar ratingBar = FragmentView.findViewById(R.id.ratingBar);

        RecyclerView recyclerView = FragmentView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RatingListAdapter();
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        final ReviewViewModel model =  ViewModelProviders.of(getActivity()).get(ReviewViewModel.class);
        model.getUserMutableLiveData().observe(getActivity(), new Observer<List<Rating>>() {
            @Override
            public void onChanged(@Nullable List<Rating> notes) {
                adapter.setData(notes);
                ratingBar.setRating(notes.get(notes.size()-1).getRating());
            }
        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                model.getFlightData().setRating((int)rating);

            }
        });

        Button submit = FragmentView.findViewById(R.id.submit);
        submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new SuccessFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return FragmentView;
    }

    @Override
    public void onStop() {
        super.onStop();

        feedbackModel.updateLiveData();
    }
}
