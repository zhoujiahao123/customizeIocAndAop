import org.junit.Test;
import simpleIoc.Car;
import simpleIoc.SimpleIoc;
import simpleIoc.SimpleIocFuXie;
import simpleIoc.Wheel;

public class SimpleIocTest {
    @Test
    public void getBean() throws Exception{

        Wheel wheel = (Wheel) SimpleIoc.getBean("wheel");
        System.out.println(wheel);
        Car car = (Car) SimpleIoc.getBean("car");
        System.out.println(car);
    }
}
