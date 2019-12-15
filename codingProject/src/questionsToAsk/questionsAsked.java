package questionsToAsk;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.*;



public class questionsAsked {

    ////////////////////////////////        DATA VARIABLES         ////////////////////////////////////////

    //this is the data structure for the questions and answers
    private HashMap<String, ArrayList<String>> trivia_set = new HashMap<>();
    public HashMap <String, String> asnwerss = new HashMap<String, String>();
    //this stores the picked question and its answers
    private String question;
    private ArrayList<String> answers;

    //this holds all of the questions
    private ArrayList<String> question_set = new ArrayList<String>();

    //this holds all of the answers
    private ArrayList<ArrayList<String>> answer_set = new ArrayList<ArrayList<String>>();

    ////////////////////////////////        ANSWER SETS         ////////////////////////////////////////
    
    //uic course answer set
    private ArrayList<String> answer_set_this_class = new ArrayList<String>(
            Arrays.asList("440","401","251","341")
    );
    
    //professors answer set
    private ArrayList<String> answer_set_professors = new ArrayList<String>(
            Arrays.asList("Bell","Hummel","Lillis","Me")
    );

    //privacy answer set
    private ArrayList<String> answer_set_privacy = new ArrayList<String>(
            Arrays.asList("Private","Final","Static","Void")
    );
    private ArrayList<String> answer_set_Forloop = new ArrayList<String>(
            Arrays.asList("Yes it can","Not in a for each loop","No it wont","Posibly")
    );
    private ArrayList<String> answer_set_IfStatement = new ArrayList<String>(
            Arrays.asList("Will run if statement is false","Run if statement is true","Else statement always executes","Return 0 on succes")
    );
    


    ////////////////////////////////        FUNCTIONS         ////////////////////////////////////////

    //default constructor
    public questionsAsked() {
        //push questions into the quesiton set in the respective order to match answers
        create_question_set();

        //push answers into the answer set in the respective order
        create_answer_set();

        //push question and answers into the hashmap
        insert_into_map();
    }
    public void resetQuestions() // redos our questions to be sent out
    {
    	asnwerss.clear();
    	answers.clear();
    	question_set.clear();
    	answer_set.clear();
    	question= "";
    	trivia_set.clear();
    	create_question_set();
        create_answer_set();
        insert_into_map();
    }
    private void create_question_set() {
    	asnwerss.put("What is the number of this CS course?", "440");
    	asnwerss.put("Who is the professor of this CS course?", "Bell");
    	asnwerss.put("What do you put in front of a method so it can't be accessed outside?", "Private");
    	asnwerss.put("Can a for loop cause erors?", "Yes it can");
    	asnwerss.put("What does an If Statement do?", "Run if statement is true");
    	
    	
        question_set.add("What is the number of this CS course?");
        question_set.add("Who is the professor of this CS course?");
        question_set.add("What do you put in front of a method so it can't be accessed outside?");
        question_set.add("Can a for loop cause erors?");
        question_set.add("What does an If Statement do?");
        
    }


    private void create_answer_set() {
        answer_set.add(answer_set_this_class);
        answer_set.add(answer_set_professors);
        answer_set.add(answer_set_privacy);
        answer_set.add(answer_set_Forloop);
        answer_set.add(answer_set_IfStatement);

    }

    //push the questions and answers into the hashmap iteratively
    private void insert_into_map() {
        for (int i=0; i < question_set.size(); i++) {
            trivia_set.put(question_set.get(i), answer_set.get(i));
        }
    }

    //pick the question randomly
    public void roll_question() {
        //first, get the size of the whole array list
        int question_set_size = question_set.size();

        //second, randomly choose one of the index values
        Random random = new Random();

        //finally, set the question to the randomly generated index
        question = question_set.get(random.nextInt(question_set_size));
        answers = trivia_set.get(question);
    }

    public String get_question() {
        return question;
    }

    public ArrayList<String> get_answers() {
        return answers;
    }

}
