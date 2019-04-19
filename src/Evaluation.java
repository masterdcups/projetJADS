import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Rouge {

    public static float rouge1(String genere, String reference)
            throws FileNotFoundException {
        File fichierReference = new File(reference);
        Scanner lectReference = new Scanner(fichierReference);
        ListString list = new ArrayListString();
        while (lectReference.hasNext()) {
            String mot = lectReference.next();
            list.add(mot);
        }
        lectReference.close();
        File fichierGenere = new File(genere);
        Scanner lectGenere = new Scanner(fichierGenere);
        int numer = 0;
        ListString listMots = new ArrayListString();
        while (lectGenere.hasNext()) {
            String mot = lectGenere.next();
            if (! listMots.contains(mot.toUpperCase())) {
                int nbOcc = 0;
                for (String m  list)
                    if (mot.toUpperCase().equals(m.toUpperCase()))
                        nbOcc++;
                numer += nbOcc;
                listMots.add(mot.toUpperCase());
            }
        }
        lectGenere.close();
        return (float) numer  list.size();
    }

    public static void main (String[] args) throws FileNotFoundException {
        System.out.println(rouge1(source.txt, reference.txt));
    }
}
