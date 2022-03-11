package com.android.todo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    ArrayList<Model> list;
    Context context;

    public ListAdapter(ArrayList<Model> list, Context context){
        this.list = list;
        this.context= context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.task_view, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Model model = list.get(position);
        holder.taskTitle.setText(list.get(position).title);
        holder.taskDesc.setText(model.description);
        boolean isVisible = model.visibility;
        holder.linearLayout.setVisibility(isVisible?View.VISIBLE:View.GONE);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                model.setDone(isChecked);
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle;
        TextView taskDesc;
        Button deleteItem;
        CheckBox checkBox;
        LinearLayout linearLayout;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            linearLayout= itemView.findViewById(R.id.content_desc);
            taskTitle = itemView.findViewById(R.id.textView);
            taskDesc = itemView.findViewById(R.id.description);
            deleteItem = itemView.findViewById(R.id.delete_item);

            taskTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Model model = list.get(getAdapterPosition());
                    model.setVisibility(!model.isVisibility());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Model m = list.get(getAdapterPosition());
                    list.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    Log.d("ListAdapter","HLEO"+m.toString());
                        ((MainActivity)context).deleteFromFile(m);

                 }
            });
        }
    }

}
