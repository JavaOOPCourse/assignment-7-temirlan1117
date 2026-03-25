import java.io.*;
import java.util.*;
public class StudentRecordProcessor {
    // Поля для хранения данных
    private final List<Student> students = new ArrayList<>();

    // _____реализуйте класс Student ниже в этом же файле______

    private double averageScore;
    private Student highestStudent;


    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() {
        // TODO: реализуйте чтение файла здесь
        try (BufferedReader br = new BufferedReader(new FileReader("input/students.txt"))){
            String line;
            while ((line = br.readLine()) != null){
                try{
                    String [] parts = line.split(",");
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    if (score < 0 || score > 100) {
                        throw new InvalidScoreException("Invalid score");
                    }
                    students.add(new Student(name, score));
                    System.out.println(line);

                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid data: " + line);

                } catch (InvalidScoreException e) {
                    System.out.println("Invalid data: " + line);

                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Invalid data: " + line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");

        } catch (IOException e) {
            System.out.println("I/O error");
        }
    }
            

    /**
     * Task 3 + Task 8
     */
    public void processData() {
        // TODO: обработка данных и сортировка здесь
        if (students.size() == 0) {
            averageScore = 0;
            highestStudent = null;
            return;
        }

        int sum = 0; // сумма всех баллов
        highestStudent = students.get(0);
        for (Student s : students) {
            sum += s.getScore();
            if (s.getScore() > highestStudent.getScore()) {
                highestStudent = s;
            }
        }


        averageScore = (double) sum / students.size();
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student a, Student b) {
                return b.getScore() - a.getScore();
            }
        });
    }

    

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {
        // TODO: запись результата в файл здесь
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("output/report.txt"))){
            bw.write("avarage" + averageScore);
            bw.newLine();
            if (highestStudent != null){
                bw.write("highest" + highestStudent.getName() + highestStudent.getScore());}
                else{
                    bw.write("no high");
                }
            bw.newLine();
            for (Student s : students){
                bw.write(s.getName() + s.getScore());
                bw.newLine();

            }
        }catch (IOException e) {
            // если ошибка записи в файл
            System.out.println("I/O error");
        }

            
        }
    
    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

// class InvalidScoreException реализуйте меня
class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}
// class Student (name, score)
class Student {
    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }


    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}