package garages;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class Voiture {

    @Getter
    @NonNull
    private final String immatriculation;

    @ToString.Exclude 
    private final List<Stationnement> myStationnements = new LinkedList<>();

    public void entreAuGarage(Garage g) throws IllegalStateException {
        if (estDansUnGarage()) {
            throw new IllegalStateException("la voiture est deja dans un garage");
        }
        Stationnement s = new Stationnement(this, g);
        myStationnements.add(s);
    }

    public void sortDuGarage() throws IllegalStateException {
        if (!estDansUnGarage()) {
            throw new IllegalStateException("la voiture n'est pas dans un garage");
        }
        Stationnement dernier = myStationnements.get(myStationnements.size() - 1);
        dernier.terminer();
    }

    public Set<Garage> garagesVisites() {
        Set<Garage> garages = new HashSet<>();
        for (Stationnement s : myStationnements) {
            garages.add(s.getGarageVisite());
        }
        return garages;
    }

    public boolean estDansUnGarage() {
        if (myStationnements.isEmpty()) return false;
        return myStationnements.get(myStationnements.size() - 1).estEnCours();
    }

    public void imprimeStationnements(PrintStream out) {
        Map<Garage, List<Stationnement>> map = new LinkedHashMap<>();
        for (Stationnement s : myStationnements) {
            map.computeIfAbsent(s.getGarageVisite(), k -> new ArrayList<>()).add(s);
        }
        for (Map.Entry<Garage, List<Stationnement>> entry : map.entrySet()) {
            out.println(entry.getKey() + ":");
            for (Stationnement s : entry.getValue()) {
                out.println("\t" + s);
            }
        }
    }
}