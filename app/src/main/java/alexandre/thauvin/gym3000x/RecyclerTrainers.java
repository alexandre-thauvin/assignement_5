package alexandre.thauvin.gym3000x;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerTrainers extends RecyclerView.Adapter<alexandre.thauvin.gym3000x.RecyclerTrainers.ViewHolder> {

    private final List<Trainer> mValues;
    private final TrainersFragment.OnListFragmentInteractionListener mListener;

    public RecyclerTrainers(List<Trainer> items, TrainersFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public alexandre.thauvin.gym3000x.RecyclerTrainers.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trainer_item, parent, false);
        return new alexandre.thauvin.gym3000x.RecyclerTrainers.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final alexandre.thauvin.gym3000x.RecyclerTrainers.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mName.setText(mValues.get(position).getName());
        holder.mQualification.setText(mValues.get(position).getQualification());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mName;
        public final TextView mQualification;
        public Trainer mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mQualification = view.findViewById(R.id.qualification);
            mName =  view.findViewById(R.id.name);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mQualification.getText() + "'";
        }
    }
}
