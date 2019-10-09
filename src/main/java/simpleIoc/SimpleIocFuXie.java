package simpleIoc;

import com.sun.deploy.model.Resource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleIocFuXie {
    private static final Map<String, Object> beans = new HashMap<String, Object>();

    static {
        try {
            loadBean("simpleIoc.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getBean(String id) {
        return beans.get(id);
    }

    private static void loadBean(String filePath) throws Exception {
        InputStream is = SimpleIocFuXie.class.getClassLoader().getResourceAsStream(filePath);
        //1.获取读对象
        SAXReader reader = new SAXReader();
        //2根据输入流获取文档
        Document document = reader.read(is);
        //3.获取根节点
        Element root = document.getRootElement();
        //4.
        List<Element> beanElementList = root.selectNodes("//bean");
        for (Element beanElement : beanElementList) {
            String id = beanElement.attributeValue("id");
            String className = beanElement.attributeValue("class");
            //反射创建出类，接下来设置属性需要它
            Class beanClass = Class.forName(className);
            //创建实例
            Object bean = beanClass.newInstance();
            //开始处理property标签
            List<Element> propertyElementList = beanElement.selectNodes("property");
            for (Element propertyElement : propertyElementList) {
                String fieldName = propertyElement.attributeValue("name");
                String fieldValue = propertyElement.attributeValue("value");
                System.out.println(beanClass.getName());
                System.out.println(fieldName);
                //根据属性名称获取对应的属性，并给他赋值
                Field field = beanClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                //没有value字段，说明是用的ref。
                if (fieldValue == null) {
                    String ref = propertyElement.attributeValue("ref");
                    field.set(bean, getBean(ref));
                } else {
                    field.set(bean,fieldValue);
                }
            }
            //存入容器中
            beans.put(id,bean);
        }

    }
}
