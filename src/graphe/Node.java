package graphe;

import java.util.ArrayList;

public class Node {

    private ArrayList<Node> fils = new ArrayList<>();;
    private Node parent;
    private String mot;
    private double score;
    private int count = 1;
    private int distance;

    public Node(String mot) {
        this.mot = mot;
        this.distance = 0;
    }

    public Node(String mot, Node parent, Node... fils) {
        this.mot = mot;
        this.parent = parent;
        this.distance = parent.distance++;
        for (Node v : fils)
            this.fils.add(v);
    }

    public void incrementerCount(){
        count++;
    }

    public double calculScore(){
        this.score = count - distance*Math.log(count);
        return score;
    }

    public Node meilleurVoisin(){
        double scoreMax = -1;
        Node meilleur = null;
        if(fils.isEmpty())
            return null;
        else {
            for (Node v : fils) {
                if (v.getScore() > scoreMax) {
                    meilleur = v;
                    scoreMax = v.getScore();
                }
            }
        }
        return meilleur;
    }

    public void addFils (Node fils){
        this.fils.add(fils);
    }

    public void nodeNull (){
        count = -1;
    }

    public ArrayList<Node> getfils() {
        return fils;
    }

    public Node getParent() {
        return parent;
    }

    public String getMot() {
        return mot;
    }

    public double getScore() {
        Node m = meilleurVoisin();
        if (m == null)
            return calculScore();
        return calculScore()+meilleurVoisin().getScore();
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Node{" +
                "mot='" + mot + '\'' +
                ", count=" + count +
                '}';
    }
}
