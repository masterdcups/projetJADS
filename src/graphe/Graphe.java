package graphe;

import java.util.ArrayList;

public class Graphe {

    private ArrayList<Node> noeuds;
    private Node racine;

    public Graphe(){
        this.noeuds = new ArrayList<>();
    }

    public Node addRacine(String mot){
        racine = new Node(mot);
        noeuds.add(racine);
        return racine;
    }

    public Node addNode(String mot, Node parent, Node... fils){
        Node n = existence(mot, parent);
        if(n != null){
            n.incrementerCount();
            return n;
        }else {
            Node newNode = new Node(mot, parent, fils);
            parent.addFils(newNode);
            return newNode;
        }
    }

    public Node existence(String mot, Node parent){
        if (parent.getfils() != null)
            for (Node n : parent.getfils())
                if(n.getMot().equals(mot))
                    return n;
        return null;
    }

    public ArrayList<String> resultat(){
        Node n = racine;
        ArrayList<String> resultat = new ArrayList<String>();
        while(n != null){
            resultat.add(n.getMot());
            n.nodeNull();
            n = n.meilleurVoisin();
        }
        return resultat;
    }

    @Override
    public String toString() {
        return "Graphe{" +
                "noeuds=" + noeuds +
                ", racine=" + racine +
                '}';
    }
}
