package com.privateAPI.DUSTestGenerator.reachability_graph.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("reachability_graph")
@Data
public class ReachabilityGraph
{
    @MongoId
    private ObjectId id;
    private Vertex[] vertices;
    private Edge[] edges;


}
