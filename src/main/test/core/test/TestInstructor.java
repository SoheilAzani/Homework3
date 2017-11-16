package core.test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 23/2/2017.
 */
public class TestInstructor {

    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }

    @Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }


    //TESTS FOR addHomework
    
    @Test
    public void testAddHomework() {
        this.admin.createClass("class1", 2017, "Instructor1", 15);
        this.instructor.addHomework("Instructor1", "class1", 2017, "hwName");
        assertTrue(this.instructor.homeworkExists("class1", 2017, "hwName"));
    }
    
    @Test
    public void testAddHomeworkInstructorNotAssignedToThatClass() {
        this.admin.createClass("class1", 2017, "Instructor1", 15);
        this.admin.createClass("class2", 2017, "Instructor2", 15);
        this.instructor.addHomework("Instructor1", "class2", 2017, "hwName");
        assertFalse(this.instructor.homeworkExists("class2", 2017, "hwName")); //bug
    }
    
    @Test
    public void testAddHomeworkCheckWrongName() {
        this.admin.createClass("class1", 2017, "Instructor1", 15);
        this.instructor.addHomework("Instructor1", "class1", 2017, "hwName");
        assertFalse(this.instructor.homeworkExists("class2", 2017, "hwWrongName"));
    }
    
    @Test
    public void testAddHomeworkCheckWrongYear() {
        this.admin.createClass("class1", 2017, "Instructor1", 15);
        this.admin.createClass("class2", 2018, "Instructor1", 15);
        this.instructor.addHomework("Instructor1", "class1", 2017, "hwName");
        assertFalse(this.instructor.homeworkExists("class1", 2018, "hwName"));
    }    
    
    //END TESTS FOR addHomework
    
    
    //TEST ASSIGN GRADE
    
    @Test
    public void testAssignGrade() {
        this.admin.createClass("class1", 2017, "Instructor1", 15);
        this.student.registerForClass("student1", "class1", 2017);
        this.instructor.addHomework("Instructor1", "class1", 2017, "hw1");
        this.student.submitHomework("student1", "hw1", "eeee", "class1", 2017);
        this.instructor.assignGrade("Instructor1", "class1", 2017, "hw1", "student1", 100);
        assertSame(100, this.instructor.getGrade("class1", 2017, "hw1", "student1"));
        }
    
    @Test
    public void testAssignGradeZero() {
        this.admin.createClass("class1", 2017, "Instructor1", 15);
        this.student.registerForClass("student1", "class1", 2017);
        this.instructor.addHomework("Instructor1", "class1", 2017, "hw1");
        this.student.submitHomework("student1", "hw1", "eeee", "class1", 2017);
        this.instructor.assignGrade("Instructor1", "class1", 2017, "hw1", "student1", 0);
        assertSame(0, this.instructor.getGrade("class1", 2017, "hw1", "student1"));
        }
    
    @Test
    public void testAssignNegativeGrade() {
        this.admin.createClass("class1", 2017, "Instructor1", 15);
        this.student.registerForClass("student1", "class1", 2017);
        this.instructor.addHomework("Instructor1", "class1", 2017, "hw1");
        this.student.submitHomework("student1", "hw1", "eeee", "class1", 2017);
        this.instructor.assignGrade("Instructor1", "class1", 2017, "hw1", "student1", -2);
        assertNull(this.instructor.getGrade("class1", 2017, "hw1", "student1"));
        }
    
    @Test
    public void testAssignGradeWrongInstructor() {
        this.admin.createClass("class1", 2017, "Instructor1", 15);
        this.student.registerForClass("student1", "class1", 2017);
        this.instructor.addHomework("Instructor1", "class1", 2017, "hw1");
        this.student.submitHomework("student1", "hw1", "eeee", "class1", 2017);
        this.instructor.assignGrade("Instructor2", "class1", 2017, "hw1", "student1", 100);
        assertNull(this.instructor.getGrade("class1", 2017, "hw1", "student1")); 
        }
    
    @Test
    public void testAssignGradeWhenHomeworkNotAssigned() { //testing hw submission and grading when no homework exists
        this.admin.createClass("class1", 2017, "Instructor1", 15);
        this.student.registerForClass("student1", "class1", 2017);
        this.student.submitHomework("student1", "hw1", "eeee", "class1", 2017);
        this.instructor.assignGrade("Instructor1", "class1", 2017, "hw1", "student1", 100);
        assertNull(this.instructor.getGrade("class1", 2017, "hw1", "student1")); 
        }
    
    @Test
    public void testAssignGradeWhenStudentHasNotSubmittedHomework() {
        this.admin.createClass("class1", 2017, "Instructor1", 15);
        this.student.registerForClass("student1", "class1", 2017);
        this.instructor.addHomework("Instructor1", "class1", 2017, "hw1");
        this.instructor.assignGrade("Instructor2", "class1", 2017, "hw1", "student1", 100);
        assertNull(this.instructor.getGrade("class1", 2017, "hw1", "student1"));
        }
    
}
