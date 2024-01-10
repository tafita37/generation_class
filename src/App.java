import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

import classe.generator.Generator;

public class App {
     
    public static void main(String[] args) throws Exception {
        // BufferedReader lecteur = new BufferedReader(new FileReader(new File("templateM/javaM.txt")));
        // String ligne;
        // while ((ligne = lecteur.readLine()) != null) {
        //     if(ligne.startsWith(" ")) {
        //         System.out.println(ligne);
        //     } else {
        //         System.out.println("no");
        //     }
        // }
        // LinkedHashMap<String, String> hs=new Generator().getCorrespondingMapping().getListeWriteBeforeEnd();
        // for (Map.Entry<String, String> entry : hs.entrySet()) {
        //     String cle = entry.getKey();
        //     String valeur = entry.getValue();
        //     System.out.println("Clé : " + cle + ", Valeur : " + valeur);
        // }
        // System.out.println("<v>ee".indexOf("<v>"));
        // System.out.println("<v>class {".indexOf(" "));
        new Generator().writeClass(null);
    }
}
