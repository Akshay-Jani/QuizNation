package com.example.shalin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shalin on 31-01-2019.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {


    ArrayList<Pojo> idname = new ArrayList<>();
    private Context Scontext;
    String id;


    public ResultAdapter(Context context, ArrayList<Pojo> iname){

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if(idname.get(position).getDatavalue().equals("0")) {
            holder.question_number.setText(idname.get(position).getId());
            holder.question_number.setBackgroundColor(Color.RED);

        }
        else if(idname.get(position).getDatavalue().equals("1")) {
            holder.question_number.setText(idname.get(position).getId());
            holder.question_number.setBackgroundColor(Color.GREEN);
        }
        else {
            holder.question_number.setText(idname.get(position).getId());
        }

        holder.question_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent q = new Intent(Scontext,Question.class);
                id = idname.get(position).getId();
                q.putExtra("question_index",id);
                Scontext.startActivity(q);

            }
        });
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
