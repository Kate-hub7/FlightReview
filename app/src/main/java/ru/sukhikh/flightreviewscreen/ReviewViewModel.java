package ru.sukhikh.flightreviewscreen;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ru.sukhikh.flightreviewscreen.Entity.Rating;

public class ReviewViewModel extends ViewModel {

    MutableLiveData<List<Rating>> raringLiveData;
    List<Rating> ratingList;

    public ReviewViewModel() {
        raringLiveData = new MutableLiveData<>();
        init();
    }
    public MutableLiveData<List<Rating>> getUserMutableLiveData() {
        return raringLiveData;
    }

    public void updateLiveData(List<Rating> newList){
        raringLiveData.setValue(newList);
    }

    public void init(){
        populateList();
        raringLiveData.setValue(ratingList);
    }

    public void populateList(){

        ratingList = new ArrayList<>();
        ratingList.add(new Rating(Parameter.PEOPLE, 0, false));
        ratingList.add(new Rating(Parameter.AIRCRAFT, 0, false));
        ratingList.add(new Rating(Parameter.SEAT, 0, false));
        ratingList.add(new Rating(Parameter.CREW, 0, false));
        ratingList.add(new Rating(Parameter.FOOD, 0, false));

    }

}
