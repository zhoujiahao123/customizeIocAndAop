import org.junit.Test;
import simpleIoc.Car;
import simpleIoc.SimpleIoc;
import simpleIoc.SimpleIocFuXie;
import simpleIoc.Wheel;

public class SimpleIocTest {
    @Test
    public void getBean() throws Exception{
        String location = "simpleIoc.xml";
        SimpleIocFuXie simpleIoc = new SimpleIocFuXie(location);
        Wheel wheel = (Wheel) simpleIoc.getBean("wheel");
        System.out.println(wheel);
        Car car = (Car) simpleIoc.getBean("car");
        System.out.println(car);
    }
}
