package pt.isel.ls.academicActivities.utils.http;

import pt.isel.ls.academicActivities.utils.Writable;

public interface HttpContent extends Writable {
    String getMediaType();    
}
