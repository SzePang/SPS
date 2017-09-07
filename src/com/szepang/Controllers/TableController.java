package com.szepang.Controllers;

import com.szepang.PeoplePriorities.*;
import com.szepang.Models.TableEntity;

import com.szepang.database.DBInteraction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //The processing of the form, from /addTable.html
    @RequestMapping(value = "/inputTable.html", method = RequestMethod.POST)
    public ModelAndView inputTable(@RequestParam("tableNumber") int theTableNum,
                                   @RequestParam("seatQty") int theSeatQty,
                                   @RequestParam(name = "free", defaultValue = "false") boolean isItfree,
                                   @RequestParam(name = "wall", defaultValue = "1") int byWall,
                                   @RequestParam(name = "window", defaultValue = "1") int byWindow,
                                   @RequestParam(name = "toilets", defaultValue = "1") int byToilet,
                                   @RequestParam(name = "kitchen", defaultValue = "1") int byKitchen,
                                   @RequestParam(name = "walkway", defaultValue = "1") int byWalkway,
                                   @RequestParam(name = "bar", defaultValue = "1") int byBar,
                                   @RequestParam(name = "entrance", defaultValue = "1") int byEntrance) {

        //Check if the table already exists in the database before adding the table
        TableEntity tableEntity = null;
        tableEntity = DBInteraction.getTableEntity(theTableNum);
        if (tableEntity == null) {


            //TODO MUST implement a check to make sure the user inputs a number for tableNum and seatQty!
            TableEntity table1 = new TableEntity(theTableNum, theSeatQty, isItfree, byWall, byWindow, byToilet,
                    byKitchen, byWalkway, byBar, byEntrance);

            //ADDS the table to the database
            DBInteraction.addTable(table1);

            //TODO check that the table number the user is trying to create does not yet exist in the DB/array list tbl1
            //TEST: Check that all fields are appropriately added
            table1.printTableProperty();

            ModelAndView model = new ModelAndView("/AddTableSuccess");
            model.addObject("headerMessage", "Successfully added table " + theTableNum);
            model.addObject("table1", table1);

            return model;
        }
        else {
            ModelAndView model = new ModelAndView("/GenericNoSuccess");
            model.addObject("result1", "Table " + tableEntity.getTableNumber() + " already exists");

            return model;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /*Method that filters the form to determine the people priorities and then call the correct method to
    process the request*/
    @RequestMapping(value = "/findPriority", method = RequestMethod.POST)
    public ModelAndView findPriorityTable(@RequestParam(value = "numPeople") int numOfPeople,
                                  @RequestParam(value = "priorities", required = false) String[] priorityList) {

        //tableMap stored the table Number as a key and the sum of getDiffTotal() as a value
        Map<Integer, Integer> tableMap = new HashMap<>();

        //Check that there is a table that can sit the number of people first
        int tNum = 0;
        List<TableEntity> tableList = DBInteraction.tbMatchList(numOfPeople);
        tNum = tableList.size();
        //If no table exists, DBInteraction will return 0
        if (tNum == 0) {
            ModelAndView model = new ModelAndView("GenericNoSuccess");
            model.addObject("result1",
                    "Sorry, there are no available tables that can seat the amount of people in your party.");
            return model;
        }
        //If a table exists to sit the party, perform person priorities checks
        else {
            //Check if priorityList contains any string of person priorities
            if (priorityList == null || priorityList.length == 0) {
                NoPriority noPriority = new NoPriority();
                //List<TableEntity> suitableTableList = DBInteraction.tbMatchList(numOfPeople);

                double score = 0.0;
                for (TableEntity tTable : tableList) {
                    double tableSimalarity = similarity(noPriority.getComparableArray(), noPriority.getComparableArray(tTable));
                    if (score < tableSimalarity) {
                        score = tableSimalarity;
                        tNum = tTable.getTableNumber();
                    }
                }

            } else {
                //Priorities must have been selected therefore perform the following block of code
                //Get required priority objects and but them in a list
                List<Priorities> checkedPriorities = new ArrayList<>();
                for (String checked : priorityList) {
                    switch (checked) {
                        case "disabled":
                            checkedPriorities.add(new DisabilityPriorities());
                            break;
                        case "pregnant":
                            checkedPriorities.add(new PregnantPriorities());
                            break;
                        case "elder":
                            checkedPriorities.add(new ElderlyPriorities());
                            break;
                        case "child":
                            checkedPriorities.add(new ChildPriorities());
                            break;

                    }
                }

                for (TableEntity tTable : tableList) {
                    int sum = 0;
                    for (Priorities p : checkedPriorities) {
                        //the sum of the differences for all checked priorities
                        sum += getDiffTotal(p.getComparableArray(), p.getComparableArray(tTable));
                    }
                    //Store the table number as the key and the sum as the value in a Map
                    tableMap.put(tTable.getTableNumber(), sum);
                }

                //get the lowest scoring table
                tNum = getBestTableNumberInMap(tableMap);
            }
        }
        ModelAndView model = new ModelAndView("FoundSuccess");
        model.addObject("result", tNum);
        return model;
    }

    /**
     * @param tableMap
     * @return the table number that scored the lowest,
     * of the total difference of the Person Priorities against the Table Properties
     */
    int getBestTableNumberInMap(Map<Integer, Integer> tableMap) {
        //Check that the Map is not empty
        if(tableMap.isEmpty())
            throw new IllegalStateException("Number of tables cannot be 0 in length");

        int minValueInMap = (Collections.min(tableMap.values()));  // This will return SMALLEST value in the Hashmap
        for (Map.Entry<Integer, Integer> entry : tableMap.entrySet()) {  // Iterate through hashmap
            if (entry.getValue() == minValueInMap) {
                return entry.getKey();     // return the key with min value, key being the table number
            }
        }
        return 0; //shouldn't happen
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Tells the dispatcher servlet to give the form for the user to fill out to search for appropriate table
    @RequestMapping(value="/findTable", method = RequestMethod.GET)
    public ModelAndView findTable() {

        ModelAndView model = new ModelAndView("FindTable");
        model.addObject("msg","Find a table");

        return model;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping("/bookTable.html/{someID}")
    public ModelAndView bookTable(@PathVariable(value="someID") int theTableNum) {

        DBInteraction.bookT(theTableNum);

        ModelAndView model = new ModelAndView("BookedSuccess");
        model.addObject("tBooked", theTableNum);
        return model;

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Tells the dispatcher servlet to give the form to FREE tables
    @RequestMapping(value="/freeATable", method = RequestMethod.GET)
    public ModelAndView freeATable() {

        ModelAndView model = new ModelAndView("FreeTable");
        model.addObject("msg","Ensure that the table is cleaned and reset" +
                " before marking this table as free!");

        return model;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FREE all tables
    @RequestMapping(value = "/freeAllTables", method = RequestMethod.GET)
    public ModelAndView freeAllTables() {

        DBInteraction.freeAllTables();

        ModelAndView model = new ModelAndView("GenericSuccess");
        model.addObject("someResult1", "Freeing all tables");
        return model;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FREE a single table by its table number
    //TODO - What if a user free a negative int
    @RequestMapping(value = "/freeTheTable", method = RequestMethod.POST)
    public ModelAndView freeTableNum(@RequestParam("theTable") int theTable) {


        DBInteraction.freeTheTable(theTable);

        ModelAndView model = new ModelAndView("GenericSuccess");
        model.addObject("someResult1", "table " +theTable+ " is free!");
        return model;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///deleteTable.html
    //Tells the dispatcher servlet to give the user the page to delete tables
    @RequestMapping(value="/deleteTable.html", method = RequestMethod.GET)
    public ModelAndView deleteATable() {

        ModelAndView model = new ModelAndView("DeleteTable");
        model.addObject("msg","Delete some tables");

        return model;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //TODO - What if a user delete a negative int
    @RequestMapping(value = "/deleteTheTable", method = RequestMethod.POST)
    public ModelAndView deleteTableNum(@RequestParam("theTable") int theTable) {


        DBInteraction.deleteTableNum(theTable);

        ModelAndView model = new ModelAndView("GenericSuccess");
        model.addObject("someResult1", "table " +theTable+ " is deleted");
        return model;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //TODO - What if a user delete a negative int
    @RequestMapping(value = "/deleteAllTables")
    public ModelAndView deleteAllTables() {

        DBInteraction.deleteAllTables();

        ModelAndView model = new ModelAndView("GenericSuccess");
        model.addObject("someResult1", "All tables are deleted");
        return model;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //TODO PRINT ALL tables
    @RequestMapping(value = "/printAllTables")
    public ModelAndView printAllTables() {

        String toPrint = DBInteraction.getHtmlForAllTables();

        ModelAndView model = new ModelAndView("DisplayTables");
        model.addObject("result1", "All tables in the system");
        model.addObject("html", toPrint);
        return model;


    }

    //The following are static functions that TableController uses to perform calculations
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Tanimoto Coefficient based on equation from:
    //http://mines.humanoriented.com/classes/2010/fall/csci568/portfolio_exports/mvoget/similarity/similarity.html
    public static double similarity(int[] arr1, int[] arr2) {
        int n = arr1.length;
        double ab = 0.0;
        double a2 = 0.0;
        double b2 = 0.0;

        for (int i = 0; i < n; i++) {
            ab += arr1[i] * arr2[i];
            a2 += arr1[i] * arr1[i];
            b2 += arr2[i] * arr2[i];
        }
        return ab / (a2 + b2 - ab);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Java Euclidean distance code - https://www.codeproject.com/Questions/480279/JavaplusEuclideanplusdistancepluscode
    /**
     * @param arr1 int array of the priority ranks
     * @param arr2 int array of the table property
     * @return score
     */
    public static double getDiffTotal(int[] arr1, int[] arr2){
        int n = arr1.length;
        double score = 0;
        for(int i = 0; i < n; i++){
            score = score + Math.pow((arr1[i] - arr2[i]),2);
        }
        return Math.sqrt(score);
    }



//End curly bracket of class
}
