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

import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import ru.sukhikh.flightreview.Entity.Rating;
import ru.sukhikh.flightreview.R;
import ru.sukhikh.flightreview.ViewModel.ReviewViewModel;


public class SuccessFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View fragmentView = inflater.inflate(R.layout.success_fragment, container, false);

        final GridView listView = fragmentView.findViewById(R.id.recycler);

        ReviewViewModel model = new ViewModelProvider(getActivity()).get(ReviewViewModel.class);
        List<Rating>  rating = model.getRatingListMutableLiveData().getValue();

        final TextView overallRating = fragmentView.findViewById(R.id.overall_rating);
        overallRating.setText(rating.get(rating.size()-1).toString());

        List<String> listReview = new ArrayList<>();
        for(int i = 0; i<5; i++)
            listReview.add(rating.get(i).toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.test_list_item, listReview);
        listView.setAdapter(adapter);

        final Button buttonClose = fragmentView.findViewById(R.id.close);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });


        return fragmentView;
    }
}
