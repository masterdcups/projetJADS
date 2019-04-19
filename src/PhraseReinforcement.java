import graphe.Graphe;
import graphe.Node;

import java.io.*;
import java.util.*;

public class PhraseReinforcement {

    File corpus;
    private Graphe grapheGauche = new Graphe();
    private Graphe grapheDroite = new Graphe();
    private Node racineG;
    private Node racineD;

    public PhraseReinforcement(File corpus) {
        this.corpus = corpus;
    }

    public void lectureCorpus(String motsDeDepart){
        racineG = grapheGauche.addRacine(motsDeDepart);
        racineD = grapheDroite.addRacine(motsDeDepart);
        try {
            BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(corpus)));
            String l;
            while(((l = buff.readLine()) != null))
                constructionDuGraphe(l.split(" "));
            buff.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void constructionDuGraphe (String[] ligne){
        boolean droite = false;
        ArrayList<String> partieGauche = new ArrayList<String> ();
        ArrayList<String> partieDroite = new ArrayList<String> ();

        for (String mot : ligne){
            droite = droite || (mot.equals(racineG.getMot()));
            if (!droite)
                partieGauche.add(mot);
            else
                partieDroite.add(mot);
        }

        partieDroite.remove(racineG.getMot());
        Node current = racineG;
        Collections.reverse(partieGauche);

        for(ListIterator<String> i = partieGauche.listIterator(); i.hasNext();){
            String mot = i.next();
            current = grapheGauche.addNode(mot, current);
        }

        current = racineD;
        for(ListIterator<String> i = partieDroite.listIterator(); i.hasNext();){
            String mot = i.next();
            current = grapheDroite.addNode(mot, current);
        }
    }

    public String calculResume (){
        ArrayList<String> resultat = new ArrayList<>();
        resultat.addAll(grapheGauche.resultat());
        Collections.reverse(resultat);
        resultat.remove(resultat.size()-1);
        resultat.addAll(grapheDroite.resultat());
        String phraseResume = "";
        for(String mot : resultat)
            phraseResume += mot + " ";
        return phraseResume;
    }

    public String resultatResume (int longRes){
        String resume = "";
        for (int i=0; i < longRes; i++){
            resume += calculResume() + "\n";
        }
        return resume;
    }
}
