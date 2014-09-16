package com.springapp.mvc.dto;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objType")
@JsonSubTypes({
        @JsonSubTypes.Type(value=Group.class, name = "group"),
        @JsonSubTypes.Type(value=Rule.class, name = "rule")
})
public abstract class Item {
    public abstract String getObjType();
}
