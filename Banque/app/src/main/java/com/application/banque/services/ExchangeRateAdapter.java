package com.application.banque.services;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.banque.R;
import com.application.banque.models.ExchangeRate;

import java.util.List;

public class ExchangeRateAdapter extends RecyclerView.Adapter<ExchangeRateAdapter.ViewHolder> {

    private List<ExchangeRate> exchangeRates;

    public ExchangeRateAdapter(List<ExchangeRate> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exchange_rate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExchangeRate exchangeRate = exchangeRates.get(position);

        // Set initial alpha to 0 for fading effect
        holder.itemView.setAlpha(0f);

        holder.currencyTextView.setText(exchangeRate.getCurrency());
        holder.rateTextView.setText(String.valueOf(exchangeRate.getRate()));

        // Apply fade-in and scale-up animation
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(holder.itemView, "alpha", 0f, 1f);
        fadeIn.setDuration(500);
        fadeIn.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0.5f, 1f);
        scaleX.setDuration(500);
        scaleX.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0.5f, 1f);
        scaleY.setDuration(500);
        scaleY.setInterpolator(new DecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(fadeIn, scaleX, scaleY);
        animatorSet.start();
    }

    @Override
    public int getItemCount() {
        return exchangeRates.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView currencyTextView;
        TextView rateTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            currencyTextView = itemView.findViewById(R.id.currencyTextView);
            rateTextView = itemView.findViewById(R.id.rateTextView);
        }
    }
}
