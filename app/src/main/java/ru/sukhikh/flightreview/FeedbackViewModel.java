package ru.sukhikh.flightreview;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



public class FeedbackViewModel extends ViewModel {

    MutableLiveData<Feedback> raringLiveData;
    Feedback ratingList;

    public FeedbackViewModel() {
        raringLiveData = new MutableLiveData<>();
        init();
    }
    public MutableLiveData<Feedback> getUserMutableLiveData() {
        return raringLiveData;
    }

    public void updateLiveData(){
        raringLiveData.setValue(ratingList);
    }

    public void updateViewModel(Feedback newList){
        ratingList = newList;
    }

    public void init(){
        populateList();
        raringLiveData.setValue(ratingList);
    }

    public Feedback get(){
        return  raringLiveData.getValue();
    }

    public void populateList(){

        ratingList = new Feedback("");

    }

}
