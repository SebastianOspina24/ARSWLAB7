package edu.eci.arsw.blueprints.model.filtros;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.eci.arsw.blueprints.model.BluePrintsFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;


@Service
@Qualifier(value="Submuestreo")
public class FiltroSubmuestreo implements BluePrintsFilter{

    @Override
    public Blueprint filtrado(Blueprint bp) {
        String autor = bp.getAuthor();
        String name = bp.getName();
        List<Point> puntos = bp.getPoints();
        List<Point> pnts= new ArrayList<>();
        boolean flag = true;
        for(int i = 1;i<puntos.size()-1;i++){
            if(flag)pnts.add(puntos.get(i));
            if(i%2==0)flag = !flag;
        }
        return new Blueprint(autor, name, pnts);
    }
    
}