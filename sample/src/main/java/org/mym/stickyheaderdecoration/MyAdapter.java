package org.mym.stickyheaderdecoration;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mym.ui.decoration.library.Section;
import org.mym.ui.decoration.library.StickyHeaderAdapter;

import java.util.List;

public class MyAdapter extends StickyHeaderAdapter<MyAdapter.ItemHolder, MyAdapter.HeaderHolder> {
    private List<?> itemList;
    private List<? extends Section> sectionList;

    public MyAdapter(List<? extends Section> sectionList) {
        super(sectionList);
        this.sectionList = sectionList;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.textView.setText(itemList.get(position).toString() + position);
    }

    @Override
    public void returnItemList(List<?> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_header, parent, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(@NonNull HeaderHolder holder, int headerPosition) {
        holder.textView.setText(sectionList.get(headerPosition).getHeader().toString());
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ItemHolder(View itemView) {
            super(itemView);
            textView = ((TextView) itemView.findViewById(R.id.item_textView));
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public HeaderHolder(View itemView) {
            super(itemView);
            textView = ((TextView) itemView.findViewById(R.id.header_textView));
        }
    }
}
