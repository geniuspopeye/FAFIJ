package com.example.fafij.presentation.bottomnavigation.categories;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fafij.R;
import com.example.fafij.databinding.FragmentCategoriesBinding;
import com.example.fafij.models.data.Category;
import com.example.fafij.models.data.postbodies.CategoryLoginJournal;
import com.example.fafij.models.data.postbodies.NoteLoginJournal;
import com.example.fafij.presentation.addcategory.AddCategoryActivity;

import java.util.ArrayList;


public class CategoriesFragment extends Fragment implements CategoriesContract.CategoriesViewInterface {

    FragmentCategoriesBinding binding;
    CategoriesPresenter presenter;

    public CategoriesFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding = FragmentCategoriesBinding.inflate(getLayoutInflater());
        presenter = new CategoriesPresenter(this);
        loadCategories();
        binding.addCategoriesButton.setOnClickListener(view -> goToAddCategory());
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCategories();
    }

    /**
     * Перенаправляет на экран добавления категории
     */
    public void goToAddCategory() {
        startActivity(new Intent(getActivity(), AddCategoryActivity.class));
    }

    @Override
    public void showCategories(ArrayList<Category> categories) {
        RecyclerView recyclerView = binding.recyclerViewCategories;
        CAdapter adapter = new CAdapter(getContext(), categories, presenter);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public CategoryLoginJournal getData(String category) {
        SharedPreferences sp = requireActivity()
                .getApplicationContext()
                .getSharedPreferences("mainStorage", Context.MODE_PRIVATE);
        return new CategoryLoginJournal(category,sp.getString("login", ""), sp.getString("journalName", ""));
    }

    @Override
    public void loadCategories() {
        SharedPreferences sp = requireActivity()
                .getApplicationContext()
                .getSharedPreferences("mainStorage", Context.MODE_PRIVATE);
        presenter.onLoad(sp.getString("journalName", ""));
    }

    @Override
    public void showToast(int code) {

    }

    @Override
    public void showToastException(String e) {

    }
}