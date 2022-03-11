package com.android.todo;

public class Model implements java.io.Serializable{
    String title;
    String description;
    boolean visibility;
    boolean done;

    public Model(String title, String description) {
        this.title = title;
        this.description = description;
        this.visibility = false;
        this.done = false;
    }

    boolean isDone() {return  done; }

    void setDone(boolean done)
    {
        this.done = done;
    }

    boolean isVisibility()
    {
        return visibility;
    }

    void setVisibility(boolean visibility)
    {
        this.visibility = visibility;
    }

    @Override
    public String toString(){
        return "Title:"+title+"\nDescription:"+description+"\nVisibility:"+visibility+"\nDone:"+done+"\n";
    }
}
