package com.adibu.shipmonitoring.viewmodel;

import com.adibu.shipmonitoring.model.KeyValueModel;
import com.adibu.shipmonitoring.model.WarningModel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<KeyValueModel>> mData;
    private MutableLiveData<List<WarningModel>> mWarningData;

    public MutableLiveData<List<KeyValueModel>> getData() {
        if (mData == null) {
            mData = new MutableLiveData<>();
            List<KeyValueModel> coolingData = new ArrayList<>();
            mData.setValue(coolingData);
        }
        return mData;
    }

    public MutableLiveData<List<WarningModel>> getWarningData() {
        if(mWarningData == null) {
            mWarningData = new MutableLiveData<>();
            List<WarningModel> warningData = new ArrayList<>();
            mWarningData.setValue(warningData);
        }
        return mWarningData;
    }

}
