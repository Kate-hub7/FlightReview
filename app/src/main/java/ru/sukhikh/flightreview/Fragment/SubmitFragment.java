package ru.sukhikh.flightreview.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;

import ru.sukhikh.flightreview.Adapter.RatingListAdapter;
import ru.sukhikh.flightreview.Entity.Feedback;
import ru.sukhikh.flightreview.Entity.Rating;
import ru.sukhikh.flightreview.R;
import ru.sukhikh.flightreview.ViewModel.FeedbackViewModel;
import ru.sukhikh.flightreview.ViewModel.ReviewViewModel;


public class SubmitFragment extends Fragment {

    private RatingListAdapter adapter;
    private FeedbackViewModel feedbackModel;
    private ReviewViewModel model;

    private CollapsingToolbarLayoutState state;

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View fragmentView = inflater.inflate(R.layout.submit_fragment, container, false);



        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setContentInsetsAbsolute(0,0);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


        final CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.toolbar_layout);
        final AppBarLayout app_bar=(AppBarLayout)view.findViewById(R.id.app_bar);
        final LinearLayout linearLayout= view.findViewById(R.id.swap);
        final ImageView imageView = view.findViewById(R.id.icon);

        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;
                        imageView.setVisibility(View.GONE);
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        linearLayout.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                        state = CollapsingToolbarLayoutState.COLLAPSED;
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if(state == CollapsingToolbarLayoutState.COLLAPSED){
                            linearLayout.setVisibility(View.VISIBLE);
                            imageView.setVisibility(View.GONE);
                        }
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;
                    }
                }
            }
        });


        final EditText editText = view.findViewById(R.id.feedback);

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

        //   final ReviewViewModel model =  ViewModelProviders.of(getActivity()).get(ReviewViewModel.class);
        model = new ViewModelProvider(getActivity()).get(ReviewViewModel.class);

        Button submit = view.findViewById(R.id.submit);
        submit.setOnClickListener(v -> {

            final ProgressBar progressBar = view.findViewById(R.id.progress_bar);

            progressBar.setVisibility(View.VISIBLE);

            List<Rating> tempList = model.getRatingList();
            StringBuilder results = new StringBuilder();
            for (int i=0;i<tempList.size();i++)
                results.append(tempList.get(i).toString()+"\n");

            results.append(feedbackModel.getLiveData().getFeedbackStr());
            Toast.makeText(getActivity(), results.toString(), Toast.LENGTH_SHORT).show();

            Handler handler = new Handler();

            new Thread(new Runnable() {
                public void run() {
                    try{
                        Thread.sleep(3000);
                    }
                    catch (Exception e) { e.printStackTrace(); }

                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);

                            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, new SuccessFragment());
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });
                }
            }).start();


        });



        final RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);

        final RatingBar ratingBar = view.findViewById(R.id.ratingBar);

        model.getRatingListMutableLiveData().observe(getViewLifecycleOwner(), notes -> {

            adapter = new RatingListAdapter(notes, model.AvailabilityFood(),model::onRatingBarChanged);
            recyclerView.setAdapter(adapter);

            //overall review
            ratingBar.setRating(notes.get(notes.size()-1).getRating());
        });

        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> model.onOverallRatingChanged((int) rating));
    }

    @Override
    public void onStop() {
        super.onStop();

        feedbackModel.updateLiveData();
    }
}
