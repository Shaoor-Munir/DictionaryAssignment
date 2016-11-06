package com.example.shaoo.assignment3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

import static java.security.AccessController.getContext;

/**
 * Created by Shaoor Munir on 05-Nov-16.
 */

public class SearchResultAdapter extends ArrayAdapter<String> implements Filterable{

    private Context mContext;
    private Filter filter;
    private ArrayList<String> words;
    private ArrayList<String> filteredWords =  new ArrayList<String>();

    SearchResultAdapter(Context c, ArrayList<String> words)
    {

        super(c, 0, words);
        this.words = words;
        this.mContext = c;
    }

    @Override
    public int getCount() {
        return filteredWords.size();
    }

    @Override
    public String getItem(int position) {
        return  filteredWords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        String string = (String) getItem(position);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.search_list_item, parent, false);
        }

        ((TextView)convertView).setText(string);
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new WordFilter();
        }
        return filter;
    }

    private class WordFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            if(constraint != null && constraint.length()>0)
            {
                ArrayList<String> filteredResults = new ArrayList<String>();
                for(int i=0;i<words.size();i++)
                {
                    if(words.get(i).toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        filteredResults.add(words.get(i));
                    }
                }

                results.count = filteredResults.size();
                results.values = filteredResults;
            }
            else
            {
                results.count = 0;
                results.values = new ArrayList<String>();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredWords = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }
}
