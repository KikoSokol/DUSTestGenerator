package com.privateAPI.DUSTestGenerator.reachability_graph.service.impl;

import com.privateAPI.DUSTestGenerator.reachability_graph.controller.request.ReachabilityGraphGeneratorRequest;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraphGeneratorResult;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReachabilityGraphServiceTestTest
{
    @Autowired
    private ReachabilityGraphServiceTest service;

    @Test
    public void testDefaultGenerator()
    {
        int count = 0;

        for (int i = 0; i < 1000; i++)
        {
//            System.out.println(i + 1);
            ReachabilityGraphGeneratorResult result = this.service.getRandomReachabilityGraphForTest();
            count = count + result.getCountOfDeletedReachabilityGraphs();
        }

        System.out.println("--------------------------------------");
        System.out.println("pocet vrcholov: 10-15");
        System.out.println("pocet miest: 4-5");
        System.out.println("pocet prechodov: 3-5");
        System.out.println("--------------------------------------");
        System.out.println("Priemerný počet zahodených grafov pri vygenerovaní 1000 grafov:");
        System.out.println(count/1000);

    }

    @Test
    public void testGenerator1()
    {
        int minVertices = 10;
        int maxVertices = 15;
        int minPlaces = 5;
        int maxPlaces = 10;
        int minCountEdges = 5;
        int maxCountEdges = 8;


        ReachabilityGraphGeneratorRequest request = new ReachabilityGraphGeneratorRequest(minVertices,maxVertices,minPlaces,maxPlaces,minCountEdges,maxCountEdges);
        int count = 0;

        for (int i = 0; i < 1000; i++)
        {
            System.out.println(i + 1);
            ReachabilityGraphGeneratorResult result = this.service.getRandomReachabilityGraph(request);
            count = count + result.getCountOfDeletedReachabilityGraphs();
        }

        System.out.println("--------------------------------------");
        System.out.println("pocet vrcholov: " + minVertices + "-" + maxVertices);
        System.out.println("pocet miest: " + minPlaces + "-" + maxPlaces);
        System.out.println("pocet prechodov: " + minCountEdges + "-" + maxCountEdges);
        System.out.println("--------------------------------------");
        System.out.println("Priemerný počet zahodených grafov pri vygenerovaní 1000 grafov:");
        int result = count/1000;
        System.out.println(result);

    }

    @Test
    public void testGenerator2()
    {
        int minVertices = 10;
        int maxVertices = 15;
        int minPlaces = 8;
        int maxPlaces = 8;
        int minCountEdges = 5;
        int maxCountEdges = 5;


        ReachabilityGraphGeneratorRequest request = new ReachabilityGraphGeneratorRequest(minVertices,maxVertices,minPlaces,maxPlaces,minCountEdges,maxCountEdges);
        int count = 0;
        int[] deleted = new int[1000];

        for (int i = 0; i < 1000; i++)
        {
            System.out.println(i + 1);
            ReachabilityGraphGeneratorResult result = this.service.getRandomReachabilityGraph(request);
            count = count + result.getCountOfDeletedReachabilityGraphs();
            deleted[i] = result.getCountOfDeletedReachabilityGraphs();
        }

        System.out.println("--------------------------------------");
        System.out.println("pocet vrcholov: " + minVertices + "-" + maxVertices);
        System.out.println("pocet miest: " + minPlaces + "-" + maxPlaces);
        System.out.println("pocet prechodov: " + minCountEdges + "-" + maxCountEdges);
        System.out.println("--------------------------------------");
        System.out.println("Priemerný počet zahodených grafov pri vygenerovaní 1000 grafov:");
        int result = count/1000;
        System.out.println(result);
        System.out.println("--------------------------------------");
        for (int i = 0; i < 1000; i++) {
            System.out.print(deleted[i] + ", ");
        }
        System.out.println();

    }

}