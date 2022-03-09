package edu.eci.arsw.blueprints.model.filtros;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.eci.arsw.blueprints.model.BluePrintsFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;


@Service
@Qualifier(value="Redundancia")
public class FiltroRedundancia implements BluePrintsFilter{

    @Override
    public Blueprint filtrado(Blueprint bp) {
        String autor = bp.getAuthor();
        String name = bp.getName();
        List<Point> puntos = bp.getPoints();
        List<Point> pnts= new ArrayList<>();
        for(int i = 0;i<puntos.size()-1;i++){
            if(puntos.get(i).getX()!=puntos.get(i+1).getX() || puntos.get(i).getY()!=puntos.get(i+1).getY())pnts.add(puntos.get(i));
        }
        pnts.add(puntos.get(puntos.size()-1));
        return new Blueprint(autor, name, pnts);
    }
    
}
