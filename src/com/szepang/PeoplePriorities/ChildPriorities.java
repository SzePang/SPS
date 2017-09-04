package com.szepang.PeoplePriorities;
import com.szepang.Models.TableEntity;

/**
 * Created by Sze on 01/09/2017.
 */
public class ChildPriorities extends Priorities{

    //Child priority favours tables near walls for enclosing the child
    //Tables near kitchen, windows and walkways are unfavourable due to safety hazards
    //The properties assigned with int ranks, 1 being lowest priority and 10 being the highest priority.

    //Child priorities for all fields, 0 means it does not matter.
    public ChildPriorities() {
        this.wallRank = 10;
        this.windowRank = 1;
        this.toiletRank = 5;
        this.kitchenRank = 1;
        this.walkwayRank = 1;
        this.barRank = 1;
        this.entranceRank = 5;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Give only fields we care about with child priorities
    @Override
    public int[] getComparableArray(){
        int[] arr = new int[7];
        arr[0] = getBarRank();
        arr[1] = getEntranceRank();
        arr[2] = getKitchenRank();
        arr[3] = getToiletRank();
        arr[4] = getWalkwayRank();
        arr[5] = getWindowRank();
        arr[6] = getWallRank();

        return arr;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param entity Takes a parameter of TableEntity
     * @return getComparableArray return the array of fields we want to compare from the table entry.
     */
    @Override
    public int[] getComparableArray(TableEntity entity){
        int[] temp = new int[7];
        temp[0] = entity.getBar();
        temp[1] = entity.getEntrance();
        temp[2] = entity.getKitchen();
        temp[3] = entity.getToilet();
        temp[4] = entity.getWalkway();
        temp[5] = entity.getWindow();
        temp[6] = entity.getWall();

        return temp;
    }





//This curly bracket is this class scope
}
