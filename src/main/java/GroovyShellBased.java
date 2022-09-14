import groovy.json.JsonSlurper;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class GroovyShellBased {


  public static void main(String[] args) {
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

    String code = "result=GQ {%s}.toString();return result";

    Binding binding = new Binding();
    binding.setVariable("json", json);
    GroovyShell groovyShell = new GroovyShell(binding);
    Object res = groovyShell.evaluate(String.format(code, sql));

    System.out.println(res);
  }
}
