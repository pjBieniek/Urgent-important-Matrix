package src;

import java.io.*;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class TodoMatrix{
    private Map<String, TodoQuarter> todoQuarters;
    private TodoQuarter iu;
    private TodoQuarter in;
    private TodoQuarter nu;
    private TodoQuarter nn;


    public TodoMatrix(){
        this.todoQuarters = new HashMap<>();
        this.todoQuarters.put("IU", iu = new TodoQuarter());
        this.todoQuarters.put("IN", in = new TodoQuarter());
        this.todoQuarters.put("NU", nu = new TodoQuarter());
        this.todoQuarters.put("NN", nn = new TodoQuarter());
    }


    public TodoQuarter getQuarter(String status){
        return todoQuarters.get(status);
    }
    public Map<String, TodoQuarter> getQuarters(){
        return todoQuarters;
    }

    public void addItem(String title, LocalDate deadline, boolean isImportant) {
        LocalDate actualDate = LocalDate.now();
        long days = ChronoUnit.DAYS.between(actualDate, deadline);

        if (isImportant) {
            handleImportantTasks(days, title, deadline);
        } else {
            handleNotImportantTasks(days, title, deadline);
        }
    }

    private void handleImportantTasks(long days, String title, LocalDate deadLine){
        if (days > 3){
            this.getQuarter("IN").addItem(title, deadLine);
        } else {
            this.getQuarter("IU").addItem(title, deadLine);
        }
    }
    private void handleNotImportantTasks(long days, String title, LocalDate deadLine){
        if (days > 3){
            this.getQuarter("NN").addItem(title, deadLine);
        } else {
            this.getQuarter("NU").addItem(title, deadLine);
        }
    }


    public void addItemsFromFile(String fileName) throws IOException {
        try (BufferedReader read = new BufferedReader(new FileReader(fileName))){
            String next;
            while ((next = read.readLine()) != null){
                String[] data = next.split("\\|");
                String name = data[0];
                LocalDate deadLine = dateFromFile(data[1]);
                if (data.length > 2){
                    addItem(name, deadLine, true);
                } else {
                    addItem(name, deadLine, false);
                }
            }
        } finally {
            System.out.println("Data added succesfully from file: " + fileName + "\n");
        }
    }


    public void saveItemsToFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            for (Map.Entry<String, TodoQuarter> entry : this.todoQuarters.entrySet()){
                int i = 0;
                while (i < entry.getValue().getItems().size()) {
                    TodoItem item = entry.getValue().getItem(i);
                    String getCorrectDateFormat = item.getDeadline().getDayOfMonth()
                            + "-" + item.getDeadline().getMonthValue();
                    if (entry.getKey().equals("IU") || entry.getKey().equals("IN")) {
                        writer.write(String.format("%s|%s|important\n", item.getTitle(), getCorrectDateFormat));
                        i++;
                    } else {
                        writer.write(String.format("%s|%s|\n", item.getTitle(), getCorrectDateFormat));
                        i++;
                    }
                }
            }
        }
    }

    public void archiveItems(){
        for (Map.Entry<String, TodoQuarter> entry : this.todoQuarters.entrySet()){
            entry.getValue().archiveItems();
        }
    }

    public String toString(){
        String IU = "Important items & urgent: \n" + this.todoQuarters.get("IU").toString();
        String IN = "Important items & not urgent: \n" + this.todoQuarters.get("IN").toString();
        String NU = "Not Important items & urgent: \n" + this.todoQuarters.get("NU").toString();
        String NN = "Not Important items * not urgent: \n" + this.todoQuarters.get("NN").toString();
        String output = IU + IN + NU + NN;
        return output;
    }

    private LocalDate dateFromFile(String date){
        String[] dayMonth = date.split("-");
        String currentDate = String.format("%s-%s-%s", Year.now().getValue(), dayMonth[1], dayMonth[0]);
        return LocalDate.parse(currentDate);
    }

    private void handleIU(String title, LocalDate deadLine){
        Scanner reader = new Scanner(System.in);
        this.getQuarter("IU").addItem(title, deadLine);
        System.out.println("Important - Urgent task added");
        boolean stopRepeat = false;
        while (!stopRepeat) {

            System.out.println("Add more? y/n: ");
            if (reader.nextLine().equals("n")){
                stopRepeat = true;
            } else {
                handleNewTasks();
            }
        }
    }

    private void handleIN(String title, LocalDate deadLine){
        Scanner reader = new Scanner(System.in);
        this.getQuarter("IN").addItem(title, deadLine);
        System.out.println("Important - Not Urgent task added");
        boolean stopRepeat = false;
        while (!stopRepeat) {
            System.out.println("Add more? y/n: ");
            if (reader.nextLine().equals("n")){
                stopRepeat = true;
            } else {
                handleNewTasks();
            }
        }
    }

    private void handleNU(String title, LocalDate deadLine){
        Scanner reader = new Scanner(System.in);
        this.getQuarter("NU").addItem(title, deadLine);
        System.out.println("Not Important - Urgent task added");
        boolean stopRepeat = false;
        while (!stopRepeat) {
            System.out.println("Add more? y/n: ");
            if (reader.nextLine().equals("n")){
                stopRepeat = true;
            } else {
                handleNewTasks();
            }
        }
    }

    private void handleNN(String title, LocalDate deadLine){
        Scanner reader = new Scanner(System.in);
        this.getQuarter("NN").addItem(title, deadLine);
        System.out.println("Not Important - Not Urgent task added");
        boolean stopRepeat = false;
        while (!stopRepeat) {
            System.out.println("Add more? y/n: ");
            if (reader.nextLine().equals("n")){
                stopRepeat = true;
            } else {
                handleNewTasks();
            }
        }
    }

    public void handleNewTasks(){
        Scanner reader = new Scanner(System.in);
        String title = readTitle();
        LocalDate deadLine = readDate();
        TodoItem newItem = new TodoItem(title, deadLine);

        LocalDate now = LocalDate.now();
        long days = ChronoUnit.DAYS.between(now, deadLine);
        System.out.println("Is the task important? y/n: ");
        String input = reader.nextLine();
        if (input.equals("y")){
            if (days >= 3){
                handleIN(title, deadLine);
            } else {
                handleIU(title, deadLine);
            }
        } else if (input.equals("n")){
            if (days >= 3){
                handleNN(title, deadLine);
            } else {
                handleNU(title, deadLine);
            }
        } else {
            System.out.println("Type correctly! \n");
            handleNewTasks();
        }
    }

    private String readTitle(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter task title: ");
        return reader.nextLine();
    }

    private LocalDate readDate(){
        FormattedDateMatcher matcher = new FormattedDateMatcher();
        Scanner reader = new Scanner(System.in);
        String str;
        do {
            System.out.println("Enter deadline yyyy-MM-dd: ");
            str = reader.nextLine();
        } while (!matcher.matches(str));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deadline = LocalDate.parse(str, formatter);
        return deadline;


    }


}