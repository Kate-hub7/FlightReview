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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.sukhikh.flightreview.Adapter.RatingListAdapter;
import ru.sukhikh.flightreview.Entity.Feedback;
import ru.sukhikh.flightreview.R;
import ru.sukhikh.flightreview.ViewModel.FeedbackViewModel;
import ru.sukhikh.flightreview.ViewModel.ReviewViewModel;


public class SubmitFragment extends Fragment {

    private RatingListAdapter adapter;
    View fragmentView;
    FeedbackViewModel feedbackModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.submit_fragment, container, false);


        Toolbar toolbar = fragmentView.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


        final EditText editText = fragmentView.findViewById(R.id.feedback);

        feedbackModel = new ViewModelProvider(this).get(FeedbackViewModel.class);
        feedbackModel.getMutableLiveData().observe(getActivity(), feedback -> editText.setText(feedback.getFeedbackStr()));

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

        final RatingBar ratingBar = fragmentView.findViewById(R.id.ratingBar);

        RecyclerView recyclerView = fragmentView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);


     //   final ReviewViewModel model =  ViewModelProviders.of(getActivity()).get(ReviewViewModel.class);
        final ReviewViewModel model = new ViewModelProvider(getActivity()).get(ReviewViewModel.class);
        model.getUserMutableLiveData().observe(getViewLifecycleOwner(), notes -> {

            adapter = new RatingListAdapter(notes, model::onRatingBarChanged);
            recyclerView.setAdapter(adapter);

            //overall review
            ratingBar.setRating(notes.get(notes.size()-1).getRating());
        });


        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> model.onOverallRatingChanged((int) rating));

        Button submit = fragmentView.findViewById(R.id.submit);
        submit.setOnClickListener(v -> {

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new SuccessFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return fragmentView;
    }

    @Override
    public void onStop() {
        super.onStop();

        feedbackModel.updateLiveData();
    }
}
