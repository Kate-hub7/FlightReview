package ru.sukhikh.flightreview.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ru.sukhikh.flightreview.Entity.Rating;
import ru.sukhikh.flightreview.Entity.RatingFood;
import ru.sukhikh.flightreview.Enum.Parameter;

public class ReviewViewModel extends ViewModel {

    private MutableLiveData<List<Rating>> ratingLiveData;
    private MutableLiveData<RatingFood> ratingFoodLiveData;
    private List<Rating> ratingList;
    private RatingFood ratingFood;

    public ReviewViewModel() {
        ratingLiveData = new MutableLiveData<>();
        ratingFoodLiveData = new MutableLiveData<>();
        init();
    }
    public MutableLiveData<List<Rating>> getRatingListMutableLiveData() {
        return ratingLiveData;
    }

    public List<Rating> getRatingList(){
        return ratingList;
    }

    private void init(){
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

    public  boolean AvailabilityFood(){
        return ratingFood.isFoodChecked();
    }

    public void updateCheckbox(){

        List<Rating> newList = new ArrayList<>(ratingList);
        newList.set(4, new Rating(Parameter.FOOD, 0, false));
        ratingList = newList;

        ratingFood = new RatingFood(ratingList.get(4), !ratingFood.isFoodChecked());

        ratingLiveData.setValue(ratingList);
        ratingFoodLiveData.setValue(ratingFood);
    }

    private void updateRatingList(int index, Parameter parameter, int newRating){

        List<Rating> newList = new ArrayList<>(ratingList);
        newList.set(index, new Rating(parameter, newRating, false));
        ratingList = newList;

        if(parameter==Parameter.FOOD)
            ratingFood = new RatingFood(ratingList.get(4), ratingFood.isFoodChecked());

        ratingLiveData.setValue(ratingList);
        ratingFoodLiveData.setValue(ratingFood);
    }

    private void populateList(){

        ratingList = new ArrayList<>();
        ratingList.add(new Rating(Parameter.PEOPLE, 0, false));
        ratingList.add(new Rating(Parameter.AIRCRAFT, 0, false));
        ratingList.add(new Rating(Parameter.SEAT, 0, false));
        ratingList.add(new Rating(Parameter.CREW, 0, false));
        ratingList.add(new Rating(Parameter.FOOD, 0, false));
        ratingList.add(new Rating(Parameter.FLIGHT, 0, false));

        ratingFood = new RatingFood(ratingList.get(4), false);
    }

}
