package com.szepang.Controllers;

import com.szepang.Models.TableEntity;

import com.szepang.database.DBInteraction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



/**
 * Created by Sze on 12/08/2017.
 */

@Controller
public class TableController {


    //Tells the dispatcher servlet to give the user the page to add a table
    @RequestMapping(value = "/addTable.html", method = RequestMethod.GET)
    public ModelAndView getAddTable() {

        ModelAndView model = new ModelAndView("AddTable");

        return model;
    }

    //The processing of the form, from /addTable.html
    @RequestMapping(value = "/inputTable.html", method = RequestMethod.POST)
    public ModelAndView inputTable(@RequestParam("tableNumber") int theTableNum,
                                   @RequestParam("seatQty") int theSeatQty,
                                   @RequestParam(name="free",defaultValue="false") boolean isItfree,
                                   @RequestParam(name="wall", defaultValue="false") boolean byWall,
                                   @RequestParam(name="window", defaultValue="false") boolean byWindow,
                                   @RequestParam(name="toilets", defaultValue="false") boolean byToilet,
                                   @RequestParam(name="kitchen", defaultValue="false") boolean byKitchen,
                                   @RequestParam(name="walkway", defaultValue="false") boolean byWalkway,
                                   @RequestParam(name="bar", defaultValue="false") boolean byBar,
                                   @RequestParam(name="entrance", defaultValue="false") boolean byEntrance) {


        //TODO MUST implement a check to make sure the user inputs a number for tableNum and seatQty!

        TableEntity table1 = new TableEntity(theTableNum, theSeatQty, isItfree, byWall, byWindow, byToilet,
        byKitchen, byWalkway, byBar, byEntrance);

        //ADDS the table to the database
        DBInteraction.addTable(table1);


        //TODO check that the table number the user is trying to create does not yet exist in the DB/array list tbl1

        //TEST: Check that all fields are appropriately added
                table1.printTableProperty();

        ModelAndView model = new ModelAndView("/AddTableSuccess");
        model.addObject("headerMessage", "Successfully added table " +theTableNum );
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

    @RequestMapping(value="/tableLookUp.html", method = RequestMethod.POST)
    public ModelAndView lookupTable (@RequestParam("numPeople") int pAmount) {

        //Method tbMatchSeat, matches the user specified customers with the seats of tables in list.
        int theTableNum = DBInteraction.tbMatchSeat(pAmount);

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

        DBInteraction dbInteract = new DBInteraction();

        dbInteract.bookT(theTableNum);

        ModelAndView model = new ModelAndView("BookedSuccess");
        model.addObject("tBooked", theTableNum);
        return model;

    }


    //FREE all tables
    @RequestMapping(value = "/freeAllTables", method = RequestMethod.GET)
    public ModelAndView freeAllTables() {

        DBInteraction.freeAllTables();

        ModelAndView model = new ModelAndView("GenericSuccess");
        model.addObject("someResult1", "Freeing all tables");
        return model;
    }


    ///deleteTable.html
    //Tells the dispatcher servlet to give the user the page to delete tables
    @RequestMapping(value="/deleteTable.html", method = RequestMethod.GET)
    public ModelAndView deleteATable() {

        ModelAndView model = new ModelAndView("DeleteTable");
        model.addObject("msg","Delete some tables");

        return model;
    }


    //TODO DELETE a table
    @RequestMapping(value = "/deleteTheTable", method = RequestMethod.POST)
    public ModelAndView deleteTableNum(@RequestParam("theTable") int theTable) {


        DBInteraction.deleteTableNum(theTable);

        ModelAndView model = new ModelAndView("GenericSuccess");
        model.addObject("someResult1", "table " +theTable+ " is deleted");
        return model;
    }

    //TODO DELETE ALL tables
    @RequestMapping(value = "/deleteAllTables")
    public ModelAndView deleteAllTables() {

        DBInteraction.deleteAllTables();

        ModelAndView model = new ModelAndView("GenericSuccess");
        model.addObject("someResult1", "All tables are deleted");
        return model;
    }

    //TODO PRINT ALL tables
    @RequestMapping(value = "/printAllTables")
    public ModelAndView printAllTables() {

        String toPrint = DBInteraction.getHtmlForAllTables();

        ModelAndView model = new ModelAndView("DisplayTables");
        model.addObject("someResult1", "All tables are deleted");
        model.addObject("html", toPrint);
        return model;
    }

}
