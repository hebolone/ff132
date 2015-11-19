package ff132;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;
import com.martiansoftware.jsap.UnflaggedOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Simone
 */
public class Ff132 {
    public static void main(String[] args) {
        JSAP jsap = new JSAP();
        
        Switch optHelp = new Switch("help")
                .setShortFlag('h')
                .setLongFlag("help");
        
        UnflaggedOption optSerie = new UnflaggedOption("serie")
                .setStringParser(JSAP.STRING_PARSER)
                .setDefault("")
                .setRequired(true)
                .setGreedy(true);
        
        try {
            jsap.registerParameter(optHelp);
            jsap.registerParameter(optSerie);
        } catch (JSAPException ex) {
            Logger.getLogger(Ff132.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JSAPResult config = jsap.parse(args);
        if(config.getBoolean("help")) {
            System.out.println("Write clock in format: n1,n2,n3,n4...");
            System.out.println("Code by Sm3P (mail: sm3p@hotmail.com)");
        } else {
            String requestedSerie = config.getStringArray("serie")[0];
            System.out.println("Try solve puzzle: " + requestedSerie);
            String [] serie = requestedSerie.split(",");
            CClock c = new CClock();
            for(String s : serie) {
                c.AddUnit(Integer.parseInt(s.trim()));
            }
            c.SearchSolution();
        }

        /*
        if(args.length != 1) {
            System.out.println("Need input parameter in form: 1,2,3,..,n");
            return;
        }
        String [] v = args[0].split(",");
        CClock c = new CClock();
        for(String s : v) {
            c.AddUnit(Integer.parseInt(s.trim()));
        }
        c.SearchSolution();*/
    }
}
