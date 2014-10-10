//package org.jware.ClassBuilder;

import java.io.PrintStream;
import java.lang.reflect.*;

/**
 *
 * @author Paul Jackson<jjacks@email.cpcc.edu>
 */
public class ReflectTest {
    
    static PrintStream out = System.out;
    
    public  static void doPrint() {
        
      try {
          Class<?> c = Class.forName( "java.applet.Applet" );
          Method[] methods = c.getDeclaredMethods();
          
          for (int ndx=0; ndx<methods.length; ndx++ ) {
              System.out.println(methods[ndx].getName());
          }
      }
      catch(ClassNotFoundException | SecurityException e) {e.printStackTrace();}
        
        out.println(System.getProperty("java.class.path"));
        
    }
       public static void main(String[] args) {
        
           ReflectTest.doPrint();    

    }
}
