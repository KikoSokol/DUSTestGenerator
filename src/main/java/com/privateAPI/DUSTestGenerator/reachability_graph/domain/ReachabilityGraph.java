package com.privateAPI.DUSTestGenerator.reachability_graph.domain;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Edge;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document("reachability_graph")
@Data
public class ReachabilityGraph
{
    @MongoId
    private ObjectId id;
    private List<Vertex> vertices;
    private List<Edge> edges;


    public ReachabilityGraph(List<Vertex> vertices, List<Edge> edges) {
        setEdges(edges);
        setVertices(vertices);
    }
}
