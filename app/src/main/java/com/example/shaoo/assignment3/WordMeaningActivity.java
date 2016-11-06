package com.example.shaoo.assignment3;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordMeaningActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_meaning);

        TextView textView = (TextView) findViewById(R.id.word_name);

        String word = getIntent().getStringExtra("word");
        textView.setText(word);

        ArrayList<String> meanings = new ArrayList<String>();
        String[] colname = {"meaning"};
        String where = "word = " + "'" + word + "'";
        Cursor cursor = getContentResolver().query(Uri.parse("content://com.example.provider.Dictionary/words"), colname, where, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            meanings.add(cursor.getString(0));
            while (cursor.moveToNext()) {
                meanings.add(cursor.getString(0));
            }

            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.search_list_item, meanings);

        ListView listView = (ListView) findViewById(R.id.word_meanings);

        listView.setAdapter(adapter);
    }
}
