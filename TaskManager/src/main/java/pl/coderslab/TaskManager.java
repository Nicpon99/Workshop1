package pl.coderslab;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import static pl.coderslab.ConsoleColors.*;

public class TaskManager {
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] tasks;
    public static void main(String[] args) {
        run();

    }


    public static void run(){
        tasks = readFile("tasks.csv");
        options(OPTIONS);
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()){
            String input = scan.nextLine().toLowerCase();
            switch (input){
                case "add":
                    add();
                    break;
                case "remove":
                    remove(removeNumber());
                    break;
                case "list":
                    printList();
                    break;
                case "exit":
                    save("tasks.csv");
                    System.out.println(PURPLE_BOLD_BRIGHT + "Bye, bye." + RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(BLUE_BOLD + "Please select a correct option" + RESET);
            }
        }
    }

    public static void options(String[] tab){
        System.out.println(BLUE_BOLD +  "Please select an option" + RESET);
        for (String option : tab) {
            System.out.println(WHITE_BRIGHT + option + RESET);
        }
    }
    public static String[][] readFile(String fileName){
        int count = 0;
        File file = new File(fileName);
        StringBuilder sb = new StringBuilder();
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()){
                sb.append(scan.nextLine() + "\n");
                count++;
            }
        } catch (FileNotFoundException e){
            System.out.println("No File");
        }
        Scanner scan1 = new Scanner(sb.toString());
        String line = scan1.nextLine();
        String[] split = line.split(", ");
        String[][] tab = new String[count][split.length];
        Scanner scan2 = new Scanner(sb.toString());
        String line1;
        for (int i = 0; i < tab.length; i++){
            line1 = scan2.nextLine();
            String[] array = line1.split(", ");
            for (int j = 0; j < array.length; j++){
                tab[i][j] = array[j];
            }
        }
        return tab;
    }
    public static void printList(){
        if (tasks.length == 0){
            System.out.println(RED + "Your task tab is empty, use \"add\" option and add your first task" + RESET);
            return;
        } else{
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + "  ");
            }
            System.out.println();
        }
        }
    }
    public static String[][] add(){
        System.out.println(BLUE + "Please add task description" + RESET);
        Scanner scan = new Scanner(System.in);
        String task = scan.nextLine();
        System.out.println(BLUE + "Please add task due date" + RESET);
        String date = scan.nextLine();
        String important = "";
        while (!(important.toLowerCase().equals("true") || important.toLowerCase().equals("false"))){
            System.out.println(BLUE + "Is your task is important: true/false" + BLUE);
            important = scan.nextLine();
        }
        tasks = ArrayUtils.add(tasks, new String[]{task, date, important});
        System.out.println(CYAN + "Your task was successfully added" + RESET);
        return tasks;
    }
    public static int removeNumber(){
        System.out.println(BLUE + "Please select number to remove" + RESET);
        Scanner scan = new Scanner(System.in);
        int number;
        while(!scan.hasNextInt()){
            scan.nextLine();
            System.out.println(RED + "Incorrect argument passed. Please give number greater or equal 0" + RESET);
            }
        return number = scan.nextInt();

    }
    public static void remove(int number){
        try{
            tasks = ArrayUtils.remove(tasks, number);
            System.out.println(CYAN + "Value was successfully deleted" + RESET);
        } catch (IndexOutOfBoundsException e){
            System.out.println(RED + "Element not exists in tab, please try again use \"remove\" option or \"add\" option if your task tab is empty" + RESET);
        }
    }
    public static void save(String fileName){
        StringBuilder sb = new StringBuilder();
        String line;
        for (int i = 0; i < tasks.length; i++){
            line = "";
            for (int j = 0; j < tasks[i].length; j++){
                if(j < 2){
                    tasks[i][j] = tasks[i][j] + ", ";
                }
                line = line + tasks[i][j];
            }
            sb.append(line).append("\n");
        }
        try(FileWriter fileWriter = new FileWriter(fileName, false)) {
            fileWriter.append(sb.toString());
        } catch (IOException e){
            System.out.println(RED + "Error writing to file" + RESET);
        }
    }
}

