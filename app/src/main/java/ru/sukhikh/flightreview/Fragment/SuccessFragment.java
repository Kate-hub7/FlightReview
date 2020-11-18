package ru.sukhikh.flightreview.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

        return inflater.inflate(R.layout.success_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorWhite));

        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        final GridView listView = view.findViewById(R.id.recycler);

        ReviewViewModel model = new ViewModelProvider(getActivity()).get(ReviewViewModel.class);
        List<Rating>  rating = model.getRatingListMutableLiveData().getValue();

        final TextView overallRating = view.findViewById(R.id.overall_rating);
        overallRating.setText(rating.get(rating.size()-1).toString());

        List<String> listReview = new ArrayList<>();
        for(int i = 0; i<5; i++)
            listReview.add(rating.get(i).toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.test_list_item, listReview);
        listView.setAdapter(adapter);

        final Button buttonClose = view.findViewById(R.id.close);
        buttonClose.setOnClickListener(v -> getParentFragmentManager().popBackStack());
    }
}
