import org.junit.Test;
import simpleIoc.Car;
import simpleIoc.SimpleIoc;
import simpleIoc.SimpleIocFuXie;
import simpleIoc.Wheel;

public class SimpleIocTest {
    @Test
    public void getBean() throws Exception{
        Wheel wheel = (Wheel) SimpleIocFuXie.getBean("wheel");
        System.out.println(wheel);
        Car car = (Car) SimpleIocFuXie.getBean("car");
        System.out.println(car);
    }
}
