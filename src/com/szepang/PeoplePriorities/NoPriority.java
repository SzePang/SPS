package com.szepang.PeoplePriorities;

import com.szepang.Models.TableEntity;

/**
 * Created by Sze on 03/09/2017.
 */

//This Class is for customer groups that do not have any specific priorities (Children, Disabilities, Elders, Pregnant)
public class NoPriority extends Priorities{

    //The properties assigned with int ranks, 1 being lowest priority and 10 being the highest priority.
    //Fields, 0 means it does not matter.
    //NoPriority fields will not favour the fields that matter therefore subsequent ranked as 1
    //to priority classes such as Children, Disabilities, Elders, Pregnant
    public NoPriority() {
        this.wallRank = 1;
        this.windowRank = 0;
        this.toiletRank = 1;
        this.kitchenRank = 0;
        this.walkwayRank = 1;
        this.barRank = 0;
        this.entranceRank = 1;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Give only fields we care about with elderly priorities
    @Override
    public int[] getComparableArray(){
        int[] arr = new int[4];
        arr[0] = getEntranceRank();
        arr[1] = getToiletRank();
        arr[2] = getWalkwayRank();
        arr[3] = getWallRank();

        return arr;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param entity Takes a parameter of TableEntity
     * @return getComparableArray return the array of fields we want to compare from the table entry.
     */
    @Override
    public int[] getComparableArray(TableEntity entity){
        int[] temp = new int[4];
        temp[0] = entity.getEntrance();
        temp[1] = entity.getToilet();
        temp[2] = entity.getWalkway();
        temp[3] = entity.getWall();

        return temp;
    }



//This curly bracket is this class scope
}
