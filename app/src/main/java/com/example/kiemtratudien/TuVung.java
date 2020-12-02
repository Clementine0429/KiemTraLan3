package com.example.kiemtratudien;

import androidx.annotation.NonNull;

public class TuVung {
    private String word;
    private String definition;

    public TuVung(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    public TuVung() {
        this.word = "";
        this.definition = "";
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @NonNull
    @Override
    public String toString() {
        return this.word+ " "+ this.definition;
    }

}
