import java.util.Scanner;

public class HeatEnergyProject {
  public static void main(String[] args) {

    Scanner reader = new Scanner(System.in);
    System.out.println("Enter a mass, in grams: ");
    double mass = reader.nextInt();

    System.out.println("Enter the Starting Temperature, in Celcius: ");
    double start_temp = reader.nextInt();
    if (start_temp < -273.14)
      start_temp = -273.14;
    System.out.println("Enter the Ending Temperature, in Celcius: ");
    double end_temp = reader.nextInt();
    if (end_temp < -273.14)
      end_temp = -273.14;

    String initialPhase = "liquid";
    if (start_temp > 100.0)
      initialPhase = "gas";
    if (start_temp < 0.0)
      initialPhase = "solid";

    String finalPhase = "liquid";
    if (end_temp > 100.0)
      finalPhase = "gas";
    if (end_temp < 0.0)
      finalPhase = "solid";

    System.out.println("Mass: " + mass + "g");
    System.out.println("Starting Temperature: " + start_temp + "C " + initialPhase);
    System.out.println("Ending Temperature: " + end_temp + "C " + finalPhase + "\n");
    boolean endothermic = false;
    if (end_temp > start_temp)
      endothermic = true;
    double heat_energy = 0;
    if (initialPhase.equals("solid")) {
      heat_energy += tempChangeSolid(mass, start_temp, end_temp, finalPhase, endothermic);
      if (!finalPhase.equals("solid")) {
        heat_energy += fusion(mass, endothermic);
        heat_energy += tempChangeLiquid(mass, 0, end_temp, finalPhase, endothermic);
      }
      if (finalPhase.equals("gas")) {
        heat_energy += vaporization(mass, endothermic);
        heat_energy += tempChangeGas(mass, 100, end_temp, finalPhase, endothermic);
      }

    }
    if (initialPhase.equals("liquid")) {
      heat_energy += tempChangeLiquid(mass, start_temp, end_temp, finalPhase, endothermic);
      if (finalPhase.equals("solid")) {
        heat_energy += fusion(mass, endothermic);
        heat_energy += tempChangeSolid(mass, 0, end_temp, finalPhase, endothermic);
      }
      if (finalPhase.equals("gas")) {
        heat_energy += vaporization(mass, endothermic);
        heat_energy += tempChangeGas(mass, 100, end_temp, finalPhase, endothermic);
      }

    }
    if (initialPhase.equals("gas")) {
      heat_energy += tempChangeGas(mass, start_temp, end_temp, finalPhase, endothermic);
      if (!finalPhase.equals("gas")) {
        heat_energy += vaporization(mass, endothermic);
        heat_energy += tempChangeLiquid(mass, 100, end_temp, finalPhase, endothermic);
      }
      if (finalPhase.equals("solid")) {
        heat_energy += fusion(mass, endothermic);
        heat_energy += tempChangeSolid(mass, 0, end_temp, finalPhase, endothermic);
      }

    }

    System.out.println("Total Heat Energy: " + round(heat_energy) + "kJ");

  }

  public static double tempChangeSolid(double mass, double init_temp, double final_temp, String endPhase,
      boolean endothermic) {
    double ICE_C = 0.002108;

    if (!endPhase.equals("solid"))
      final_temp = 0;
    double energyChange = round(mass * ICE_C * (final_temp - init_temp));
    if (endothermic)
      System.out.println("Heating (solid): " + energyChange + "kJ");
    else
      System.out.println("Cooling (solid): " + energyChange + "kJ");
    return energyChange;
  }

  public static double tempChangeLiquid(double mass, double init_temp, double final_temp, String endPhase,
      boolean endothermic) {

    double WATER_C = 0.004184;

    if (endPhase.equals("solid"))
      final_temp = 0;
    if (endPhase.equals("gas"))
      final_temp = 100;
    double energyChange = round(mass * WATER_C * (final_temp - init_temp));
    if (endothermic)
      System.out.println("Heating (liquid): " + energyChange + " kJ");
    else
      System.out.println("Cooling (liquid): " + energyChange + " kJ");
    return energyChange;

  }

  public static double tempChangeGas(double mass, double init_temp, double final_temp, String endPhase,
      boolean endothermic) {
    double VAPOR_C = 0.001996;
    if (!endPhase.equals("gas"))
      final_temp = 100;
    double energyChange = round(mass * VAPOR_C * (final_temp - init_temp));
    if (endothermic)
      System.out.println("Heating (vapor): " + energyChange + " kJ");
    else
      System.out.println("Cooling (vapor): " + energyChange + " kJ");
    return energyChange;
  }

  public static double vaporization(double mass, boolean endothermic) {
    double energyChange;
    if (endothermic) {
      energyChange = round(2.257 * mass);
      System.out.println("Evaporation: " + energyChange + " kJ");
    } else {
      energyChange = round(-2.257 * mass);
      System.out.println("Condensation: " + energyChange + " kJ");
    }
    return energyChange;
  }

  public static double fusion(double mass, boolean endothermic) {
    double energyChange;
    if (endothermic) {
      energyChange = round(0.333 * mass);
      System.out.println("Melting: " + energyChange + " kJ");
    } else {
      energyChange = round(-0.333 * mass);
      System.out.println("Freezing: " + energyChange + " kJ");
    }
    return energyChange;
  }

  public static double round(double x) {
    x *= 1000.0;
    if (x > 0)
      return (int) (x + 0.5) / 1000.0;
    else
      return (int) (x - 0.5) / 1000.0;

  }
}