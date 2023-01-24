package com.evelyne.labs.myapplication.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.evelyne.labs.myapplication.R;
import com.evelyne.labs.myapplication.model.Upload;


import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>{
    private Context context;
    private List<Upload> uploadsList;
    public BookAdapter(Context context, List<Upload> uploadsLisT) {
        this.context = context;
        this.uploadsList = uploadsLisT;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.fragment_book,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

         Upload uploadcurrent = uploadsList.get(position);

        holder.company.setText(uploadcurrent.getMcompany());
        holder.capacity.setText(uploadcurrent.getMcapacity());
        holder.price.setText(uploadcurrent.getMprice());
        holder.plate.setText(uploadcurrent.getMplate());

       // holder.foodItemDate.setText(booking.getPickUpDate());
        //holder.foodItemNameLocation.setText(booking.getPickUpLocation());

        Glide.with(context).load(uploadcurrent.getMimageUri()).into(holder.imageView);

        holder.approvebooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("APPROVE Booking")
                        .setMessage("Approve current booking?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });


            }
        });



    }

    @Override
    public int getItemCount() {

        return uploadsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView company,capacity,plate,price;
        public ImageView imageView;
        public Button approvebooking;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            company=itemView.findViewById(R.id.descriptionl);
            capacity = itemView.findViewById(R.id.capl);
            plate = itemView.findViewById(R.id.NoPlatel);
            price = itemView.findViewById(R.id.Bpricel);

            imageView = itemView.findViewById(R.id.bookImageView);
            //description = itemView.findViewById(R.id.descriPtion);
           // cap = itemView.findViewById(R.id.cap);
           // price = itemView.findViewById(R.id.Bprice);
            //noplate = itemView.findViewById(R.id.NoPlate);
            //foodItemDate = itemView.findViewById(R.id.foodItemNameDate);
           // foodItemNameLocation = itemView.findViewById(R.id.foodItemNameLocation);
            // bookingImage = itemView.findViewById(R.id.bookImageView);
           // approvebooking = itemView.findViewById(R.id.booksp);

        }
    }
}

