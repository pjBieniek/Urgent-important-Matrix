package src;

import java.util.Map;

public class MatrixTable {
    private TodoMatrix myMatrix;
    private int urgentWidth;
    private int notUrgentWidth;

    public MatrixTable(TodoMatrix matrix){
        this.myMatrix = matrix;
        this.urgentWidth = 10;
        this.notUrgentWidth = 10;
    }

    public void displayTable(TodoMatrix matrix){
        getWidth(myMatrix);
        TodoQuarter topLeft = matrix.getQuarter("IU");
        TodoQuarter topRight = matrix.getQuarter("IN");
        TodoQuarter downLeft = matrix.getQuarter("NU");
        TodoQuarter downRight = matrix.getQuarter("NN");

        handleTop();
        displayPart(topLeft, topRight, "IMPORTANT");
        handleBottom();
        displayPart(downLeft, downRight, "NOT IMPORTANT");
        handleBottom();
    }

    private void displayPart(TodoQuarter left, TodoQuarter right, String isImportant){
        int count = 0;
        int l = left.getItems().size();
        int r = right.getItems().size();
        int max = Math.max(l, r);
        for (int i=0; i < Math.max(isImportant.length(), max); i++){
            char[] titleHandler = isImportant.toCharArray();
            if (i<titleHandler.length){
                System.out.print(titleHandler[i] + "| ");
            } else {
                System.out.print(" | ");
            }
            String format = "%-" + urgentWidth + "s| " + "%-" + notUrgentWidth + "s| " + "%n";
            if (count<Math.min(l, r)){
                System.out.printf(format, left.getItem(count), right.getItem(count));
                count++;
            } else if (l < r && count < r){
                System.out.printf(format, "", right.getItem(count));
                count++;
            } else if (l > r && count < l){
                System.out.printf(format, left.getItem(count), "");
                count++;
            } else {
                System.out.printf(format, "", "");
            }
        }
    }

    private void handleTop(){
        String breakLine = "-".repeat(urgentWidth+notUrgentWidth);
        System.out.print(" | ");
        String headers = "%-" + urgentWidth + "s| " + "%-" + notUrgentWidth + "s| " + "%n";
        System.out.printf(headers, "URGENT", "NOT URGENT");
        System.out.println(breakLine);
    }

    private void handleBottom(){
        String breakLine = "-".repeat(urgentWidth+notUrgentWidth);
        System.out.println(breakLine);
    }

    private void getWidth(TodoMatrix matrix){
        for (Map.Entry<String, TodoQuarter> entry : matrix.getQuarters().entrySet()){
            boolean leftCol = entry.getKey().equals("IU") || entry.getKey().equals("NU");
            boolean rightCol = entry.getKey().equals("IN") || entry.getKey().equals("NN");

            for (TodoItem item : entry.getValue().getItems()){
                int length1 = item.getDeadline().toString().length();
                int length2 = item.getTitle().length();
                if (this.urgentWidth < length1+length2 && leftCol){
                    this.urgentWidth = length1+length2;
                } else if (this.notUrgentWidth < length1+length2 && rightCol){
                    this.notUrgentWidth = length1+length2;
                }
            }
        }
    }
}
