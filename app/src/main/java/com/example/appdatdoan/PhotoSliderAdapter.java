package com.example.appdatdoan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhotoSliderAdapter extends RecyclerView.Adapter<PhotoSliderAdapter.PhotoViewHolder>{
    private List<PhotoSlider> mListPhoto;

    public PhotoSliderAdapter(List<PhotoSlider> mListPhoto) {
        this.mListPhoto = mListPhoto;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider,parent,false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        PhotoSlider photoSlider = mListPhoto.get(position);
        if(photoSlider == null) return;
        holder.imageView.setImageResource(photoSlider.getResourceID());
    }

    @Override
    public int getItemCount() {
        if(mListPhoto !=  null)
        {
            return mListPhoto.size();
        }
        return 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_photoSlider);
        }
    }
}
