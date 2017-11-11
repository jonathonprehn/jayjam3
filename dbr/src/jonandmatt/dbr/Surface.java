/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jonandmatt.dbr;

/**
 *
 * @author Jonathon
 */
public enum Surface {
    GRASS, VOID, TRACK, BLOOD;
    
    private static double accelerationDueToGravity = 9.81; // m/s^2
    private static double getMuValue(Surface surface) {
        switch(surface) {
            case GRASS:
                    return 0.5;
            case VOID:
                return 100;
            case TRACK:
                return 0.1;
            case BLOOD:
                return 0.0;
            default:
                break;
        }
        return 100;
    }
    
    public static double getFrictionForce(Surface surface, double mass) {
        return getMuValue(surface) * mass * accelerationDueToGravity;
    }
}
