package com.szepang.PeoplePriorities;
import com.szepang.Models.TableEntity;

/**
 * Created by Sze on 31/08/2017.
 */
public class PregnantPriorities extends Priorities{

    //Pregnancy priority favours tables near toilets and walkway for easy accessibility
    //and near the entrance/exit for easier emergency evacuation.
    //The properties assigned with int ranks, 1 being lowest priority and 10 being the highest priority.

    //Pregnancy priorities for all fields, 0 means it does not matter.
    public PregnantPriorities() {
        this.wallRank = 0;
        this.windowRank = 0;
        this.toiletRank = 10;
        this.kitchenRank = 5;
        this.walkwayRank = 10;
        this.barRank = 1;
        this.entranceRank = 5;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Give only fields we care about with pregnancy priorities
    @Override
    public int[] getComparableArray(){
        int[] arr = new int[5];
        arr[0] = getBarRank();
        arr[1] = getEntranceRank();
        arr[2] = getKitchenRank();
        arr[3] = getToiletRank();
        arr[4] = getWalkwayRank();

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
        temp[2] = entity.getKitchen();
        temp[3] = entity.getToilet();
        temp[4] = entity.getWalkway();

        return temp;
    }

//This curly bracket is this class scope
}
