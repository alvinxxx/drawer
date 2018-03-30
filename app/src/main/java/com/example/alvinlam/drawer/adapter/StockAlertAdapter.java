package com.example.alvinlam.drawer.adapter;

/**
 * Created by Alvin Lam on 3/22/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.data.StocklistContract;

import java.util.Locale;

public class StockAlertAdapter extends RecyclerView.Adapter<StockAlertAdapter.StockAlertViewHolder> {

    private static final String TAG = StockAlertAdapter.class.getSimpleName();
    private Context mContext;
    private Cursor mCursor;
    final private ListItemClickListener mOnClickListener;

    String letter;
    ColorGenerator generator = ColorGenerator.MATERIAL;

    public interface ListItemClickListener{
        void onListItemClick(View v, int parameter);
    }

    public StockAlertAdapter(Context context, Cursor cursor, ListItemClickListener listener ) {
        mContext = context;
        mCursor = cursor;
        mOnClickListener = listener;
    }

    @Override
    public StockAlertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.stock_alert_content_list_data, parent, false);
        StockAlertViewHolder viewHolder = new StockAlertViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StockAlertViewHolder holder, int position) {

        if (!mCursor.moveToPosition(position))
            return;
        long id = mCursor.getLong(mCursor.getColumnIndex(StocklistContract.StockAlertEntry._ID));
        int buy = mCursor.getInt(mCursor.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_BUY));

        int code = mCursor.getInt(mCursor.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_CODE));
        String current = mCursor.getString(mCursor.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_INDICATOR));
        String condition = mCursor.getString(mCursor.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_CONDITION));
        String target = mCursor.getString(mCursor.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_TARGET));
        String distance = mCursor.getString(mCursor.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_DISTANCE));

        holder.itemView.setTag(id);
        holder.codeTextView.setText(String.format(Locale.getDefault(), "%04d", code));
        holder.currentTextView.setText(current);
        holder.conditionTextView.setText(condition);
        holder.expectTextView.setText(target);
        holder.distanceTextView.setText(distance);

        //        Get the first letter of list item
        letter = String.valueOf(buy);
        int color = generator.getColor(letter);

        //        Create a new TextDrawable for our image's background
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, color);

        holder.letter.setImageDrawable(drawable);
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        // Update the local mCursor to be equal to  newCursor
        mCursor = newCursor;
        // Check if the newCursor is not null, and call this.notifyDataSetChanged() if so
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }


    class StockAlertViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView codeTextView;
        TextView currentTextView;
        TextView conditionTextView;
        TextView expectTextView;
        TextView distanceTextView;

        ImageView letter;
        Switch alertSwitch;

        public StockAlertViewHolder(View itemView) {
            super(itemView);
            alertSwitch = (Switch) itemView.findViewById(R.id.switchAlert);

            codeTextView = (TextView) itemView.findViewById(R.id.codeATextView);
            currentTextView = (TextView) itemView.findViewById(R.id.currentATextView);
            conditionTextView = (TextView) itemView.findViewById(R.id.conditionATextView);
            expectTextView = (TextView) itemView.findViewById(R.id.expectATextView);
            distanceTextView = (TextView) itemView.findViewById(R.id.distanceATextView);
            letter = (ImageView) itemView.findViewById(R.id.imageViewALetter);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int click = getAdapterPosition();
            mOnClickListener.onListItemClick(v, click);
        }
    }
}
