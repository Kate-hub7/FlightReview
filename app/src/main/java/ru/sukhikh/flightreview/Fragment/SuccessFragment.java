package ru.sukhikh.flightreview.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.tabs.TabItem;

import java.util.ArrayList;
import java.util.List;

import ru.sukhikh.flightreview.Entity.Rating;
import ru.sukhikh.flightreview.Parameter;
import ru.sukhikh.flightreview.R;
import ru.sukhikh.flightreview.ReviewViewModel;


public class SuccessFragment extends Fragment {

    View FragmentView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentView = inflater.inflate(R.layout.success_fragment, container, false);

        GridView listView = FragmentView.findViewById(R.id.recycler);

        //ReviewViewModel model = new ViewModelProvider(getActivity()).get(ReviewViewModel.class);
        ReviewViewModel model =  ViewModelProviders.of(getActivity()).get(ReviewViewModel.class);
        List<Rating>  rating = model.getUserMutableLiveData().getValue();

        TextView overallRating = FragmentView.findViewById(R.id.overall_rating);
        overallRating.setText(rating.get(rating.size()-1).toString());

        List<String> listReview = new ArrayList<>();
        for(int i = 0; i<5; i++)
            listReview.add(rating.get(i).toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.test_list_item, listReview);
        listView.setAdapter(adapter);

        Button buttonClose = FragmentView.findViewById(R.id.close);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


        return FragmentView;
    }
}
