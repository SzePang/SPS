package com.szepang.Models;

/**
 * Created by Sze on 30/08/2017.
 */
public class Constraint {

    public int wallRank;
    public int windowRank;
    public int toiletRank;
    public int kitchenRank;
    public int walkwayRank;
    public int barRank;
    public int entranceRank;

    public int getWallRank() {
        return wallRank;
    }

    public int getWindowRank() {
        return windowRank;
    }

    public int getToiletRank() {
        return toiletRank;
    }

    public int getKitchenRank() {
        return kitchenRank;
    }

    public int getWalkwayRank() {
        return walkwayRank;
    }

    public int getBarRank() {
        return barRank;
    }

    public int getEntranceRank() {
        return entranceRank;
    }

    public int[] getCompareableArray(){
        return null;
    }

    /**
     * @param entity Takes a parameter of TableEntity
     * @return getComparableArray return the array of fields we want to compare from the table entry.
     */
    public int[] getCompareableArray(TableEntity entity){
        return null;
    }
}
