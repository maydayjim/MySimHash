package main;

import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        SimCheckService simCheckService = new SimCheckService();
        Content content1 = new Content(123,simCheckService.readDataFile("src/main/trainText1"));
        Content content2 = new Content(124,simCheckService.readDataFile("src/main/trainText2"));
        Content content3 = new Content(125,simCheckService.readDataFile("src/main/trainText3"));
        Content content4 = new Content(125,simCheckService.readDataFile("src/main/trainText7"));
        Content content5 = new Content(125,simCheckService.readDataFile("src/main/trainText9"));
        simCheckService.simCheck(content1);
        System.out.println("************");
       // System.out.println(simCheckService.AllSimHashValue.get(1).segmentHash);
       // System.out.println(simCheckService.AllSimHashValue.get(2).segmentHash);
        simCheckService.simCheck(content2);
        System.out.println("************");
        simCheckService.simCheck(content3);
        System.out.println("************");
        simCheckService.simCheck(content4);
        System.out.println("************");
        simCheckService.simCheck(content5);


        }
    }

