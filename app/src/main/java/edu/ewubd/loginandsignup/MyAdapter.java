package edu.ewubd.loginandsignup;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<DataClass> dataList;

    String currentUser;

    public MyAdapter(Context context, List<DataClass> dataList, String currentUser) {
        this.context = context;
        this.dataList = dataList;
        this.currentUser = currentUser;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

//        Showing image
        Glide.with(context).load(dataList.get(position).imgURL1).into(holder.recImage);

//        Showing location information
        holder.recLocation.setText(dataList.get(position).location);
//        Showing Type
        holder.recType.setText(dataList.get(position).type);
//        Showing Rent
        holder.recPhone.setText(dataList.get(position).phone);

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = dataList.get(holder.getAdapterPosition()).username;

                System.out.println(currentUser);

                if (currentUser.equals(user)) {
                    Intent intent = new Intent(context, DetailActivity.class);

                    //                sending images
                    intent.putExtra("Image1", dataList.get(holder.getAdapterPosition()).imgURL1);
                    intent.putExtra("Image2", dataList.get(holder.getAdapterPosition()).imgURL2);
                    intent.putExtra("Image3", dataList.get(holder.getAdapterPosition()).imgURL3);
                    intent.putExtra("Image4", dataList.get(holder.getAdapterPosition()).imgURL4);

//              Rest of the data

                    intent.putExtra("location", dataList.get(position).location);
                    intent.putExtra("type", dataList.get(position).type);
                    intent.putExtra("phone", dataList.get(position).phone);
                    intent.putExtra("rent", dataList.get(position).rent);
                    intent.putExtra("size", dataList.get(position).size);
                    intent.putExtra("description", dataList.get(position).description);
                    intent.putExtra("latitude", dataList.get(position).latitude);
                    intent.putExtra("longitude", dataList.get(position).longitude);
                    intent.putExtra("boost", dataList.get(position).boost);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, NormalDetailsActivity.class);

                    //                sending images
                    intent.putExtra("Image1", dataList.get(holder.getAdapterPosition()).imgURL1);
                    intent.putExtra("Image2", dataList.get(holder.getAdapterPosition()).imgURL2);
                    intent.putExtra("Image3", dataList.get(holder.getAdapterPosition()).imgURL3);
                    intent.putExtra("Image4", dataList.get(holder.getAdapterPosition()).imgURL4);

//              Rest of the data

                    intent.putExtra("location", dataList.get(position).location);
                    intent.putExtra("type", dataList.get(position).type);
                    intent.putExtra("phone", dataList.get(position).phone);
                    intent.putExtra("rent", dataList.get(position).rent);
                    intent.putExtra("size", dataList.get(position).size);
                    intent.putExtra("description", dataList.get(position).description);
                    intent.putExtra("latitude", dataList.get(position).latitude);
                    intent.putExtra("longitude", dataList.get(position).longitude);
                    intent.putExtra("boost", dataList.get(position).boost);

                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<DataClass> searchList) {
        dataList = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView recImage;
    TextView recTitle, recDesc, recLang;
    TextView recLocation, recType, recPhone;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);

        recLocation = itemView.findViewById(R.id.recLocation);
        recType = itemView.findViewById(R.id.recType);
        recPhone = itemView.findViewById(R.id.recPhone);


    }
}
