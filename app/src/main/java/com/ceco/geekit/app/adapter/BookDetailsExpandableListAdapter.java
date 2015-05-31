package com.ceco.geekit.app.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ceco.geekit.R;

import java.util.AbstractMap;
import java.util.List;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 30 May 2015
 */
public class BookDetailsExpandableListAdapter extends BaseExpandableListAdapter {

    private static final int CHILDREN_COUNT = 1;

    private Context context;

    private List<AbstractMap.SimpleEntry<String, String>> bookDetailsPair;

    public BookDetailsExpandableListAdapter(Context context, List<AbstractMap.SimpleEntry<String, String>> bookDetailsPair) {
        this.context = context;
        this.bookDetailsPair = bookDetailsPair;
    }

    @Override
    public int getGroupCount() {
        return bookDetailsPair.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return CHILDREN_COUNT;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.bookDetailsPair.get(groupPosition).getKey();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.bookDetailsPair.get(groupPosition).getValue();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.book_details_exp_list_item, parent, false);
        }
        TextView bookDetailsHeader = (TextView) convertView
                .findViewById(R.id.book_details_exp_list_header);
        bookDetailsHeader.setTypeface(null, Typeface.BOLD);
        bookDetailsHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String detailsValue = (String) getChild(groupPosition,childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.book_details_exp_child_list_item, parent, false);
        }

        TextView bookDetailsHeaderValue = (TextView) convertView
                .findViewById(R.id.book_details_child_list_item_value_holder);
        bookDetailsHeaderValue.setText(detailsValue);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
