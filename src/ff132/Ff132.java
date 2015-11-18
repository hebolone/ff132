package ff132;

import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;

/**
 *
 * @author Simone
 */
public class Ff132 {
    public static void main(String[] args) throws Exception {
        JSAP jsap = new JSAP();
        
        UnflaggedOption opt1 = new UnflaggedOption("command")
                .setStringParser(JSAP.STRING_PARSER)
                .setDefault("s")
                .setRequired(true)
                .setGreedy(true);
        opt1.setHelp("S | G = get Solution or Generate a clock");
        
        jsap.registerParameter(opt1);
        
        JSAPResult config = jsap.parse(args);
        String command = config.getString("command");
        
        System.out.println("Chosen command: " + command);
        
        if(args.length != 1) {
            System.out.println("Need input parameter in form: 1,2,3,..,n");
            return;
        }
        String [] v = args[0].split(",");
        CClock c = new CClock();
        for(String s : v) {
            c.AddUnit(Integer.parseInt(s.trim()));
        }
        c.SearchSolution();
    }
}
