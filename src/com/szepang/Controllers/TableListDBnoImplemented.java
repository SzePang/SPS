package com.szepang.Controllers;

import Models.Table;

import java.util.ArrayList;
import java.util.Iterator;

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

//@param tNum, books the table number specified
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

// @param int number of people
// Return the table that matches the Seat Quantity
// Also checks that the table is Free to seat people
    public int tbMatchSeat (int numOfPeople) {
    //TODO Consider if a client makes table called 0
    int tNum = 0;
    int tableCollection = 0;
    boolean notFound = true;
    while (notFound && tableCollection < tableList.size()) {
            for (Table t : tableList) {
                int seats = t.getSeatQty();
                        if(seats == numOfPeople && t.isFree()){
                            //TODO chuck constraint code in here
                            tNum = t.getTableNumber();
                            notFound = false;
                        }
                        tableCollection++;
            }
        }
        return tNum;
    }

// FREE all tables
    public void freeAllTables() {
        for (Table t : tableList) {
            t.setFree(true);
            //TEST Check that the appropriate fields have changed
            t.printTableProperty();
            }
    }




//TODO
//Check if a certain boolean value exists in a field
/*    public boolean yesOrNo (boolean theProperty) {
    boolean yesOrNo = theProperty;
    return yesOrNo;
    }*/


}
