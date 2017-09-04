package com.szepang.PeoplePriorities;

import com.szepang.Models.TableEntity;

/**
 * Created by Sze on 01/09/2017.
 */
public class ElderlyPriorities extends Priorities{
    //Elderly priorities favour tables near walls
    //Tables near bar and walkways are unfavourable due to safety hazards
    //The properties assigned with int ranks, 1 being lowest priority and 10 being the highest priority.

    //Elderly priorities for all fields, 0 means it does not matter.
    public ElderlyPriorities() {
        this.wallRank = 10;
        this.windowRank = 0;
        this.toiletRank = 5;
        this.kitchenRank = 0;
        this.walkwayRank = 1;
        this.barRank = 1;
        this.entranceRank = 5;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Give only fields we care about with elderly priorities
    @Override
    public int[] getComparableArray(){
        int[] arr = new int[5];
        arr[0] = getBarRank();
        arr[1] = getEntranceRank();
        arr[2] = getToiletRank();
        arr[3] = getWalkwayRank();
        arr[4] = getWallRank();

        return arr;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param entity Takes a parameter of TableEntity
     * @return getComparableArray return the array of fields we want to compare from the table entry.
     */
    @Override
    public int[] getComparableArray(TableEntity entity){
        int[] temp = new int[5];
        temp[0] = entity.getBar();
        temp[1] = entity.getEntrance();
        temp[2] = entity.getToilet();
        temp[3] = entity.getWalkway();
        temp[4] = entity.getWall();

        return temp;
    }



//This curly bracket is this class scope
}
