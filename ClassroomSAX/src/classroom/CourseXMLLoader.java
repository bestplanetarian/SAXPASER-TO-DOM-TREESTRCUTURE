/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classroom;

import java.io.File;
import java.util.Stack;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 *
 * @author Shiqi Wang
 * http://www.saxproject.org/apidoc/org/xml/sax/helpers/DefaultHandler.html
 * https://docs.oracle.com/javase/8/docs/api/org/xml/sax/SAXException.html#SAXException--
 */
public class CourseXMLLoader {
    private CourseXMLLoader(){}
    public static void parse(File file,isresulthandler resultHandler) throws Exception {
        
       
            String newline = System.lineSeparator();
            
            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            StringBuilder string = new StringBuilder();
            
            DefaultHandler handle = new DefaultHandler(){
                 Stack<String> stack = new Stack<>();    

                 public void startElement(String uri, String localname, String qName, Attributes attributes) throws SAXException{
                    stack.push(qName);
                    string.append(getIntent(stack.size()-1));
                    string.append(qName).append(newline);
                    for(int i = 0; i < attributes.getLength(); i++){
                        string.append(getIntent(stack.size()-1));
                        string.append(attributes.getQName(i) + "=" + attributes.getValue(i)).append(newline);
                        }
                   resultHandler.handle(string.toString());
                }
                 
                public void endElement(String uri,String localname, String qName) throws SAXException{
                    if(!stack.isEmpty() && qName.equals(stack.peek())){
                        stack.pop();
                    }
                }
                public void characters(char[] ch, int start, int length) throws SAXException{
                    String str = new String(ch, start, length);
                    if(!"".equals(str.trim())){
                        string.append(getIntent(stack.size()));
                        string.append(" = " + str).append(newline);
                         }
                    resultHandler.handle(string.toString());
                }
                
      };
            
        saxParser.parse(file, handle);
    }
    private static String getIntent(int count){
        StringBuilder string2 = new StringBuilder();
        for (int i = 0; i < count; i++) {
            string2.append("   ");
            }
        return string2.toString();
    }
}


