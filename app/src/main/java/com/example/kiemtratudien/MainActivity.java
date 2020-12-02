package com.example.kiemtratudien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    static LinkedList<TuVung> lst_word ;
    WordListAdapter adapter;
    EditText editSearch;
    Button btnSearch;
    String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView= findViewById(R.id.recycleView);
        editSearch= findViewById(R.id.editSearch);
        btnSearch= findViewById(R.id.btnSearch);

        try {
            jsonString= new APIGetting(this).execute("1").get();

            if(get_lst_word(jsonString)){

                adapter= new WordListAdapter(lst_word, this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
            else{
                recyclerView.setVisibility(View.INVISIBLE);
                editSearch.setVisibility(View.INVISIBLE);
                btnSearch.setVisibility(View.INVISIBLE);

            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public Boolean get_lst_word(String js){
        lst_word= new LinkedList<>();

        try {
            JSONArray jsonArray= new JSONArray(js);

            int num= jsonArray.length();
            for(int i=0; i<num; i++){
                JSONObject jsonObject= jsonArray.getJSONObject(i);
                TuVung word= new TuVung();

                word.setWord(jsonObject.getString("word"));
                word.setDefinition(jsonObject.getString("definition"));

                lst_word.add(word);
            }
            return  true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void Search(View view) {
        editSearch= findViewById(R.id.editSearch);
        String result= editSearch.getText().toString();

        result= checkTuVung(result);

        if(checkLength(result)) {
            TuVung tuVung= new TuVung(result, "");
            try {
                jsonString= new APISearch(this).execute(tuVung).get();

                if(get_lst_word(jsonString)){

                    adapter= new WordListAdapter(lst_word, this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                }
                else{
                    recyclerView.setVisibility(View.INVISIBLE);
                    editSearch.setVisibility(View.INVISIBLE);
                    btnSearch.setVisibility(View.INVISIBLE);

                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
            Toast.makeText(this, "Cannot find the property result", Toast.LENGTH_SHORT).show();

    }

    public static String checkTuVung(String tuVung){
        tuVung= tuVung.replaceAll(" ", "");

        return tuVung;
    }
    public static boolean checkLength(String tuVung){
        if(tuVung.length()<3)
            return false;
        else return  true;
    }
}