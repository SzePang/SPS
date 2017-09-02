package com.szepang.PeoplePriorities;

import com.szepang.Models.TableEntity;

/**
 * Created by Sze on 30/08/2017.
 */
public final class DisabilityPriorities extends Priorities {

    //Disability favours tables near toilets and walkway for easy accessibility
    //and near the entrance/exit for easier emergency evacuation.
    //tables near walls or kitchen are unfavourable.
    //The properties assigned with int ranks, 1 being lowest priority and 10 being the highest priority.

    //Disability priorities for all fields, 0 means it does not matter.
    public DisabilityPriorities() {
        this.wallRank = 0;
        this.windowRank = 1;
        this.toiletRank = 9;
        this.kitchenRank = 1;
        this.walkwayRank = 10;
        this.barRank = 5;
        this.entranceRank = 10;
    }

    //Give only fields we care about with the disability priorities
    @Override
    public int[] getComparableArray(){
        int[] arr = new int[6];
        arr[0] = getBarRank();
        arr[1] = getEntranceRank();
        arr[2] = getKitchenRank();
        arr[3] = getToiletRank();
        arr[4] = getWalkwayRank();
        arr[5] = getWindowRank();

        return arr;
    }

    /**
     * @param entity Takes a parameter of TableEntity
     * @return getComparableArray return the array of fields we want to compare from the table entry.
     */
    @Override
    public int[] getComparableArray(TableEntity entity){
        int[] temp = new int[6];
        temp[0] = entity.getBar();
        temp[1] = entity.getEntrance();
        temp[2] = entity.getKitchen();
        temp[3] = entity.getToilet();
        temp[4] = entity.getWalkway();
        temp[5] = entity.getWindow();

        return temp;
    }


//End of Class curly bracket
}
