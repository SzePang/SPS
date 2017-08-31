package com.szepang.Models;

/**
 * Created by Sze on 30/08/2017.
 */
public final class DisabilityConstraint extends Constraint{

    //Disability constraint favours tables near toilets and walkway for easy accessibility
    //and near the entrance/exit for easier emergency evacuation.
    //tables near walls or kitchen are unfavourable.
    //The properties  assigned with int ranks, 1 being the highest preference.


    public DisabilityConstraint() {
        this.wallRank = 0;
        this.windowRank = 10;
        this.toiletRank = 1;
        this.kitchenRank = 7;
        this.walkwayRank = 1;
        this.barRank = 7;
        this.entranceRank = 1;
    }

    //Give only fields we care about with the disability constraint
    @Override
    public int[] getCompareableArray(){
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
    public int[] getCompareableArray(TableEntity entity){
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
