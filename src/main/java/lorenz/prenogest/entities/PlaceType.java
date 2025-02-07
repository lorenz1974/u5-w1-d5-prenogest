package lorenz.prenogest.entities;

public enum PlaceType {
    PRIVAT, OPENSPACE, MEETING_ROOM;

    public String getDescription() {
        switch (this) {
            case PRIVAT:
                return "Private Place of Work";
            case OPENSPACE:
                return "Open Space Place of Work";
            case MEETING_ROOM:
                return "Meeting Room Place of Work";
            default:
                return "Unknown Place Type";
        }
    }

    public String getShortDescription() {
        switch (this) {
            case PRIVAT:
                return "Private";
            case OPENSPACE:
                return "Open Space";
            case MEETING_ROOM:
                return "Meeting Room";
            default:
                return "Unknown";
        }
    }
}
