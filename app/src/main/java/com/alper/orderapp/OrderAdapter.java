package com.alper.orderapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>  {

    List<Model> modelList;
    Context context;

    public OrderAdapter(List<Model> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.listitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nameofDrink = modelList.get(position).getmDrinkName();
        String description = modelList.get(position).getmDrinkDetail();
        int images = modelList.get(position).getmDrinkPhoto();

        holder.mDrinkName.setText(nameofDrink);
        holder.mDrinkDescription.setText(description);
        holder.imageView.setImageResource(images);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mDrinkName, mDrinkDescription;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mDrinkName = itemView.findViewById(R.id.coffeeName);
            mDrinkDescription =itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.coffeeImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position == 0) {
                Intent intent = new Intent(context, InfoActivity.class);
                context.startActivity(intent);
            }

            if(position == 1){
                Intent intent2 = new Intent(context, LatteActivity.class);
                context.startActivity(intent2);
            }

            if(position == 2){
                Intent intent3 = new Intent(context,OrangeSmoothieActivity.class);
                context.startActivity(intent3);
            }

            if(position == 3){
                Intent intent4 = new Intent(context,OrangeVanillaActivity.class);
                context.startActivity(intent4);
            }
            if(position == 4){
                Intent intent5 = new Intent(context,CappucinoActivity.class);
                context.startActivity(intent5);
            }
            if (position==5){
                Intent intent6 = new Intent(context,ThaiTeaActivity.class);
                context.startActivity(intent6);
            }
            if (position == 6){
                Intent intent7 = new Intent(context,TeaActivity.class);
                context.startActivity(intent7);
            }
            if (position == 7){
                Intent intent8 = new Intent(context,BubbleTeaActivity.class);
                context.startActivity(intent8);
            }
            if (position == 8){
                Intent intent9 = new Intent(context,MatchaActivity.class);
                context.startActivity(intent9);
            }
        }
    }
}
