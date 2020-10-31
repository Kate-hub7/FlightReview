package ru.sukhikh.flightreview.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import ru.sukhikh.flightreview.Entity.Rating;
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
        final String[] catNames = new String[] {
                rating.get(0).getRating()+" Aircraft", rating.get(1).getRating()+" Crew",
                rating.get(2).getRating()+" Seats", rating.get(3).getRating()+" Flight experience", " Food"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.test_list_item, catNames);
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
