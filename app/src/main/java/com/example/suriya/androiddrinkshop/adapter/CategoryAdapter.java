package com.example.suriya.androiddrinkshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suriya.androiddrinkshop.DrinkActivity;
import com.example.suriya.androiddrinkshop.R;
import com.example.suriya.androiddrinkshop.manager.Common;
import com.example.suriya.androiddrinkshop.model.CategoryModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context mContext;
    private List<CategoryModel> menuList;

    public CategoryAdapter(Context mContext, List<CategoryModel> menuList) {
        this.mContext = mContext;
        this.menuList = menuList;
    }

    public interface IOnClickListener {
        void onClick(View v);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imgProduct;
        public TextView tvName;

        IOnClickListener itemClickListener;

        public void setItemClickListener(IOnClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public CategoryViewHolder(View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName = itemView.findViewById(R.id.tvName);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            itemClickListener.onClick(v);

        }
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_menu_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {

        Picasso.with(mContext)
                .load(menuList.get(position).getLink())
                .into(holder.imgProduct);

        holder.tvName.setText(menuList.get(position).getName());

        holder.setItemClickListener(new IOnClickListener() {
            @Override
            public void onClick(View v) {

                Common.cerrentCategoryModel = menuList.get(position);

                mContext.startActivity(new Intent(mContext, DrinkActivity.class));

            }
        });

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}
