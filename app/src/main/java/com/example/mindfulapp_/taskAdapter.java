package com.example.mindfulapp_;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



class taskAdapter extends RecyclerView.Adapter<taskAdapter.ViewHolder> {


     private OnTaskListener monTaskListener;
     List<tasktable> tasktables;

    public taskAdapter(List<tasktable> tasktables,OnTaskListener monTaskListener) {
        this.tasktables = tasktables;
        this.monTaskListener = monTaskListener;
    }

    @NonNull
     @Override
     public taskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);


        return new ViewHolder(view,monTaskListener);


     }

     @Override
     public void onBindViewHolder(@NonNull taskAdapter.ViewHolder holder, int position) {

        holder.title1.setText(tasktables.get(position).getTask_desc());

    if(tasktables.get(position).isTask_status()){

        holder.imageView.setImageResource(R.drawable.checked);

    }
    else{
        holder.imageView.setImageResource(R.drawable.unchecked);

    }


     }

     @Override
     public int getItemCount() {
         return tasktables.size();
     }



     public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title1;
        OnTaskListener mOnTaskListener;
        ImageView imageView;


         public ViewHolder(@NonNull View itemView, OnTaskListener mOnTaskListener) {
             super(itemView);


             title1 = itemView.findViewById(R.id.taskText);
             imageView = itemView.findViewById(R.id.checkbutton);
             this.mOnTaskListener = mOnTaskListener;
                itemView.setOnClickListener(this);




         }

         @Override
         public void onClick(View v) { mOnTaskListener.onTaskClick(getAdapterPosition());

         }
     }


     public interface OnTaskListener{

        void onTaskClick(int position);
     }



 }
