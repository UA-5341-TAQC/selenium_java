package org.academy.ui.types;

public enum ProgramField {
    NAME("name"),
    DESCRIPTION("description"),
    LOCATION("location"),
    PARTICIPANTS_COUNT("participantsCount"),
    MEETING_COUNT("meetingCount");

    private final String id;

    ProgramField(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
