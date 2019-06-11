package src;


public class Main {
    public static void main(String[] args) {
        TodoMatrix todoMatrix = new TodoMatrix();
        Engine controller = new Engine();
        controller.runApp(todoMatrix);
    }
}