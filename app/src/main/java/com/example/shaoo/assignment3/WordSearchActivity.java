package com.example.shaoo.assignment3;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WordSearchActivity extends AppCompatActivity {

    ArrayList<String> words = new ArrayList<String>();
    SearchResultAdapter searchResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_search);
        ListView searchResults = (ListView) findViewById(R.id.results);

        String[] colnames = {"word"};
        Cursor cursor = getContentResolver().query(Uri.parse("content://com.example.provider.Dictionary/words"), colnames, null, null, null);

        assert cursor != null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                words.add(cursor.getString(0));
            }

            cursor.close();
        }
        searchResultAdapter = new SearchResultAdapter(this, words);

        searchResults.setAdapter(searchResultAdapter);

        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openWord(position);
            }
        });

        EditText editText = (EditText) findViewById(R.id.in_word);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchResultAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void openWord(int position) {
        Intent intent = new Intent(this, WordMeaningActivity.class);
        intent.putExtra("word", searchResultAdapter.getItem(position));
        startActivity(intent);

    }
}
