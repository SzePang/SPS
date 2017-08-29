package com.szepang.Models;

import javax.persistence.*;

/**
 * Created by Sze on 12/07/2017.
 * The persistence class for the Table database table.
 */

@Entity
@Table(name = "tables")
public class TableEntity {

    //FIELDS for Table class
    @Id
    @Column(name="tableNumber")
    private int tableNum;
    private int seatQty;
    private boolean free;
    //Table characteristic fields
    private boolean wall;
    private boolean window;
    /*@Column(name="toilets")*/
    private boolean toilet;
    private boolean kitchen;
    private boolean walkway;
    private boolean bar;
    private boolean entrance;

    //Empty constructor for hibernate
    public TableEntity () {}

    //CONSTRUCTOR for Table objects. This constructor creates a new table.
    //Create a table specified by the parameters.
    public TableEntity(int tableNumber, int seatQty, boolean free, boolean wall, boolean window, boolean toilets,
                 boolean kitchen, boolean walkway, boolean bar, boolean entrance) {
        this.tableNum = tableNumber;
        this.seatQty = seatQty;
        this.free = free;
        this.wall = wall;
        this.window = window;
        this.toilet = toilets;
        this.kitchen = kitchen;
        this.walkway = walkway;
        this.bar = bar;
        this.entrance = entrance;
    }

    //Table Number
    //@return the table number
    public int getTableNumber() {return tableNum;}
    //@param int set the table number
    public void setTableNumber(int tableNumber) {
        this.tableNum = tableNumber;
    }

    //SEAT
    //@return the number of seats for the table
    public int getSeatQty() {
        return seatQty;
    }
    //@param int set the number of seats
    public void setSeatQty(int seatQty) {
        this.seatQty = seatQty;
    }


    //Is the Table Free, return true if free
    //Return boolean free
    public boolean isFree() {return free;}
    //Set boolean free
    public void setFree(boolean free) {this.free = free;}

    //Table near wall, return true if near wall
    //return boolean wall
    public boolean isWall() {return wall;}
    //Set boolean wall
    public void setWall(boolean wall) {this.wall = wall;}

    //Table near window, return true is near window
    //return boolean window
    public boolean isWindow() {return window;}
    //Set boolean window
    public void setWindow(boolean window) {this.window = window;}

    //Table near toilet, return true if near toilet
    //return boolean toilet
    public boolean isToilet() {return toilet;}
    //Set boolean toilet
    public void setToilet(boolean toilets) {this.toilet = toilets;}

    //Table near kitchen, return true if near kitchen
    //return boolean kitchen
    public boolean isKitchen() {return kitchen;}
    //Set boolean kitchen
    public void setKitchen(boolean kitchen) {this.kitchen = kitchen;}

    //Table near walkway, return true if near walkway
    //return boolean walkway
    public boolean isWalkway() {return walkway;}
    //Set boolean walkway
    public void setWalkway(boolean walkway) {this.walkway = walkway;}

    //Table near bar, return true if near bar
    //return boolean bar
    public boolean isBar() {return bar;}
    //Set boolean bar
    public void setBar(boolean bar) {this.bar = bar;}

    //Table near entrance, return true if near entrance
    //return boolean entrance
    public boolean isEntrance() {return entrance;}
    //Set boolean entrance
    public void setEntrance(boolean entrance) {
        this.entrance = entrance;
    }

public void printTableProperty() {
        System.out.println(tableNum);
        System.out.println(seatQty);
        System.out.println(free);
        System.out.println(wall);
        System.out.println(window);
        System.out.println(toilet);
        System.out.println(kitchen);
        System.out.println(walkway);
        System.out.println(bar);
        System.out.println(entrance);
}

//@param int tableNum, Book the table by it's table number
public void bookTable() {
        setFree(false);
}



}