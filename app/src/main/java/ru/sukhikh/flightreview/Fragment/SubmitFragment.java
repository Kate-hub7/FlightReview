package ru.sukhikh.flightreview.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sukhikh.flightreview.Adapter.RatingListAdapter;
import ru.sukhikh.flightreview.Entity.Rating;
import ru.sukhikh.flightreview.Feedback;
import ru.sukhikh.flightreview.FeedbackViewModel;
import ru.sukhikh.flightreview.MainActivity;
import ru.sukhikh.flightreview.R;
import ru.sukhikh.flightreview.ReviewViewModel;


public class SubmitFragment extends Fragment {

    private RecyclerView recyclerView;
    private RatingListAdapter adapter;
    View FragmentView;
    FeedbackViewModel feedbackModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentView = inflater.inflate(R.layout.submit_fragment, container, false);

        Toolbar toolbar = (Toolbar) FragmentView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        final EditText editText = FragmentView.findViewById(R.id.feedback);

         feedbackModel = ViewModelProviders.of(getActivity()).get(FeedbackViewModel.class);
        feedbackModel.getUserMutableLiveData().observe(getActivity(), new Observer<Feedback>() {
            @Override
            public void onChanged(Feedback feedback) {

                editText.setText(feedback.getFeedbackStr());
               // editText.setSelection(editText.getText().length());
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {

                if(s.toString().equals(feedbackModel.get().getFeedbackStr()))
                    return;
               // feedbackModel.updateLiveData(new Feedback(s.toString()));
                feedbackModel.updateViewModel(new Feedback(s.toString()));


            }

            @Override
            public void afterTextChanged(final Editable s) {

            }
        });



        recyclerView =  (RecyclerView) FragmentView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RatingListAdapter();
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);


       // ReviewViewModel model = new ViewModelProvider(getActivity()).get(ReviewViewModel.class);
        ReviewViewModel model =  ViewModelProviders.of(getActivity()).get(ReviewViewModel.class);
        model.getUserMutableLiveData().observe(getActivity(), new Observer<List<Rating>>() {
            @Override
            public void onChanged(@Nullable List<Rating> notes) {
                adapter.setData(notes);
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
