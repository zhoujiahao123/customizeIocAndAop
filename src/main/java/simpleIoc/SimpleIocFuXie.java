package simpleIoc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SimpleIocFuXie {
    Map<String, Object> beanMap = new HashMap<String, Object>();

    public SimpleIocFuXie(String fileLocation) throws Exception {
        loadBeans(fileLocation);
    }

    private void loadBeans(String fileLocation) throws Exception {
        InputStream in = SimpleIocFuXie.class.getClassLoader().getResourceAsStream(fileLocation);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(in);
        Element element = doc.getDocumentElement();
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                Element beanElement = (Element) node;
                String id = beanElement.getAttribute("id");
                String className = beanElement.getAttribute("class");
                //反射创建类
                Class beanClass = null;
                beanClass = Class.forName(className);
                Object bean = beanClass.newInstance();
                //获取类的属性值并注入
                NodeList propertyList = beanElement.getElementsByTagName("property");
                for (int j = 0; j < propertyList.getLength(); j++) {
                    Node propertyNode = propertyList.item(j);
                    if (propertyNode instanceof Element) {
                        Element propertyElement = (Element) propertyNode;
                        String name = propertyElement.getAttribute("name");
                        String value = propertyElement.getAttribute("value");
                        Field declaredField = beanClass.getDeclaredField(name);
                        declaredField.setAccessible(true);
                        if (value != null && value.length() > 0) {
                            declaredField.set(bean, value);
                        } else {
                            String ref = propertyElement.getAttribute("ref");
                            if (ref == null || ref.length() == 0) {
                                throw new IllegalArgumentException("参数错误");
                            }else {
                                declaredField.set(bean,getBean(ref));
                            }
                        }
                        registerBean(id,bean);
                    }
                }
            }
        }
    }
    public Object getBean(String id){
        return beanMap.get(id);
    }
    private void registerBean(String id,Object bean){
        beanMap.put(id,bean);
    }
}
