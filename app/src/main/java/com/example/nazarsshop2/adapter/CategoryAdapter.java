package com.example.nazarsshop2.adapter;

import static android.content.Intent.getIntent;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.nazarsshop2.CategoryCreateActivity;
import com.example.nazarsshop2.CategoryEditActivity;
import com.example.nazarsshop2.MainActivity;
import com.example.nazarsshop2.NetworkService;
import com.example.nazarsshop2.R;
import com.example.nazarsshop2.objects.Category;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<Category> categories;
    private MainActivity mainActivity;

    public CategoryAdapter(Context context, List<Category> categories, MainActivity mainActivity) {
        this.context=context;
        this.categories=categories;
        this.mainActivity = mainActivity;
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
        holder.user.setText(categories.get(position).getUser());
        String url = "http://192.168.0.105:5182/images/"+categories.get(position).getImage();
        Glide.with(context).load(url).apply(new RequestOptions().override(500)).into(holder.image);



        int id_ = position;
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                builder.setMessage("Ви впевнені, що хочете видалити категорію # "+categories.get(position).getId()+" ?")
                        .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                NetworkService
                                        .GetNetworkService()
                                        .getApi()
                                        .Delete(categories.get(id_).getId())
                                        .enqueue(
                                                new Callback() {
                                                    @Override
                                                    public void onResponse(Call call, Response response) {
                                                        if(response.isSuccessful()){
                                                            Toast.makeText(context, "Категорію успішно видалено!", Toast.LENGTH_SHORT).show();
                                                            mainActivity.finish();
                                                            context.startActivity(mainActivity.getIntent());

                                                        }

                                                    }

                                                    @Override
                                                    public void onFailure(Call call, Throwable t) {

                                                    }
                                                }
                                        );
                            }
                        })
                        .setNegativeButton("Ні", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                builder.show();


               };

        });
        holder.editBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mainActivity, CategoryEditActivity.class);
                        intent.putExtra("Id",categories.get(id_).getId());
                        intent.putExtra("Name",categories.get(id_).getName());
                        intent.putExtra("Description",categories.get(id_).getDescription());
                        intent.putExtra("Image",url);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mainActivity.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        TextView id,name,description,user;
        ImageView image;
        Button editBtn, deleteBtn;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idView);
            name = itemView.findViewById(R.id.nameView);
            description = itemView.findViewById(R.id.descriptionView);
            user = itemView.findViewById(R.id.userView);
            image = itemView.findViewById(R.id.imageView);
            editBtn = itemView.findViewById(R.id.buttonEdit);
            deleteBtn = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
