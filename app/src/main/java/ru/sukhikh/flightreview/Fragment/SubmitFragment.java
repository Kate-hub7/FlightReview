package ru.sukhikh.flightreview.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
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
import ru.sukhikh.flightreview.Enum.Parameter;
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

        return inflater.inflate(R.layout.submit_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorBlack));

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setContentInsetsAbsolute(0,0);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


        final AppBarLayout appBarLayout=(AppBarLayout)view.findViewById(R.id.app_bar);
        final LinearLayout linearLayout= view.findViewById(R.id.swap);
        final ImageView imageView = view.findViewById(R.id.icon);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            int mAppBarOffset;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                mAppBarOffset = verticalOffset;
                int totalScrollRange = appBarLayout.getTotalScrollRange();

                float alpha = (float) -verticalOffset / totalScrollRange;
                imageView.setAlpha(alpha);
                linearLayout.setAlpha(1-alpha);

                if (alpha == 0) {
                    imageView.setVisibility(View.GONE);
                } else if (imageView.getVisibility() == View.GONE) {
                    imageView.setVisibility(View.VISIBLE);
                }

            }
        });


        final EditText editText = view.findViewById(R.id.feedback);

        feedbackModel = new ViewModelProvider(getActivity()).get(FeedbackViewModel.class);
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

            FrameLayout layout = view.findViewById(R.id.progress);
            layout.setVisibility(View.VISIBLE);

            List<Rating> tempList = model.getRatingList();
            StringBuilder results = new StringBuilder();
            for (int i=0;i<tempList.size();i++)
                results.append(tempList.get(i).toString()).append("\n");

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

            adapter = new RatingListAdapter(notes, model.AvailabilityFood(), new RatingListAdapter.RatingListListener() {
                @Override
                public void updateRating(Parameter parameter, int newRating) {
                    model.onRatingBarChanged(parameter, newRating);
                }

                @Override
                public void updateStateCheckbox() {
                    model.updateCheckbox();
                }
            });
            recyclerView.setAdapter(adapter);

            //overall review
            ratingBar.setRating(notes.get(notes.size()-1).getRating());
        });

        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> model.onOverallRatingChanged((int) rating));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        adapter = null;
        feedbackModel=null;
        model=null;
    }

    @Override
    public void onStop() {
        super.onStop();

        feedbackModel.updateLiveData();
    }
}
