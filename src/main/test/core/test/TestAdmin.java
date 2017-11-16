package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 23/2/2017.
 */
public class TestAdmin {

    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }

    
    //START CLASS MAKING TESTS.
    
    @Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    
    @Test
    public void makeClassCheckCapacity0() {
    	this.admin.createClass("test", 2017, "Instructor", 0);
    	assertFalse(this.admin.classExists("test", 2017));
    }
    
    @Test
    public void makeClassCheckCapacityNegetive() {			
    	this.admin.createClass("test", 2017, "Instructor", -1); 
    	assertFalse(this.admin.classExists("test", 2017));
    }
    
    @Test
    public void testMakeClassFuture() {
        this.admin.createClass("Test", 2020, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2020));
    }
    
    @Test
    public void threeClass1Year1Instructor () {		//should not have more than 2 classes per year.
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test2", 2017, "Instructor", 15);
        this.admin.createClass("Test3", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("Test3", 2017));
    }
    
    @Test
    public void testMakeClassSameNameDifferentInstructor() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test", 2017, "Instructor2", 15);
        assertEquals("Instructor", this.admin.getClassInstructor("Test", 2017));
    }
    //END CLASS MAKING TESTS
    
    //START CLASS CAPACITY CHANGE TESTS
    @Test
    public void changeCapacityLessThanBefore () {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        admin.changeCapacity("Test", 2017, 14);
        assertEquals(15, this.admin.getClassCapacity("Test", 2017));
    }
    
    @Test
    public void changeCapacityMoreThanBefore () {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test", 2017, 16);
        assertEquals(16, this.admin.getClassCapacity("Test", 2017));
    }
    
    @Test
    public void changeCapacitySameAsBefore () {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test", 2017, 15);
     //   assertTrue(this.admin.classExists("Test", 2017));
        assertEquals(15, this.admin.getClassCapacity("Test", 2017));
    }
    
    //END CLASS CAPACITY CHANGE TESTS
    
    
}
