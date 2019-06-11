package src;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Engine {
    private TodoMatrix myMatrix;
    private MatrixTable table;

    public void runApp(TodoMatrix matrix){
        this.myMatrix = matrix;
        this.table = new MatrixTable(myMatrix);
        handleUserInteraction(myMatrix);

    }

    private void handleUserInteraction(TodoMatrix matrix){
        System.out.println("\nChoose option: ");
        System.out.println("[ 1 ] New matrix \n[ 2 ] Show matrix \n[ 0 ] quit");
        System.out.println("Enter number in range 0-2:");
        String input = new Scanner(System.in).nextLine();
        boolean correctAnswer = false;
        do {
            if (input.equals("1")) {

                correctAnswer = true;
            } else if (input.equals("2")) {
                System.out.println("Enter file name (.csv): ");
                input = new Scanner(System.in).nextLine();
                try {
                    matrix.addItemsFromFile(input);
                    handleSubMenu1(matrix);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                    handleUserInteraction(matrix);
                }
                correctAnswer = true;
            } else if (input.equals("0")) {
                System.exit(0);
            } else {
                System.out.println("type correctly ! \n");
                handleUserInteraction(matrix);
            }
        } while (!correctAnswer);
    }
    private void handleSubMenu2(TodoMatrix matrix){
        String status;
        System.out.println("\n[ 1 ] Important and Urgent\n" +
                "[ 2 ] Important and NotUrgent \n" +
                "[ 3 ] Not Important and Urgent \n" +
                "[ 4 ] Not Important and Not Urgent \n" +
                "[ 0 ] back");
        System.out.println("Enter number in range 0-4:");
        String input = new Scanner(System.in).nextLine();
        boolean correctAnswer = false;
        while (!correctAnswer) {
            if (input.equals("1")) {
                status = "IU";
                if (matrix.getQuarter(status).getItems().isEmpty()){
                    System.out.println("Empty matrix");
                } else {
                    System.out.println(matrix.getQuarter(status).toString());
                }
                handleSubMenu2(matrix);
            } else if (input.equals("2")) {
                status = "IN";
                if (matrix.getQuarter(status).getItems().isEmpty()){
                    System.out.println("Empty matrix");
                } else {
                    System.out.println(matrix.getQuarter(status).toString());
                }
                handleSubMenu2(matrix);
            } else if (input.equals("3")) {
                status = "NU";
                if (matrix.getQuarter(status).getItems().isEmpty()){
                    System.out.println("Empty matrix");
                } else {
                    System.out.println(matrix.getQuarter(status).toString());
                }
                handleSubMenu2(matrix);
            } else if (input.equals("4")) {
                status = "NN";
                if (matrix.getQuarter(status).getItems().isEmpty()){
                    System.out.println("Empty matrix");
                } else {
                    System.out.println(matrix.getQuarter(status).toString());
                }
                handleSubMenu2(matrix);
            } else if (input.equals("0")) {
                handleSubMenu1(matrix);
            } else {
                System.out.println("type correctly ! ");
                handleSubMenu2(matrix);
            }

        }
    }
    private void handleSubMenu1(TodoMatrix matrix){
        System.out.println("\n[ 1 ] display status \n" +
                "[ 2 ] add new\n" +
                "[ 3 ] mark task\n" +
                "[ 4 ] unmark task\n" +
                "[ 5 ] remove task\n" +
                "[ 6 ] remove done tasks\n" +
                "[ 7 ] save\n" +
                "[ 8 ] display matrix\n" +
                "[ 0 ] back\n");
        System.out.println("Enter number in range 0-8:");
        String input = new Scanner(System.in).nextLine();
        boolean correctAnswer = false;
        while (!correctAnswer) {
            if (input.equals("1")) {
                handleSubMenu2(matrix);
                break;
            } else if (input.equals("2")) {
                handleNewTask();
                break;
            } else if (input.equals("3")) {
                handleMarking(true, matrix);
                break;
            } else if (input.equals("4")) {
                handleMarking(false, matrix);
                break;
            } else if (input.equals("5")) {
                handleRemove(matrix);
                break;
            } else if (input.equals("6")) {
                matrix.archiveItems();
                System.out.println("Archived succesfully\n");
                break;
            } else if (input.equals("7")) {
                handleSave(matrix);
                break;
            } else if (input.equals("8")) {
                table.displayTable(matrix);
                break;
            } else if (input.equals("0")) {
                handleUserInteraction(matrix);
            } else {
                System.out.println("type correctly ! \n ");
                handleSubMenu1(matrix);
            }
        }

    }

    private void handleMarking(boolean mark, TodoMatrix matrix){
        System.out.println("Enter task title: ");
        String input = new Scanner(System.in).nextLine();
        String[] statusTable = {"IU", "IN", "NU", "NN"};
        for (String element : statusTable){
            TodoQuarter currentQuarter = matrix.getQuarter(element);
            List<TodoItem> currentTasksList = currentQuarter.getItems();
            int i = 0;
            for (TodoItem next : currentTasksList){
                if (input.equals(next.getTitle())){
                    if (mark == true){
                        matrix.getQuarter(element).getItem(i).mark();
                        System.out.println("Marked succesfully\n");
                    } else if (mark == false){
                        matrix.getQuarter(element).getItem(i).unmark();
                        System.out.println("Umarked succesfully\n");
                    }
                }
                i++;
            }
        }
        System.out.println("\n");
    }

    private void handleRemove(TodoMatrix matrix) {
        System.out.println("Enter task title: ");
        String input = new Scanner(System.in).nextLine();
        String[] statusTable = {"IU", "IN", "NU", "NN"};
        for (String element : statusTable) {
            TodoQuarter currentQuarter = matrix.getQuarter(element);
            List<TodoItem> currentTasksList = currentQuarter.getItems();
            int i = 0;
            for (TodoItem next : currentTasksList) {
                if (input.equals(next.getTitle())) {
                    matrix.getQuarter(element).removeItem(i);
                    System.out.println(input +" Removed succesfully\n");
                }
                i++;
            }
        }

    }

    private void handleNewTask(){
        myMatrix.handleNewTasks();
    }

    private void handleSave(TodoMatrix matrix){
        System.out.println("Enter file name: ");
        String input = new Scanner(System.in).nextLine();
        try {
           matrix.saveItemsToFile(input);
        }catch (IOException e) {
            System.out.println(e.getMessage());
            handleSave(matrix);
        } finally {
            System.out.println("Saved succesfully to " + input + "\n");
        }

    }
}
