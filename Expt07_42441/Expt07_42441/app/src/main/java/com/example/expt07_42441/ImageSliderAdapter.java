package com.example.expt07_42441;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder> {
    private final int[] images;

    public ImageSliderAdapter(int[] images) {
        this.images = images;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_slider, parent, false);
        return new ImageSliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    static class ImageSliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ImageSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlider);
        }
    }
}