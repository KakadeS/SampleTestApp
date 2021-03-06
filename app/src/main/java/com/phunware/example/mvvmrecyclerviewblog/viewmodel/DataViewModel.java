/*
 * Copyright (c) 2018 Phunware Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.phunware.example.mvvmrecyclerviewblog.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.phunware.example.mvvmrecyclerviewblog.BR;
import com.phunware.example.mvvmrecyclerviewblog.adapter.DataAdapter;
import com.phunware.example.mvvmrecyclerviewblog.helper.ApiInterface;
import com.phunware.example.mvvmrecyclerviewblog.helper.RetrofitClass;
import com.phunware.example.mvvmrecyclerviewblog.model.CountryListModel;
import com.phunware.example.mvvmrecyclerviewblog.model.DataModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Gregory Rasmussen on 7/26/17.
 */
public class DataViewModel extends BaseObservable {
    private static final String TAG = "DataViewModel";
    private DataAdapter adapter;
    private List<DataModel> data;

    public DataViewModel() {
        data = new ArrayList<>();
        adapter = new DataAdapter();
    }

    public void setUp() {
        // perform set up tasks, such as adding listeners, data population, etc.
        populateData();
    }

    public void tearDown() {
        // perform tear down tasks, such as removing listeners
    }

    @Bindable
    public List<DataModel> getData() {
        return this.data;
    }

    @Bindable
    public DataAdapter getAdapter() {
        return this.adapter;
    }

    private void populateData() {
        // populate the data from the source, such as the database.

        ApiInterface api = RetrofitClass.getRetrofitObject().create(ApiInterface.class);
        Call<CountryListModel> call = api.getCountryDetails();
        call.enqueue(new Callback<CountryListModel>() {
            @Override
            public void onResponse(Call<CountryListModel> call, Response<CountryListModel> response) {
                Log.e("response=", String.valueOf(response.body()));
                for (int i = 0; i < response.body().getRows().size(); i++) {
                    data.add(response.body().getRows().get(i));
                }
            }

            @Override
            public void onFailure(Call<CountryListModel> call, Throwable t) {
            }
        });
        notifyPropertyChanged(BR.data);



//        for (int i = 0; i < 50; i++) {
//            DataModel dataModel = new DataModel();
//            dataModel.setTitle(String.valueOf(i));
//            data.add(dataModel);
//        }
//        notifyPropertyChanged(BR.data);
    }
}
