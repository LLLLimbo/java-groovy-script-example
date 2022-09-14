import groovy.json.JsonSlurper;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import java.io.IOException;

public class RunScript {

  public static void main(String[] args)
      throws InstantiationException, IllegalAccessException {
    Object json = new JsonSlurper().parseText("{\n"
        + "        \"weather\": \"sunny\",\n"
        + "        \"temperature\": 28,\n"
        + "        \"humidity\": 70,\n"
        + "        \"date\": \"2022-03-14\"\n"
        + "    },\n"
        + "    {\n"
        + "        \"weather\": \"snow\",\n"
        + "        \"temperature\": -1,\n"
        + "        \"humidity\": 48,\n"
        + "        \"date\": \"2022-03-13\"\n"
        + "    },\n"
        + "    {\n"
        + "        \"weather\": \"rain\",\n"
        + "        \"temperature\": 10,\n"
        + "        \"humidity\": 90,\n"
        + "        \"date\": \"2022-03-16\"\n"
        + "    }");

    String sql = "from w in json where w.temperature > 20 select w";

    // Create GroovyClassLoader.
    final GroovyClassLoader classLoader = new GroovyClassLoader();

    // Create a String with Groovy code.
    String code = "class JSONParser {\n"
        + "    String parse(Object json) {\n"
        + "        def result = GQ {%s}.toString()\n"
        + "        return result\n"
        + "    }\n"
        + "}";

    // Load string as Groovy script class.
    String formatCode=String.format(code, sql);
    Class groovy = classLoader.parseClass(formatCode);
    GroovyObject groovyObj = (GroovyObject) groovy.newInstance();
    String output = (String) groovyObj.invokeMethod("parse", json);

    System.out.println(output);
  }

}
