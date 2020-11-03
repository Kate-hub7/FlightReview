package ru.sukhikh.flightreview.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.sukhikh.flightreview.Entity.Feedback;


public class FeedbackViewModel extends ViewModel {

    MutableLiveData<Feedback> liveData;
    Feedback feedback;

    public FeedbackViewModel() {
        liveData = new MutableLiveData<>();
        init();
    }
    public MutableLiveData<Feedback> getMutableLiveData() {
        return liveData;
    }

    public void updateLiveData(){
        liveData.setValue(feedback);
    }

    public void updateViewModel(Feedback newList){
        feedback = newList;
    }

    public void init(){
        feedback = new Feedback("");
        liveData.setValue(feedback);
    }

    public Feedback getLiveData(){
        return  liveData.getValue();
    }


}
