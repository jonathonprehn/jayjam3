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
    
    private static float accelerationDueToGravity = 9.81F; // m/s^2
    private static float getMuValue(Surface surface) {
        switch(surface) {
            case GRASS:
                    return 0.5F;
            case VOID:
                return 100F;
            case TRACK:
                return 0.1F;
            case BLOOD:
                return 0.0F;
            default:
                break;
        }
        return 100;
    }
    
    public static float getFrictionForce(Surface surface, float mass) {
        return getMuValue(surface) * mass * accelerationDueToGravity;
    }
}
