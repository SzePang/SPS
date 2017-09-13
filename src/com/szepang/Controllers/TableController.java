package com.szepang.Controllers;

import com.szepang.Messages.Error;
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

            TableEntity table1 = new TableEntity(theTableNum, theSeatQty, isItfree, byWall, byWindow, byToilet,
                    byKitchen, byWalkway, byBar, byEntrance);

            //ADDS the table to the database
            DBInteraction.addTable(table1);

            //TEST: Check that all fields are appropriately added
            table1.printTableProperty();

            ModelAndView model = new ModelAndView("/AddTableSuccess");
            model.addObject("headerMessage", "Successfully added table " + theTableNum);
            model.addObject("table1", table1);

            return model;
        } else {
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
        //////////////////////////////////////////TEST
        System.out.println("The amount of tables in the tableList: " + tNum);

        //If no table exists, DBInteraction will return 0
        if (tNum == 0) {
            ModelAndView model = new ModelAndView("GenericNoSuccess");
            model.addObject("result1",
                    "Sorry, there are no available tables that can seat the amount of people in your party.");
            return model;
        }
        //If a table exists to sit the party, perform table seatQty checks and person priority checks
        else{
            //Check if priorityList contains any string of person priorities, if none, use NoPriority class
            if (priorityList == null || priorityList.length == 0) {

                //Filter the tableList to get tables that are equal in size to the number of people to be seated
                List<TableEntity> seatSort = tableSeatSet(tableList, numOfPeople, true);

                //Check if a filtering seat array is empty
                if (seatSort.isEmpty()) {
                    ModelAndView model = new ModelAndView("NoEqualTable");
                    /*model.addObject("result1", tableList);*/
                    model.addObject("result1", numOfPeople);
                    model.addObject("result2", true);
                    return model;
                } else {
                    //toDo difference check and add threshold so tables suitable for potential priority groups are not first chosen
                    NoPriority noPriority = new NoPriority();
                    double threshold = 0.6;
                    List<TableEntity> filteredList = new ArrayList<>();
                    for (TableEntity tTable : seatSort) {
                        double tableSimilarity = similarity(noPriority.getComparableArray(), noPriority.getComparableArray(tTable));
                        if (tableSimilarity > threshold) {
                           filteredList.add(tTable);
                        }
                        //////TEST
                        System.out.println("table number: " + tTable.getTableNumber() + " table sim: " +tableSimilarity );
                    }

                    if(filteredList.isEmpty()){
                        threshold = 0.4;
                        for (TableEntity tTable : seatSort) {
                            double tableSimilarity = similarity(noPriority.getComparableArray(), noPriority.getComparableArray(tTable));
                            if (tableSimilarity > threshold) {
                                filteredList.add(tTable);
                            }
                        }
                    }

                    if(filteredList.isEmpty()){ //if nothing was found above the thresholds
                        //pick a random table from the list of suitable tables with seatQty field filtered
                        //Give Random anything of numOfPeople
                        //Don't do similarity anymore, give any seat from seatSort (including tables which would have been best for priority groups)
                        Random r = new Random();
                        int n = r.nextInt(seatSort.size());
                        TableEntity randomEntity = seatSort.get(n);
                        tNum = randomEntity.getTableNumber();
                    }else { //if there are seats above a threshold
                        //pick a random table from the list of suitable tables with seatQty field filtered
                        Random r = new Random();
                        int n = r.nextInt(filteredList.size());
                        TableEntity randomEntity = filteredList.get(n);
                        tNum = randomEntity.getTableNumber();
                    }
                }
/*
                //Commented out block of code below to allow a table to be picked from a list at random
                //The following code gets only one table by best similarity score
                NoPriority noPriority = new NoPriority();
                double score = 0.0;
                for (TableEntity tTable : tableList) {
                    double tableSimilarity = similarity(noPriority.getComparableArray(), noPriority.getComparableArray(tTable));
                    if (score < tableSimilarity) {
                        score = tableSimilarity;
                        tNum = tTable.getTableNumber();
                    }
                    //TEST
                    System.out.println("--------------------------");
                    System.out.println("Table no. : " + tTable.getTableNumber() + " Similarity: " + tableSimilarity);
                }*/
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

                List<TableEntity> seatSort = tableSeatSet2(tableList, numOfPeople, false);

                if (!seatSort.isEmpty()) { //Check if a filtering seat array is not empty

                    for (TableEntity tTable : seatSort) {
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
                if (seatSort.isEmpty()) {
                    ModelAndView model = new ModelAndView("GenericNoSuccess");
                    model.addObject("result1", "Could not find a table that allows " +
                            "a remainder of two seats for the party");
                    return model;
                }
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
        if(tableMap.isEmpty())  //Check that the Map is not empty
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
    //Second attempt in searching for a table with max remainder seat of 2
    @RequestMapping("/searchAgain.html/{result1} {result2}")
    //todo consider making tableList a integer array
    public ModelAndView findAgain(@PathVariable(value="result1") int numOfPeople,
                                  @PathVariable(value="result2") Boolean noPriority){

        List<TableEntity> tableList = DBInteraction.tbMatchList(numOfPeople); //all of them

        List<TableEntity> seatSort = tableSeatSet2(tableList, numOfPeople, noPriority); // numOfPeople +2
        int tNum = 0;

        if (!seatSort.isEmpty() && noPriority) {
            //todo Add threshold so table appropriate for potential priority groups are not picked initially in the first randomizer
            //pick a random table from the list of suitable tables with seatQty field filtered

            NoPriority noPriorityObject = new NoPriority();
            double threshold = 0.6;
            List<TableEntity> filteredList = new ArrayList<>();
            for (TableEntity tTable : seatSort) {
                double tableSimilarity = similarity(noPriorityObject.getComparableArray(), noPriorityObject.getComparableArray(tTable));
                if (tableSimilarity > threshold) {
                    filteredList.add(tTable);
                }
                //////TEST
                System.out.println("table number: " + tTable.getTableNumber() + " table sim: " +tableSimilarity );
            }

            if(filteredList.isEmpty()){
                threshold = 0.4;
                for (TableEntity tTable : seatSort) {
                    double tableSimilarity = similarity(noPriorityObject.getComparableArray(), noPriorityObject.getComparableArray(tTable));
                    if (tableSimilarity > threshold) {
                        filteredList.add(tTable);
                    }
                }
            }


            if(filteredList.isEmpty()){ //if nothing was found above the thresholds
                //pick a random table from the list of suitable tables with seatQty field filtered
                //Give Random anything of numOfPeople + 2
                //Don't do similarity anymore, give any seat from seatSort (including tables which would have been best for priority groups)
                Random r = new Random();
                int n = r.nextInt(seatSort.size());
                TableEntity randomEntity = seatSort.get(n);
                tNum = randomEntity.getTableNumber();
            }else { //if there are seats above a threshold
                //pick a random table from the list of suitable tables with seatQty field filtered
                Random r = new Random();
                int n = r.nextInt(filteredList.size());
                TableEntity randomEntity = filteredList.get(n);
                tNum = randomEntity.getTableNumber();
            }



            ModelAndView model = new ModelAndView("FoundSuccess");
            model.addObject("result",tNum);

            return model;

        }

        ModelAndView model = new ModelAndView("GenericNoSuccess");
        model.addObject("result1",tNum + " tables with a remainder of two seats available" +
                "to fit your party");
        return model;
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
    //Give booking table form, allows the user to book ANY free table
    @RequestMapping(value="/bookATable", method = RequestMethod.GET)
    public ModelAndView bookATable() {

        ModelAndView model = new ModelAndView("BookATable");
        model.addObject("msg","Find a table");

        return model;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Book a table by its table number
    @RequestMapping(value = "/bookAnyTable", method = RequestMethod.POST)
    public ModelAndView bookTheTable(@RequestParam("tableNumber") int theTable) {

        if(theTable < 1){ //Do not allow the user to enter 0 or negative values
            ModelAndView model = new ModelAndView("ErrorPage");
            model.addObject("result1", Error.TABLE_CANNOT_BE_0.getDescription());
            return model;
        }

        TableEntity t = DBInteraction.getTableEntity(theTable);
        if(t == null){ //Do not allow the user to enter a table number not in DB
            ModelAndView model = new ModelAndView("ErrorPage");
            model.addObject("result1", Error.NON_EXISTANT.getDescription());
            return model;
        }
        if(!t.isFree()){ //check if the table is free to book
            ModelAndView model = new ModelAndView("ErrorPage");
            model.addObject("result1", Error.TABLE_FREE_FALSE.getDescription());
            return model;
        }
        if(t.getTableNumber() == theTable && t.isFree()) {
            DBInteraction.bookT(theTable);
        }

        ModelAndView model = new ModelAndView("GenericSuccess");
        model.addObject("someResult1", "table " +theTable+ " is booked!");
        return model;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Tells the dispatcher servlet to give the form to FREE tables
    @RequestMapping(value="/freeATable", method = RequestMethod.GET)
    public ModelAndView freeATable() {

        ModelAndView model = new ModelAndView("FreeTable");
        model.addObject("msg","Ensure that the table is cleaned and reset" +
                " before marking this table as free!");

        //TODO - DO a check to see if the table number specified, exists in DB

        return model;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FREE all tables
    @RequestMapping(value = "/freeAllTables", method = RequestMethod.GET)
    public ModelAndView freeAllTables() { //Sets all the tables' free field to true

        DBInteraction.freeAllTables();

        ModelAndView model = new ModelAndView("GenericSuccess");
        model.addObject("someResult1", "Freeing all tables");
        return model;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FREE a single table by its table number
    @RequestMapping(value = "/freeTheTable", method = RequestMethod.POST)
    public ModelAndView freeTableNum(@RequestParam("theTable") int theTable) {

        if(theTable < 1){ //Do not allow the user to enter 0 or negative values
            ModelAndView model = new ModelAndView("ErrorPage");
            model.addObject("result1", Error.TABLE_CANNOT_BE_0.getDescription());
            return model;
        }

        TableEntity t = DBInteraction.getTableEntity(theTable);
        if(t == null){ //Do not allow the user to enter a table number not in DB
            ModelAndView model = new ModelAndView("ErrorPage");
            model.addObject("result1", Error.NON_EXISTANT.getDescription());
            return model;
        }
        if(t.isFree()){ //check if the table is free already
            ModelAndView model = new ModelAndView("ErrorPage");
            model.addObject("result1", Error.TABLE_FREE_TRUE.getDescription());
            return model;
        }
        if(t.getTableNumber() == theTable && !t.isFree()) {
            DBInteraction.freeTheTable(theTable);
        }

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
    @RequestMapping(value = "/deleteTheTable", method = RequestMethod.POST)
    public ModelAndView deleteTableNum(@RequestParam("theTable") int theTable) {

        if(DBInteraction.getTableEntity(theTable) == null){ //Check that the table exits first in the DB
            ModelAndView model = new ModelAndView("ErrorPage");
            model.addObject("result1", Error.NON_EXISTANT.getDescription());
            return model;
        }

        DBInteraction.deleteTableNum(theTable);

        ModelAndView model = new ModelAndView("GenericSuccess");
        model.addObject("someResult1", "table " +theTable+ " is deleted");
        return model;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //TODO - What if a user delete a negative int
    @RequestMapping(value = "/deleteAllTables")
    public ModelAndView deleteAllTables() {


        List<TableEntity> tList = DBInteraction.allTables();

        if(tList.isEmpty()){
            ModelAndView model = new ModelAndView("ErrorPage");
            model.addObject("result1", Error.NO_TABLES_IN_DB.getDescription());
            return model;
        }

        DBInteraction.deleteAllTables();

        ModelAndView model = new ModelAndView("GenericSuccess");
        model.addObject("someResult1", "All tables are deleted");
        return model;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //TODO PRINT ALL tables
    @RequestMapping(value = "/displayAllTables")
    public ModelAndView printAllTables() {

        List<TableEntity> tList = DBInteraction.allTables();

        if(tList.isEmpty()){
            ModelAndView model = new ModelAndView("ErrorPage");
            model.addObject("result1", Error.NO_TABLES_IN_DB.getDescription());
            return model;
        }

        ArrayList<String> tableStatus = new ArrayList<>();

        for (TableEntity t : tList) {
            String tableNum = "Table: " + Integer.toString(t.getTableNumber()) +
                    " - Unoccupied: " + String.valueOf(t.isFree());
            tableStatus.add(tableNum);
        }

        ModelAndView model = new ModelAndView("DisplayTables");
        model.addObject("result1", "All tables in the system");
        model.addObject("table1", tableStatus);
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Filter the list of suitable tables by it's equal seat quantity
    public static List<TableEntity> tableSeatSet (List<TableEntity> tSet, int nPeople, Boolean noPriority) {

        List<TableEntity> tableSet = new ArrayList<>();
        Boolean notFound = true;
        if (noPriority) {
            ///////TEST
            System.out.println("noPriority = true therefore,");
            System.out.println("Looking tables that match the equal num of People:");

            for (TableEntity t : tSet) {
                if (t.getSeatQty() == nPeople) {
                    tableSet.add(t);
                    notFound = false;
                    //////TEST
                    System.out.println("NumOfPeople: " + nPeople);
                    System.out.println("Table num: " + t.getTableNumber() + " SeatQty: " + t.getSeatQty());
                    System.out.println("------------------------------------------");
                }
            }
        }
        return tableSet;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Second check for a suitable seat allowing a seat remainder of 2
    public static List<TableEntity> tableSeatSet2 (List<TableEntity> tSet, int nPeople, Boolean noPriority) {

        List<TableEntity> tableSet = new ArrayList<>();
        Boolean notFound = true;
        //Check again just incase a table with equal seats to the group number becomes available
        if (noPriority) {
            ///////TEST
            System.out.println("noPriority = true therefore,");
            System.out.println("Looking tables that match the equal num of People:");

            for (TableEntity t : tSet) {
                if (t.getSeatQty() == nPeople) {
                    tableSet.add(t);
                    notFound = false;
                    //////TEST
                    System.out.println("NumOfPeople: " + nPeople);
                    System.out.println("Table num: " + t.getTableNumber() + " SeatQty: " + t.getSeatQty());
                    System.out.println("------------------------------------------");
                }
            }
        }
        if (notFound || !noPriority) { //if still not found or if there is a Priority
            /////////TEST
            System.out.println("notFound == true || noPriority = false, therefore,");
            System.out.println("looking for tables that are numOfPeople + 2...");

            for (TableEntity t : tSet) {

                if (t.getSeatQty() <= nPeople + 2 ) {
                    //todo add in descending order
                    tableSet.add(t);
                    ////////TEST
                    System.out.println("NumOfPeople: " + nPeople);
                    System.out.println("Found table num: " + t.getTableNumber() + " - Seats: " + t.getSeatQty() );
                }
            }
        }
        else {
            System.out.println("The current tables that can seat your party have a seat remainder over 2.");
        }
        return tableSet;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////




//End curly bracket of class
}
