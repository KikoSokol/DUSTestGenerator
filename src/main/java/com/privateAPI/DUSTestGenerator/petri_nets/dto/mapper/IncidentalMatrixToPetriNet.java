package com.privateAPI.DUSTestGenerator.petri_nets.dto.mapper;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import org.springframework.stereotype.Component;

import java.util.Random;

import static java.lang.Math.abs;


@Component
public class IncidentalMatrixToPetriNet {
    private double[][] c;
    private PetriNetDto petriNet;

    public PetriNetDto calculatePetriNet(double[][] c) {
        this.c = c;
        this.petriNet = new PetriNetDto();

        this.startCalculation();

        return this.petriNet;
    }

    /**
     * calculation of places, transitions and edges
     */
    private void startCalculation() {
        this.calculatePlaces();
        this.calculateTransitions();
        this.calculateEdges();
    }


    /**
     * calculation of PLACES
     */
    private void calculatePlaces() {
        Random random = new Random();
        for (int placeId = 0; placeId < this.c.length; placeId++) {
            this.petriNet.addPlace(random.nextInt(4), placeId, false);
        }
    }


    /**
     * calculation of TRANSITIONS
     */
    private void calculateTransitions() {
        for (int transitionId = 0; transitionId < this.c[0].length; transitionId++) {
            this.petriNet.addTransitionWithNameSameAsId(transitionId + "");
        }
    }


    /**
     * calculation of EDGES
     */
    private void calculateEdges() {
        for (int place = 0; place < this.c.length; place++) {
            for (int transition = 0; transition < this.c[0].length; transition++) {
                double value = c[place][transition];
                this.chooseEdgeDirection((int) value, place, transition);
            }
        }
    }

    private void chooseEdgeDirection(int value, int place, int transition) {
        Random random = new Random();
//        int direction = random.nextInt(2);
//        if (direction == 0 && abs(value) != 0){ //|| abs(value) > 5) {
            if(value > 0){ //??????naopak???
            this.petriNet.addEdge("t" + transition, "p" + place, value);

            } else {
                if(abs(value) != 0)
                    this.petriNet.addEdge("p" + place, "t" + transition, abs(value));
            }

//        } else {
//            int fromPlace, toPlace;
//            if (value > 0){ //??????naopak???
//                fromPlace = random.nextInt(3) * (-1) ;
//                toPlace = value - fromPlace;
//            }
//            else {
//                toPlace = random.nextInt(3);
//                fromPlace = value - toPlace;
//            }
//            if(abs(toPlace) != 0)
//                this.petriNet.addEdge("t" + transition, "p" + place, toPlace);
//            if(abs(fromPlace) != 0)
//                this.petriNet.addEdge("p" + place, "t" + transition, abs(fromPlace));
//
//
//        }
    }


}
