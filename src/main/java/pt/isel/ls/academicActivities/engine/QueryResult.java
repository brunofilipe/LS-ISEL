package pt.isel.ls.academicActivities.engine;

import pt.isel.ls.academicActivities.model.IEntity;

import java.util.List;

public class QueryResult {
    private List<IEntity> entities;
    private IEntity entity;

    public QueryResult(List<IEntity> entities) {
        this.entities = entities;
    }

    public QueryResult(IEntity entity) {
        this.entity = entity;
    }

    public IEntity getEntity() {
        return entity;
    }

    public void setEntity(IEntity entity) {
        this.entity = entity;
    }

    public List<IEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<IEntity> entities) {
        this.entities = entities;
    }
}
