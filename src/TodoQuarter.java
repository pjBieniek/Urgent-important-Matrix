package src;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TodoQuarter {
    private List<TodoItem> todoItems;

    public TodoQuarter() {
        this.todoItems = new ArrayList<>();
    }

    public void addItem(String title, LocalDate deadline){
        todoItems.add(new TodoItem(title, deadline));
//        todoItems.stream().sorted();
        todoItems.sort(Comparator.comparing(TodoItem::getDeadline));
    }

    public void removeItem(int index){
        this.todoItems.remove(index);
    }
    public void archiveItems(){
        for (int i = 0; i < this.todoItems.size(); i++) {
            this.todoItems.removeIf(todoItem -> todoItem.isDone());
        }
    }
    public TodoItem getItem(int index) {
        return this.todoItems.get(index);
    }

    public List<TodoItem> getItems(){
        return this.todoItems;
    }
    public String toString(){
        int i = 1;
        StringBuilder str = new StringBuilder();
        for (TodoItem next: todoItems) {
            str.append(i + ": " + next.toString() + "\n");
            i++;
        }
        return str.toString();
    }



}