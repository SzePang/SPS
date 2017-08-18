package com.szepang.Controllers;

import Models.Table;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;

/**
 * Created by Sze on 12/08/2017.
 */

@Controller
public class TableController {

    //CREATE ARRAY LIST OF TABLES WHEN TableController object is created
    TableListDBnoImplemented tbl1 = new TableListDBnoImplemented();

    @RequestMapping(value = "/addTable.html", method = RequestMethod.GET)
    public ModelAndView getaddTable() {

        ModelAndView model = new ModelAndView("AddTable");

        return model;
    }

    @RequestMapping(value = "/inputTable.html", method = RequestMethod.POST)
    public ModelAndView inputTable(@RequestParam("tableNumber") int theTableNum,
                                   @RequestParam("seatQty") int theSeatQty,
                                   @RequestParam(name="free",defaultValue="false") boolean isItfree,
                                   @RequestParam(name="wall", defaultValue="false") boolean byWall,
                                   @RequestParam(name="window", defaultValue="false") boolean byWindow,
                                   @RequestParam(name="toilet", defaultValue="false") boolean byToilet,
                                   @RequestParam(name="kitchen", defaultValue="false") boolean byKitchen,
                                   @RequestParam(name="walkway", defaultValue="false") boolean byWalkway,
                                   @RequestParam(name="bar", defaultValue="false") boolean byBar,
                                   @RequestParam(name="entrance", defaultValue="false") boolean byEntrance) {



        Table table1 = new Table(theTableNum, theSeatQty, isItfree, byWall, byWindow, byToilet,
        byKitchen, byWalkway, byBar, byEntrance);

        //TEMPORARY: ADD THE CREATED TABLE TO THE ARRAY LIST OF TABLES - tabl1
        tbl1.addTableToList(table1);

        //TODO check that the table number the user is trying to create does not yet exist in the DB/array list tbl1

        //TEST: Check that all fields are appropriately added
                table1.printTableProperty();
        //TEST: Check that the created table has been added to the array list
                tbl1.printAllTables();


        ModelAndView model = new ModelAndView("AddTableSuccess");
        model.addObject("headerMessage", "Succesfully added table " +theTableNum );
        model.addObject("table1", table1);

        return model;

    }

    //Tells the dispatcher servlet to give the form for the user to fill out to search for appropriate table
    @RequestMapping(value="/findTable", method = RequestMethod.GET)
    public ModelAndView findTable() {

        ModelAndView model = new ModelAndView("FindTable");
        model.addObject("msg","Find a table");

        return model;
    }

    //Processes the tableLookup form
/*    @RequestMapping(value="/tableLookUp.html", method = RequestMethod.POST)
    public ModelAndView lookupTable (@RequestParam("numPeople") int pAmount) {


        //Method tbMatchSeat, matches the user specified customers with the seats of tables in list.
        int theTableNum = tbl1.tbMatchSeat(pAmount);

        //TODO write code to allow the user to specify the max seats the largest table can sit
        if(theTableNum > 0) {
            ModelAndView model = new ModelAndView("FoundSuccess");
            model.addObject("result", theTableNum);
            return model;
        }
        else {
            ModelAndView model = new ModelAndView("GenericNoSuccess");
            model.addObject("result",
                    "Sorry, there are no available tables that can seat the amount of people in your party.");
            return model;
        }

    }*/

    @RequestMapping(value="/tableLookUp.html", method = RequestMethod.POST)
    public ModelAndView lookupTable (@RequestParam("numPeople") int pAmount) {


        //Method tbMatchSeat, matches the user specified customers with the seats of tables in list.
        int theTableNum = tbl1.tbMatchSeat(pAmount);
        //TEST what is in theTableNum variable
        System.out.println(theTableNum);


        //TODO write code to allow the user to specify the max seats the largest table can sit
        if(theTableNum == 0) {
            ModelAndView model = new ModelAndView("GenericNoSuccess");
            model.addObject("result1",
                    "Sorry, there are no available tables that can seat the amount of people in your party.");
            return model;
        }
        else {
            ModelAndView model = new ModelAndView("FoundSuccess");
            model.addObject("result", theTableNum);
            return model;
        }
    }



    @RequestMapping("/bookTable.html/{someID}")
    public ModelAndView bookTable(@PathVariable(value="someID") int theTableNum) {

        tbl1.bookT(theTableNum);

        ModelAndView model = new ModelAndView("BookedSuccess");
        model.addObject("tBooked", theTableNum);
        return model;

    }


    //FREE all tables
    @RequestMapping(value = "/freeAllTables", method = RequestMethod.GET)
    public ModelAndView freeAllTables() {

        tbl1.freeAllTables();

        ModelAndView model = new ModelAndView("GenericSuccess");
        model.addObject("someResult1", "Freeing all tables");
        return model;
    }

    //Book the table
/*    @RequestMapping(value = "/bookTable.html", method = RequestMethod.GET)
    public ModelAndView bookTable(@RequestParam("result") int tNum) {





        tbl1.bookT(tNum);

        ModelAndView bookedSModel = new ModelAndView("BookedSuccess");
        bookedSModel.addObject("tBooked", " " +tNum+ " ");

        return bookedSModel;
    }*/

}
