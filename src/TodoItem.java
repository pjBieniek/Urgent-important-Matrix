package src;


import java.time.LocalDate;

public class TodoItem {
    private String title;
    private LocalDate deadline;
    private Boolean isDone;

    public TodoItem(String title, LocalDate deadline){
        this.title = title;
        this.deadline = deadline;
        this.isDone = false;
    }

    public String getTitle(){
        return this.title;
    }
    public LocalDate getDeadline(){
        return this.deadline;
    }
    public Boolean isDone(){
        return isDone;
    }
    public void mark(){
        isDone = true;
    }
    public void unmark(){
        isDone = false;
    }
    public String toString(){
        StringBuilder str = new StringBuilder();

        if (this.isDone){
            str.append("[x] " + Integer.toString(deadline.getDayOfMonth()) + "-" + Integer.toString(deadline.getMonthValue()) + " " + title);
            return str.toString();
        } else {
            str.append("[ ] " + Integer.toString(deadline.getDayOfMonth()) + "-" + Integer.toString(deadline.getMonthValue()) + " " + title);
            String output = str.toString();
            return output;
        }
    }
}