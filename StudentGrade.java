import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
class student{
    private String Name;
    private int Rollno,grade;
    public student(String Name,int Rollno,int grade){
        this.Name=Name;
        this.Rollno=Rollno;
        this.grade=grade;
    }
    public String getname(){
        return Name;
    }
    public int getRollno(){
        return Rollno;
    }
    public int getGrade(){
        return grade;
    }  
}
public class StudentGrade extends Frame{
    private TextField NameField,RollnoField,GradeField;
    private TextArea DisplayArea;
    private ArrayList<student> students = new ArrayList<>();

    public StudentGrade(){
        setTitle("Student Grade");
        setSize(500, 500);
        setLayout(new BorderLayout());
        // These are fields that use in the GUI
        Panel input_panel=new Panel(new GridLayout(3, 2, 5, 5));
        input_panel.add(new Label("Student Name:"));
        NameField=new TextField();
        input_panel.add(NameField);        
        input_panel.add(new Label("Student Roll NUMBER:"));
        RollnoField = new TextField();
        input_panel.add(RollnoField);
        input_panel.add(new Label("Student Grade:"));
        GradeField = new TextField();
        input_panel.add(GradeField);
        add(input_panel, BorderLayout.NORTH);

        DisplayArea = new TextArea();
        DisplayArea.setEditable(false);
        add(DisplayArea, BorderLayout.CENTER);
        Panel file_panel = new Panel(); 
        Button Add_student=new Button("Add Student");
        Button show_student=new Button("Show Student");
        Button save_Button = new Button("Save to File");
        Button load_Button = new Button("Load File");
        file_panel.add(Add_student);
        file_panel.add(show_student);
        file_panel.add(save_Button);
        file_panel.add(load_Button);

        add(file_panel, BorderLayout.SOUTH);
        // display area 
        
        
        
        Add_student.addActionListener(e -> addstudent());
        show_student.addActionListener(e -> showstudent());
        save_Button.addActionListener(e ->savestudent());
        load_Button.addActionListener(e ->loadstudent());
        
        //close windows
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
    private void addstudent(){
        String Name=NameField.getText().trim();
        String Rolltext=RollnoField.getText().trim();
        String Gradetext=GradeField.getText().trim();
        if(Name.isEmpty()||Rolltext.isEmpty()||Gradetext.isEmpty()){
             Message("Please fill all fields.");
            return;
        }
        try {
            int grade = Integer.parseInt(Gradetext);
            int roll =Integer.parseInt(Rolltext);
            students.add(new student(Name,roll,grade));
            DisplayArea.append(Name + " - " + roll +" - "+ grade+"\n");
            NameField.setText("");
            GradeField.setText("");
            RollnoField.setText("");
        } catch (NumberFormatException ex) {
            Message("Enter valid numbers for roll number and grade.");

        }
    }
    private void showstudent(){
        if(students.isEmpty()){
            Message("There is no student");
            return;
        }
        double total=0;
        double high=students.get(0).getGrade();
        double low= students.get(0).getGrade();
        for(student s:students){
            double g=s.getGrade();
            total+=s.getGrade();
            if(g>high){
                high=g;
            }
            if(g<low){
                low=g;
            }
        }
        double avg = total / students.size();
        Message(
            "Total Students: " + students.size() +"\nAverage Grade: " + String.format("%.2f", avg) +"\nHighest Grade: " + high + "\nLowest Grade: " + low
        );
    }
    private void savestudent(){
        try(PrintWriter file_writter = new PrintWriter(new FileWriter("studentlist.txt"));){
            for(student s: students){
                file_writter.println(s.getname()+','+s.getGrade()+','+s.getRollno());
            }
            Message("Sucessfully Saved");
        }catch(IOException e){
            Message("File saving error");
        }
            
    }
    private void loadstudent(){
        students.clear();
        DisplayArea.setText("");
        try(BufferedReader br=new BufferedReader(new FileReader("studentlist.txt"))){
        String word;
        while((word=br.readLine())!=null){
            String[] p = word.split(",");
            if(p.length==3){
                students.add(new student(p[0], Integer.parseInt(p[2]), Integer.parseInt(p[1])));
                DisplayArea.append(p[0] + " - " + p[2] + " - " + p[1] + "\n");
            }
        }
            Message("File loaded successfully");
        }catch(IOException e){
            Message("File Not Found");
        }
    }
    private void Message(String msg){
        Dialog d= new Dialog(this,"Message",true);
        d.setLayout(new BorderLayout());
        d.add(new Label(msg),BorderLayout.CENTER);
        Button Ok= new Button("OK");
        Ok.addActionListener(e -> d.dispose());
        d.add(Ok , BorderLayout.SOUTH);
        d.setSize(300, 150);
        d.setLocationRelativeTo(this);
        d.setVisible(true);

    }


    public static void main(String[] args) {
        StudentGrade sg=new StudentGrade();
        sg.setVisible(true);
    }
}
