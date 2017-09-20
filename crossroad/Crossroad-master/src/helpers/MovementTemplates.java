package helpers;


import java.util.HashMap;

public class MovementTemplates{
    private MovementTemplates() {}
    public static final String[] modes = { "po", "function-like" };

    public static HashMap<String, String> getTemplateStrings(String mode){
        HashMap<String, String> templateStrings = new HashMap<>();
        if(mode.equals(modes[0])){
            templateStrings.put("enter", "Car that moves from shady pathway %d towards thread of pathway %d has entered shallow valley of dark crossroad.");
            templateStrings.put("appear", "Car that moves from shady pathway %d towards thread of pathway %d lets it's pace of movement to flow.");
            templateStrings.put("wait", "Car that moves from shady pathway %d towards thread of pathway %d is awaiting to collide with the dreadful world.");
            templateStrings.put("crossroad", "Car that moves from shady pathway %d towards thread of pathway %d goes forward.");
            templateStrings.put("leave", "Car that moves from shady pathway %d towards thread of pathway %d has passed away from the crimson tide of the crossroad.");
        }
        else if(mode.equals(modes[1])){
            templateStrings.put("enter", "e(%d => %d)");
            templateStrings.put("appear", "a(%d => %d)");
            templateStrings.put("wait", "w(%d => %d)");
            templateStrings.put("crossroad", "c(%d => %d)");
            templateStrings.put("leave", "l(%d = > %d)");
        }
        return templateStrings;
    }

    public static String getEndStringSequence(String mode){
        if(mode.equals("po")){
            return "This movement of diabolic cars has ended. For now...";
        }
        else if(mode.equals("function-like")){
            return "exit()";
        }
        return "";
    }
}
