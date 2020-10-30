package ru.sukhikh.flightreviewscreen.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ru.sukhikh.flightreviewscreen.Entity.Rating;
import ru.sukhikh.flightreviewscreen.R;
import ru.sukhikh.flightreviewscreen.ReviewViewModel;

public class SuccessFragment extends Fragment {


    View FragmentView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentView = inflater.inflate(R.layout.success_fragment, container, false);


        GridView listView = FragmentView.findViewById(R.id.recycler);

        ReviewViewModel model = new ViewModelProvider(getActivity()).get(ReviewViewModel.class);
        List<Rating>  rating = model.getUserMutableLiveData().getValue();
        final String[] catNames = new String[] {
                rating.get(0).getRating()+" Aircraft", rating.get(1).getRating()+" Crew",
                rating.get(2).getRating()+" Seats", rating.get(3).getRating()+" Flight experience", " Food"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.test_list_item, catNames);

        listView.setAdapter(adapter);

        return FragmentView;
    }
}
