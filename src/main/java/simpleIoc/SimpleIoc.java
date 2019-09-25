package simpleIoc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * 最简单的IOC容器由以下四步形成
 * 1.加载xml文件
 * 2.获取其中的id与class通过class反射创建类并创建bean
 * 3.获取属性值，将属性值填充到bean钟
 * 4.将bean注入我们的IOC容器中
 */
public class SimpleIoc {
    //创建容器
    Map<String, Object> beanMap = new HashMap<String, Object>();

    public SimpleIoc(String fileLocation) throws Exception {
        loadBeans(fileLocation);
    }

    /**
     * 加载xml文件读取数据并放入bean容器当
     * @param fileLocation
     */
    private void loadBeans(String fileLocation) throws Exception {
        //读取资源文件
        InputStream in = SimpleIoc.class.getClassLoader().getResourceAsStream(fileLocation);
        //创建xml解析对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(in);
        //返回文档根元素:beans?
        Element root = doc.getDocumentElement();
        //返回beans的子节点bean
        NodeList nodes = root.getChildNodes();
        //遍历bean标签
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                //获取id与全限定类名
                String id = ele.getAttribute("id");
                String className = ele.getAttribute("class");

                //通过全限定类名反射创建该类
                Class beanClass = null;
                beanClass = Class.forName(className);
                //获取该类的实例，后面需要该实例获取对象的属性
                Object bean = beanClass.newInstance();


                //遍历<property>
                NodeList propertyNodes = ele.getElementsByTagName("property");
                for (int j = 0; j < propertyNodes.getLength(); j++) {
                    Node propertyNode = propertyNodes.item(j);
                    if (propertyNode instanceof Element) {
                        Element propertyElement = (Element) propertyNode;
                        String name = propertyElement.getAttribute("name");
                        String value = propertyElement.getAttribute("value");

                        //利用反射获取类中定义的所有属性
                        Field declareField = beanClass.getDeclaredField(name);
                        declareField.setAccessible(true);
                        //属性注入
                        if (value != null && value.length() > 0) {
                            declareField.set(bean, value);
                        } else {
                            String ref = propertyElement.getAttribute("ref");
                            if (ref == null || ref.length() == 0) {
                                throw new IllegalArgumentException("ref config error");
                            }else {
                                declareField.set(bean,getBean(ref));
                            }
                        }
                        registerBean(id, bean);
                    }
                }
            }
        }
    }

    private void registerBean(String id, Object bean) {
        beanMap.put(id, bean);
    }

    public Object getBean(String className) {
        return beanMap.get(className);
    }

}
