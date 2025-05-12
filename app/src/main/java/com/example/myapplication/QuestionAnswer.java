package com.example.myapplication;

public class QuestionAnswer {

    public static String question[] ={
            "What is a numerator?",
            "What is a denominator? ",
            "What is 7/20 + 5/20 ?",
            "What is 20/30 - 5/30 ?"
    };

    public static String choices[][] = {
            {"The number above the line in a fraction", "the number below the line in a fraction", "A machine", "the gruffallos cat"},
            {"The number below the line in a fraction","the number above the line in a fraction","A type of dinosaur","A unit of measurement for angles"},
            {"35/40","12/40","2/20","12/20"},
            {"4/30","100/900","15/30","25/30"}
    };

    public static String correctAnswers[] = {
            "The number above the line in a fraction",
            "The number below the line in a fraction",
            "12/20",
            "15/30"
    };

}
