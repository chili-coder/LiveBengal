package com.sohaghlab.livebengal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sohaghlab.livebengal.R;
import com.sohaghlab.livebengal.RedioViewActivity;
import com.sohaghlab.livebengal.VideoViewActivity;
import com.sohaghlab.livebengal.model.RedioModel;
import com.sohaghlab.livebengal.model.VideoModel;

import java.util.ArrayList;

public class RedioAdapter extends RecyclerView.Adapter<RedioAdapter.redioViewHolder>{

    private Context context;
    private ArrayList<RedioModel> list;

    public RedioAdapter(Context context, ArrayList<RedioModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RedioAdapter.redioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item,parent,false);
        return new RedioAdapter.redioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RedioAdapter.redioViewHolder holder, int position) {

        final  RedioModel currentItem =list.get(position);
        holder.titleText.setText(currentItem.getTitle());

        try {
            if (currentItem.getImage()!=null){
                Glide.with(context).load(currentItem.getImage()).error(R.drawable.logo).into(holder.img);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, RedioViewActivity.class);
                intent.putExtra("url",currentItem.getUrl());
                intent.putExtra("title",currentItem.getTitle());
                intent.putExtra("fmcode",currentItem.getFmcode());
                intent.putExtra("image",currentItem.getImage());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
//
//                Intent intent = new Intent(context, RedioViewActivity.class);
//                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class redioViewHolder extends RecyclerView.ViewHolder {

        private CardView card;
        private TextView titleText,codeText;
        private ImageView img;

        public redioViewHolder(@NonNull View itemView) {
            super(itemView);
            card=itemView.findViewById(R.id.cardV);
            titleText=itemView.findViewById(R.id.titleV);
            img=itemView.findViewById(R.id.imageViewV);
        }
    }
}
