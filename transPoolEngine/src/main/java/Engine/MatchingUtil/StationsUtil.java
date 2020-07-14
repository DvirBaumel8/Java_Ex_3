package Engine.MatchingUtil;



import Engine.XML.XMLLoading.jaxb.schema.generated.Stop;

import java.util.List;

public class StationsUtil {

    public static int getXCoorOfStation(String sourceStation, List<Stop> stops) {
        for(Stop stop : stops) {
            if(stop.getName().equals(sourceStation)) {
                return stop.getX();
            }
        }
        return -1;
    }

    public static int getYCoorOfStation(String sourceStation, List<Stop> stops) {
        for(Stop stop : stops) {
            if(stop.getName().equals(sourceStation)) {
                return stop.getY();
            }
        }
        return -1;
    }
}
