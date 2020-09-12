package com.turorial.boardleader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class LearnersAdapter extends RecyclerView.Adapter<LearnersAdapter.LearnersView> {

    private List<Student> students   ;
    private Context mContext;

    public LearnersAdapter(List<Student> students, Context mContext) {
        this.students = students;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public LearnersView onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list,viewGroup,false);
        return new LearnersView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LearnersView holder, int position) {
        Picasso.get().load(students.get(position).getBadgeUrl()).into(holder.imageView);

        holder.name.setText(students.get(position).getName());
        holder.hours.setText(students.get(position).getHours());
        holder.country.setText(students.get(position).getCountry());

        holder.currentPosition = position;
    }

    @Override
    public int getItemCount() {

        return students.size();
    }

    public class LearnersView extends  RecyclerView.ViewHolder{

        public TextView name ;
        public TextView  hours ;
        public TextView country ;
        public ImageView imageView;

        private int currentPosition ;

        public LearnersView(@NonNull View itemView) {
            super(itemView);
            name =(TextView)itemView.findViewById(R.id.names);
            hours = (TextView)itemView.findViewById(R.id.hours);
            country =(TextView)itemView.findViewById(R.id.country);
            imageView =(ImageView) itemView.findViewById(R.id.imageView);
          /*  itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,NoteListActivity.class);
                    intent.putExtra("student",articles.get(currentPosition));
                    mContext.startActivity(intent);
                }
            });*/
        }
    }
}
