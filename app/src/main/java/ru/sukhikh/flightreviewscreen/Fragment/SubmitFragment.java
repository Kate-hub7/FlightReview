package ru.sukhikh.flightreviewscreen.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sukhikh.flightreviewscreen.Adapter.RatingListAdapter;
import ru.sukhikh.flightreviewscreen.Entity.Rating;
import ru.sukhikh.flightreviewscreen.R;
import ru.sukhikh.flightreviewscreen.ReviewViewModel;

public class SubmitFragment extends Fragment {

    private RecyclerView recyclerView;
    private RatingListAdapter adapter;
    View FragmentView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentView = inflater.inflate(R.layout.submit_fragment, container, false);

        Toolbar toolbar = (Toolbar) FragmentView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        //FragmentView.setActionBar(toolbar);

        recyclerView =  (RecyclerView) FragmentView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RatingListAdapter();
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        ReviewViewModel model = new ViewModelProvider(getActivity()).get(ReviewViewModel.class);
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
}
