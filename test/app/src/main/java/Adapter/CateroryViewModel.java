package Adapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CateroryViewModel extends ViewModel {
    private MutableLiveData<Boolean> dataUpdated = new MutableLiveData<>();

    public LiveData<Boolean> getDataUpdated() {
        return dataUpdated;
    }

    public void setDataUpdated(boolean updated) {
        dataUpdated.setValue(updated);
    }
}
