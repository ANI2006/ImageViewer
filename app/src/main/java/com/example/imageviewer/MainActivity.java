package com.example.imageviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView imageView=findViewById(R.id.image);
    private EmployeeAdapter adapter;
    private LinkViewModel employeeViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("UWC", "Activity created");
        //RecyclerView init to display the data
        RecyclerView rv = findViewById(R.id.item_list);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new EmployeeAdapter();
        rv.setAdapter(adapter);

        //Retrieving data from ViewModel and passing to RecyclerView
//        EmployeeViewModel employeeViewModel = new EmployeeViewModel(getApplication());
        employeeViewModel = new ViewModelProvider(this).get(LinkViewModel.class);

        employeeViewModel.getEmployees().observe(this, links ->  {
            Log.i("UWC", "Employees live data changed");
            adapter.setEmployees(links);
        });

        TextView searchBtn = findViewById(R.id.search_btn);
        EditText searchText = findViewById(R.id.search_bar);

        searchBtn.setOnClickListener(v -> {
            employeeViewModel.setSearchFilter(searchText.getText().toString());
        });
    }
    private class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.EmployeeCardHolder> {
        List<LinksEntity> employees;

        public void setEmployees(List<LinksEntity> employees) {
            this.employees = new ArrayList<>(employees);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public LinkAdapter.EmployeeCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            return new LinkAdapter.EmployeeCardHolder(inflater.inflate(R.layout.view_link_item, parent, false));
        }
        @Override
        public void onBindViewHolder(@NonNull LinkAdapter.EmployeeCardHolder holder, int position) {
            holder.id.setText(String.valueOf(employees.get(position).id));
            holder.name.setText(employees.get(position).name);

        }

        @Override
        public int getItemCount() {
            return employees != null ? employees.size() : 0;
        }
        class EmployeeCardHolder extends RecyclerView.ViewHolder {
            TextView id;
            TextView name;
            ImageView delete;

            public EmployeeCardHolder(@NonNull View itemView) {
                super(itemView);
                id = itemView.findViewById(R.id.image);
                name = itemView.findViewById(R.id.employee_name);


                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        employeeViewModel.deleteEmployee(employees.get(getAdapterPosition()));
                    }
                });
            }
        }
    }
    private void loadImg(Intent intent) {
        String action = intent.getAction();
        Uri imageUri = null;
        if (action.equals(Intent.ACTION_SEND)) {
            imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
            if (imageUri != null) {
                Glide.with(this).load(imageUri)
                        .into(imageView);
            }
        }

    }
}