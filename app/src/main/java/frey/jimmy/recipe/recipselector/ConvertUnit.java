package frey.jimmy.recipe.recipselector;

import java.util.ArrayList;

/**
 * Created by James on 5/26/2015.
 */
public class ConvertUnit {
    private static ArrayList<Unit> mUnits;

    public ConvertUnit(){
        mUnits = new ArrayList<>();
        addAllUnits();
    }

    private void addAllUnits() {
        mUnits.add(new Unit("Ounce",1));
        mUnits.add(new Unit("oz",1));
        mUnits.add(new Unit("cup",1));
        mUnits.add(new Unit("tsp",1));
        mUnits.add(new Unit("tbsp",1));
        mUnits.add(new Unit("Tablespoon",1));
        mUnits.add(new Unit("tsp",1));
        mUnits.add(new Unit("Pound",16));
        mUnits.add(new Unit("Pounds",16));
        mUnits.add(new Unit("Kilogram",35.274));
        mUnits.add(new Unit("package",35.274));
    }

    public ArrayList<String> getUnitsStringList() {
        ArrayList<String> unitsString = new ArrayList<>();
        for(Unit u: mUnits){
            unitsString.add(u.toString());
        }
        return unitsString;
    }

    public static double convertToOunce(double quantity, Unit unit){
        return quantity*unit.getOunceConversion();
    }

    public static double convertFromOunce(double quantity, Unit unit){
        return quantity/unit.getOunceConversion();
    }

    public static double convertToUnit(double quantity, Unit fromUnit, Unit toUnit){
        return convertFromOunce(convertToOunce(quantity, fromUnit),toUnit);
    }

    public static double convertToUnit(double quantity, String fromUnitString, String toUnitString){
        Unit fromUnit = null;
        Unit toUnit = null;
        for(Unit u: mUnits){
            if(u.getName().toLowerCase().equals(fromUnitString.toLowerCase())){
                fromUnit = u;
            }
            if(u.getName().toLowerCase().equals(toUnitString.toLowerCase())){
                toUnit = u;
            }
        }
        return convertFromOunce(convertToOunce(quantity, fromUnit),toUnit);
    }

    public class Unit {
        private String mName;
        private double mOunceConversion;

        public Unit(String name, double ounceConversion) {
            mName = name;
            mOunceConversion = ounceConversion;
        }

        public String getName() {
            return mName;
        }

        public double getOunceConversion() {
            return mOunceConversion;
        }

        @Override
        public String toString() {
            return mName;
        }
    }
}
