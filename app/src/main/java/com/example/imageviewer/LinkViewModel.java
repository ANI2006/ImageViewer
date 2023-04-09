package com.example.imageviewer;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import java.util.List;
import java.util.stream.Collectors;

public class LinkViewModel extends AndroidViewModel {
    private MutableLiveData<List<LinksEntity>> employees;
    private AppDatabase appDatabase;
    private String nameFilter;

    public LinkViewModel(@NonNull Application application) {
        super(application);
        Log.i("UWC", "ViewModel created");
        employees = new MutableLiveData<>();

        AsyncTask.execute(() -> {
            Log.i("UWC", "ViewModel thread started DB reading");
            refreshEmployeeList();
        });
    }

    public LiveData<List<LinksEntity>> getEmployees() {
        return employees;
    }


    public void addEmployee(String name) {
        Log.i("UWC", "Adding new link: " + name);
        //DB access must be done from background thread
        AsyncTask.execute(() -> {
            appDatabase.imageDao().insertEmployee(new LinksEntity(name));
            refreshEmployeeList();
        });
    }

    public void deleteEmployee(LinksEntity links){
        AsyncTask.execute(() -> {
            appDatabase.imageDao().deleteEmployee(links);
            refreshEmployeeList();
        });
    }

    public void setSearchFilter(String f) {
        nameFilter = f.toLowerCase();
        AsyncTask.execute(() -> {
            refreshEmployeeList();
        });
    }
    private void refreshEmployeeList() {
        List<LinksEntity> all = appDatabase.imageDao().getAll();
        if(nameFilter != null && !nameFilter.isEmpty()) {
            List<LinksEntity> filteredEmployees = all.stream().filter(employee -> employee.getName().toLowerCase().contains(nameFilter)).collect(Collectors.toList());
            employees.postValue(filteredEmployees);
        } else {
            employees.postValue(all);
        }
    }
}