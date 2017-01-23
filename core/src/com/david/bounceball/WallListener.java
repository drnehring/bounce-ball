package com.david.bounceball;

/**
 * Created by David on 12/22/2016.
 */
public interface WallListener {

    void wallRotated(Wall wall);

    void wallDestroyed(Wall wall);

}
