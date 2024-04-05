package com.example.nazarsshop2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nazarsshop2.R;
import com.example.nazarsshop2.objects.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context=context;
        this.categories=categories;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.category_item,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        System.out.println(categories.get(position).getId()+" - "+ categories.get(position).getName()+" - "+ categories.get(position).getDescription());
        holder.id.setText(String.valueOf(categories.get(position).getId()));
        holder.name.setText(categories.get(position).getName());
        holder.description.setText(categories.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        TextView id,name,description;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idView);
            name = itemView.findViewById(R.id.nameView);
            description = itemView.findViewById(R.id.descriptionView);
        }
    }
}
