package com.szepang.Controllers;

import Models.Table;

import java.util.ArrayList;

/**
 * Created by Sze on 14/08/2017.
 */
public class TableListDBnoImplemented {

    //Creates an ArrayList of tables to test code until DB is implemented.
 ArrayList<Table> tableList = new ArrayList<>();

    
//@param Table, adds the table to the list
public void addTableToList(Table theTable) {
    tableList.add(theTable);
}

//Prints all tables in list tableList
public void printAllTables () {
    for (Table t : tableList) {
        System.out.println(t);
    }
}

//@param int tNum, books the table number specified
    public void bookT(int tNum) {
        boolean notFound = true;
        while (notFound) {
            for (Table t : tableList) {
                int tt = t.getTableNumber();
                if (tt == tNum) {
                    notFound = false;
                    t.bookTable();
                    //TEST Check that the appropriate fields have changed
                    t.printTableProperty();
                }
            }
        }
    }

//Return the table that matches the Seat Quantity
    public int tbMatchSeat (int numOfPeople) {
    int tNum = 0;
    boolean notFound = true;
    while (notFound) {
            for (Table t : tableList) {
                int seats = t.getSeatQty();
                        if(seats == numOfPeople){
                            tNum = t.getTableNumber();
                            notFound = false;
                        }
            }
        }
        return tNum;
    }





//TODO
//Check if a certain boolean value exists in a field
/*    public boolean yesOrNo (boolean theProperty) {
    boolean yesOrNo = theProperty;
    return yesOrNo;
    }*/


}
