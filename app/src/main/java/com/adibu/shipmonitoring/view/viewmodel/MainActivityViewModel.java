package com.adibu.shipmonitoring.view.viewmodel;

import com.adibu.shipmonitoring.model.KeyValueModel;
import com.adibu.shipmonitoring.model.WarningModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<KeyValueModel>> mCoolingData;
    private MutableLiveData<List<WarningModel>> mWarningData;

    public MutableLiveData<List<KeyValueModel>> getCoolingData() {
        if (mCoolingData == null) {
            mCoolingData = new MutableLiveData<>();
            List<KeyValueModel> coolingData = new ArrayList<>();
            mCoolingData.setValue(coolingData);
        }
        return mCoolingData;
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
