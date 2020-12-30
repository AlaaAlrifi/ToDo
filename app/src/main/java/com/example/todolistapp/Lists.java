package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolistapp.Adapters.AddNewListViewModel;
import com.example.todolistapp.Adapters.ListViewModel;
import com.example.todolistapp.Adapters.ListsAdapter;

import java.util.ArrayList;

public class Lists extends AppCompatActivity {
    EditText createList;
    RecyclerView rv;
    EditText search;
    AddNewListViewModel mViewModel;
    ListsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        rv = findViewById(R.id.list_rv);
        search = findViewById(R.id.search);
        createList = findViewById(R.id.creatlist);

        mViewModel = ViewModelProviders.of(this).get(AddNewListViewModel.class);

        ListViewModel mlistViewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        mlistViewModel.getAllLists().observe(this, lists -> {
            Toast.makeText(Lists.this, "on changed work ", Toast.LENGTH_SHORT).show();

            adapter = new ListsAdapter((ArrayList<com.example.todolistapp.Models.Lists>) lists, position -> {
                Toast.makeText(Lists.this, "clicked ", Toast.LENGTH_SHORT).show();
                Intent addTask = new Intent(getApplicationContext(), Category.class);
                addTask.putExtra("categoryID", lists.get(position).getId());
                addTask.putExtra("categoryName", lists.get(position).getListname());
                startActivity(addTask);
            });

            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setHasFixedSize(true);
            rv.setAdapter(adapter);
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(search.getText().toString());
            }
        });


    }



    public void back(View view) {
        startActivity(new Intent(getApplicationContext(), LogIn.class));
    }

    public void saveToDatabase(View view) {
        String categ = createList.getText().toString();
        if (TextUtils.isEmpty(categ)) {
            createList.setError("Enter List Name");

        } else {
            mViewModel.insert(new com.example.todolistapp.Models.Lists(categ));
        }


    }
}