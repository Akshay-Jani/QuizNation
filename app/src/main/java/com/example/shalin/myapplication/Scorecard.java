package com.example.shalin.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shalin on 31-01-2019.
 */

public class Scorecard extends RecyclerView.Adapter<Scorecard.ViewHolder> {


    ArrayList<Pojo> idname = new ArrayList<>();
    private Context Scontext;


    public Scorecard(Context context, ArrayList<Pojo> iname){

        Scontext = context;
        idname = iname;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.question_number.setText(idname.get(position).getId());

    }

    @Override
    public int getItemCount() {
        return idname.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView question_number;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            question_number =  itemView.findViewById(R.id.questionumber);
        }
    }
}
