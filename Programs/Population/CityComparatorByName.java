import java.util.Comparator;

public class CityComparatorByName implements Comparator<City> {

    @Override
    public int compare(City city1, City city2) {
        if(city1.getName().equals(city2.getName()))
        {
            return city1.getPopulation() - city2.getPopulation();
        }
        return city1.getName().compareTo(city2.getName());
    }

    
    
}
