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
public class TestStudent {

    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }
    
    // TEST REGISTER FOR CLASS.
    
    @Test
    public void testRegisteredForClass() {
        this.admin.createClass("class1", 2017, "Instructor1", 15);
        this.student.registerForClass("student1", "class1", 2017);
        assertTrue(this.student.isRegisteredFor("student1", "class1", 2017));
    }
    
    @Test
    public void testClassCapacityMetAndRegistiering() {
        this.admin.createClass("class1", 2017, "Instructor1", 2);
        this.student.registerForClass("student1", "class1", 2017); //student 1
        this.student.registerForClass("student2", "class1", 2017); //student 2
        this.student.registerForClass("student3", "class1", 2017); //student 3
        assertFalse(this.student.isRegisteredFor("student3", "class1", 2017)); //BUG
    }
    
    @Test
    public void testRegisterForClassThatDoesNotExist() {
        this.admin.createClass("class1", 2017, "Instructor1", 15);
        this.student.registerForClass("student1", "class2", 2017);
        assertFalse(this.student.isRegisteredFor("student1", "class2", 2017));
    }
    
    @Test
    public void testRegisterForClassWrongYearInput() {
        this.admin.createClass("class1", 2017, "Instructor1", 15);
        this.student.registerForClass("student1", "class1", 2018);
        assertFalse(this.student.isRegisteredFor("student1", "class1", 2017));
    }
    
    //TEST DROP CLASS.
    
    @Test
    public void testDropClass() {
        this.admin.createClass("class1", 2017, "Instructor1", 15);
        this.student.registerForClass("student1", "class1", 2017);
        this.student.dropClass("student1", "class1", 2017);
        assertFalse(this.student.isRegisteredFor("student1", "class1", 2017));
    }
    
    @Test
    public void testDropWrongClass() {
        this.admin.createClass("class1", 2017, "Instructor1", 15);
        this.admin.createClass("class2", 2017, "Instructor1", 15);
        this.student.registerForClass("student1", "class1", 2017);
        this.student.dropClass("student1", "class2", 2017);
        assertTrue(this.student.isRegisteredFor("student1", "class1", 2017));
    }
    
    
    //TEST SUBMIT HOMEWORK
    
    @Test
    public void testSubmitHomework() {
        this.admin.createClass("class1", 2017, "Instructor1", 15);
        this.student.registerForClass("student1", "class1", 2017);
        this.instructor.addHomework("Instructor1", "class1", 2017, "hw1");
        this.student.submitHomework("student1", "hw1", "eeee", "class1", 2017);
        assertTrue(this.student.hasSubmitted("student1", "hw1", "class1", 2017));
    }
    
    @Test
    public void testSubmitHomeworkStudentNotRegistered() {
        this.admin.createClass("class1", 2017, "Instructor1", 15);
        this.instructor.addHomework("Instructor1", "class1", 2017, "hw1");
        this.student.submitHomework("student1", "hw1", "eeee", "class1", 2017);
        assertFalse(this.student.hasSubmitted("student1", "hw1", "class1", 2017));
    }
    
    @Test
    public void testSubmitHomeworkWrongHomework() {
        this.admin.createClass("class1", 2017, "Instructor1", 15);
        this.student.registerForClass("student1", "class1", 2017);
        this.instructor.addHomework("Instructor1", "class1", 2017, "hw1");
        this.student.submitHomework("student1", "hw2", "eeee", "class1", 2017);
        assertFalse(this.student.hasSubmitted("student1", "hw1", "class1", 2017));
    }
    
}
