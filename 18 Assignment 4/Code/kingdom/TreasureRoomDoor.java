package kingdom;

import java.util.List;

public interface TreasureRoomDoor {

    void acquireReadAccess(String actorName);

    void acquireWriteAccess(String actorName);

    void releaseReadAccess(String actorName);

    void releaseWriteAccess(String actorName);

    Gem retrieveValuable();

    void addValuable(Gem v);

    List<Gem> lookAtAllGems();

}
