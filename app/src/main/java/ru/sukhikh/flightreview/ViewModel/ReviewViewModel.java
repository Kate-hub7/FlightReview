package ru.sukhikh.flightreview.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ru.sukhikh.flightreview.Entity.Rating;
import ru.sukhikh.flightreview.Enum.Parameter;

public class ReviewViewModel extends ViewModel {

    MutableLiveData<List<Rating>> ratingLiveData;
    List<Rating> ratingList;

    public ReviewViewModel() {
        ratingLiveData = new MutableLiveData<>();
        init();
    }
    public MutableLiveData<List<Rating>> getUserMutableLiveData() {
        return ratingLiveData;
    }

    public Rating getFlightData(){return ratingList.get(ratingList.size()-1);}

    public void updateLiveData(List<Rating> newList){
        ratingLiveData.setValue(newList);
    }

    public void init(){
        populateList();
        ratingLiveData.setValue(ratingList);
    }

    public void onRatingBarChanged(Parameter parameter, int newRating){

        int index=0;
        for(int i=0;i<ratingList.size();i++)
            if(ratingList.get(i).getParameter().equals(parameter)){
                index = i;
                break;
            }

        updateRatingList(index, parameter, newRating);
    }

    public void onOverallRatingChanged(int newRating){

        updateRatingList(ratingList.size()-1, Parameter.FLIGHT, newRating);
    }

    private void updateRatingList(int index, Parameter parameter, int newRating){

        List<Rating> newList = new ArrayList<>(ratingList);
        newList.set(index, new Rating(parameter, newRating, false));
        ratingList = newList;
        Log.d("mytag", ratingList.toString());
        ratingLiveData.setValue(ratingList);
    }

    public void populateList(){

        ratingList = new ArrayList<>();
        ratingList.add(new Rating(Parameter.PEOPLE, 0, false));
        ratingList.add(new Rating(Parameter.AIRCRAFT, 0, false));
        ratingList.add(new Rating(Parameter.SEAT, 0, false));
        ratingList.add(new Rating(Parameter.CREW, 0, false));
        ratingList.add(new Rating(Parameter.FOOD, 0, false));
        ratingList.add(new Rating(Parameter.FLIGHT, 0, false));
    }

}
