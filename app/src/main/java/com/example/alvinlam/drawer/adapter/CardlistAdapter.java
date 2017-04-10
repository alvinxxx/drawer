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
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.data.CardlistContract;

public class CardlistAdapter extends RecyclerView.Adapter<CardlistAdapter.CardViewHolder> {

    private static final String TAG = CardlistAdapter.class.getSimpleName();
    private Context mContext;
    private Cursor mCursor;
    final private ListItemClickListener mOnClickListener;
    String letter;
    ColorGenerator generator = ColorGenerator.MATERIAL;

    public interface ListItemClickListener{
        void onListItemClick(View v, int parameter);
    }

    public CardlistAdapter(Context context, Cursor cursor, ListItemClickListener listener ) {
        mContext = context;
        mCursor = cursor;
        mOnClickListener = listener;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.main_content_list_data, parent, false);
        CardViewHolder viewHolder = new CardViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {

        if (!mCursor.moveToPosition(position))
            return;
        String name = mCursor.getString(mCursor.getColumnIndex(CardlistContract.CardlistEntry.COLUMN_NAME));
        String title = mCursor.getString(mCursor.getColumnIndex(CardlistContract.CardlistEntry.COLUMN_TITLE));
        long id = mCursor.getLong(mCursor.getColumnIndex(CardlistContract.CardlistEntry._ID));
        holder.nameTextView.setText(name);
        holder.titleTextView.setText(title);
        holder.itemView.setTag(id);

        //        Get the first letter of list item
        letter = String.valueOf(name.charAt(0));
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


    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTextView;
        TextView titleTextView;
        ImageView letter;

        public CardViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            letter = (ImageView) itemView.findViewById(R.id.imageViewLetter);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int click = getAdapterPosition();
            mOnClickListener.onListItemClick(v, click);
        }
    }
}
