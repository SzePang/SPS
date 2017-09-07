package com.szepang.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.szepang.Models.TableEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.szepang.htmlviews.HtmlBuilder;


/**
 * Created by Sze on 20/08/2017.
 */
public class DBInteraction {

    private static SessionFactory sessionFactory = null;
    private static StandardServiceRegistry StandardServiceRegistry = null;

    private static SessionFactory configureSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(TableEntity.class);

        Properties properties = configuration.getProperties();

        StandardServiceRegistry = new StandardServiceRegistryBuilder().applySettings(properties).build();
        sessionFactory = configuration.buildSessionFactory(StandardServiceRegistry);

        return sessionFactory;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Adds a table to the database
     * @param table table to be added
     *              TEST - PASS
     */
    public static void addTable(TableEntity table) {
        // Configure the session factory
        configureSessionFactory();

        Session session = null;
        Transaction tx=null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Saving to the database
            session.save(table);

            // TODO may allow user to input several tables at a time
            //session.save(tTable2);

            // Committing the change in the database.
            session.flush();
            tx.commit();

            // Fetching saved data
            List<TableEntity> tableEntityList = session.createQuery("from TableEntity").list();

            for (TableEntity tTable : tableEntityList) {
                System.out.println("t-num: " + tTable.getTableNumber());
                tTable.printTableProperty();
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            tx.rollback();
        } finally{
            if(session != null) {
                session.close();
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**Deletes a Table entity in the database by the table number
     *@param tableNumId The table number
     *                  TEST - PASS
     */
    public static void deleteTableNum(int tableNumId) {
        // Configure the session factory
        configureSessionFactory();

        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            TableEntity thisTable = getTableEntity(tableNumId);

            session.delete(thisTable);

            // Committing the change in the database.
            session.flush();
            tx.commit();

            // Fetching saved data - FOR CHECKING CURRENT TABLES IN DB
            List<TableEntity> tableEntityList = session.createQuery("from TableEntity").list();
            for (TableEntity tTable : tableEntityList) {
                System.out.println("t-num: " + tTable.getTableNumber());
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            tx.rollback();
        } finally{
            if(session != null) {
                session.close();
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Deletes all table entries in the database
     *                  TEST - PASS
     */
    public static void deleteAllTables() {
        // Configure the session factory
        configureSessionFactory();

        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            session.createQuery("delete from TableEntity").executeUpdate();

            // Committing the change in the database.
            session.flush();
            tx.commit();

            // Fetching saved data - FOR CHECKING CURRENT TABLES IN DB
            List<TableEntity> tableEntityList = session.createQuery("from TableEntity").list();
            for (TableEntity tTable : tableEntityList) {
                System.out.println("t-num: " + tTable.getTableNumber());
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            tx.rollback();
        } finally{
            if(session != null) {
                session.close();
            }
        }
    }

    //Prints all tables in the database
    //TODO --------------------------------MUST TEST
    public static void printAllTables() {

    // Configure the session factory
        configureSessionFactory();

        Session session = null;

        try {
            session = sessionFactory.openSession();

        // Fetching saved data - FOR CHECKING CURRENT TABLES IN DB
        List<TableEntity> tableEntityList = session.createQuery("from TableEntity").list();
        for (TableEntity tTable : tableEntityList) {

    System.out.println("t-num: " + tTable.getTableNumber() );

            //Print out all the fields of the entity
            tTable.printTableProperty();

        }
            // Flushes all the streams of data
            session.flush();

        }
        catch (Exception ex) {
            ex.printStackTrace();



        } finally{
            if(session != null) {
                session.close();
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //FOR TESTING - CHECK ALL TABLES ARE VIEWABLE
    public static String getHtmlForAllTables() {

        String temp = "";

        // Configure the session factory
        configureSessionFactory();

        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Fetching saved data - FOR CHECKING CURRENT TABLES IN DB
            List<TableEntity> tableEntityList = session.createQuery("from TableEntity").list();
            for (TableEntity tTable : tableEntityList) {

                temp += HtmlBuilder.getSimpleTableCard("Table " +tTable.getTableNumber(),
                                                        "Properties: " +tTable.toString());

                //Print out all the fields of the entity
                tTable.printTableProperty();

            }
            // Flushes all the streams of data
            session.flush();
            tx.commit();

        }
        catch (Exception ex) {
            ex.printStackTrace();

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            tx.rollback();

        } finally{
            if(session != null) {
                session.close();
            }
        }
        return temp;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //TODO check this works - MUST PROPERLY IMPLEMENT ----- MUST TEST
    //@return TableEntity returns a TableEntity object
     // by looking for its unique table number
     //
    public static TableEntity getTableEntity(int tableNumId) {
        // Configure the session factory
        configureSessionFactory();

        Session session = null;
        TableEntity thisT = null;

        try {
            session = sessionFactory.openSession();

            thisT = session.get(TableEntity.class, tableNumId);

            // Flushes all the streams of data
            session.flush();

        }
        catch (Exception ex) {
            ex.printStackTrace();

        } finally{
            if(session != null) {
                session.close();
            }
        }
        return thisT;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


/**@param numOfPeople number of people
     Return the table that matches the Seat Quantity
     Also checks that the table is Free to seat people*/
 //TODO --------- MUST TEST - PASS
    public static int tbMatchSeat (int numOfPeople) {

        // Configure the session factory
        configureSessionFactory();
        Session session = null;

        int tNum = 0;

        try {
            session = sessionFactory.openSession();

            //TODO Consider if a client makes table called 0
            int tableCollection = 0;
            //GETS the number of entries in the table of Tables
            long tableEntries = (long) session.createQuery("select count(*) from  TableEntity ")
                    .uniqueResult();

            List<TableEntity> tableEntityList = session.createQuery("from TableEntity").list();

            boolean notFound = true;

            while (notFound && tableCollection < tableEntries) {

                for (TableEntity tTable : tableEntityList) {
                    int numSeats = tTable.getSeatQty();
                    if (numSeats == numOfPeople && tTable.isFree()) {
                        //TODO chuck constraint code in here
                        tNum = tTable.getTableNumber();
                        notFound = false;
                        break;
                    }
                    tableCollection++;
                }
            }

            // Committing the change in the database.
            session.flush();

        } catch (Exception ex) {
            ex.printStackTrace();



        } finally {
            if (session != null) {
                session.close();
            }
        }
        return tNum;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @param numOfPeople number of people that need to be seated
     * @return tableEntityList, return an array list of table entities that are free and match the user seatQty request
     */
    //TODO --------- MUST TEST
    public static List<TableEntity> tbMatchList (int numOfPeople) {

        // Configure the session factory
        configureSessionFactory();
        Session session = null;
        Transaction tx = null;

        List<TableEntity> tableEntityList = new ArrayList<>();

        session = sessionFactory.openSession();
        tx = session.beginTransaction();

        try {
            session = sessionFactory.openSession();

            //TODO Consider if a client makes table called 0
            tableEntityList = session.createQuery("from TableEntity as t where t.free =" +
                    "true and t.seatQty >= :numOfPeople").setParameter("numOfPeople", numOfPeople).list();

            // Committing the change in the database.
            session.flush();
           tx.commit();

        } catch (Exception ex) {
            ex.printStackTrace();

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            tx.rollback();

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return tableEntityList;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////



  /*  /**@param numOfPeople number of people
    Return the table that matches the Seat Quantity plus disability priority
    Also checks that the table is Free to seat people*//*
    public static int disabledMatchSeat (int numOfPeople, Priorities priorities) {

        // Configure the session factory
        configureSessionFactory();
        Session session = null;

        int tNum = 0;
        double previousSim = 0.0;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();

            //TODO Filter request to get tables that are free and can seat the quantity of people
            List<TableEntity> tableEntityList = session.createQuery("from TableEntity as t where t.free =" +
                    "true and t.seatQty >= :numOfPeople").setParameter("numOfPeople", numOfPeople).list();

            int[] featuresPrioritised = priorities.getComparableArray();


                for (TableEntity tTable : tableEntityList) {
                    int[] TableProperties = priorities.getComparableArray(tTable);
                    double tableSimalarity = TableController.similarity(featuresPrioritised, TableProperties);
                    System.out.println("Table " + tTable.getTableNumber() + " = " + tableSimalarity);

                    if(previousSim < tableSimalarity){
                        previousSim = tableSimalarity;
                        tNum = tTable.getTableNumber();
                    }

            }

            // Committing the change in the database.
            session.flush();
            tx.commit();

        } catch (Exception ex) {
            ex.printStackTrace();

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            tx.rollback();

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return tNum;
    }
*/
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //TODO CHECK THIS WORKS MUST TEST - PASS
//@param tNum books the table number specified
    public static void bookT(int tNum) {

        // Configure the session factory
        configureSessionFactory();

        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            List<TableEntity> tableEntityList = session.createQuery("from TableEntity").list();

        boolean notFound = true;
        while (notFound) {
            for (TableEntity tTable : tableEntityList ) {
                int tt = tTable.getTableNumber();
                if (tt == tNum) {
                    notFound = false;
                    tTable.bookTable();
                    //TEST Check that the appropriate fields have changed
                    tTable.printTableProperty();
                }
            }
        }
            // Committing the change in the database.
            session.flush();
            tx.commit();

    } catch (Exception ex) {
        ex.printStackTrace();

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            tx.rollback();

    } finally {
        if (session != null) {
            session.close();
        }
    }
}


//TODO CHECK THIS WORKS MUST TEST - PASS
    // FREE all tables
    public static void freeAllTables() {

        // Configure the session factory
        configureSessionFactory();

        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            List<TableEntity> tableEntityList = session.createQuery("from TableEntity").list();

        for (TableEntity tTable : tableEntityList) {
            tTable.setFree(true);

            //TEST Check that the appropriate fields have changed
            tTable.printTableProperty();
        }

            // Committing the change in the database.
            session.flush();
            tx.commit();

        } catch (Exception ex) {
            ex.printStackTrace();

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            tx.rollback();

        } finally {
            if (session != null) {
                session.close();
            }
        }

    }


    /**FREE a Table entity in the database by the table number
     *@param tableNumId The table number
     */
    //TODO CHECK THIS _____ PASS
    public static void freeTheTable(int tableNumId) {
        // Configure the session factory
        configureSessionFactory();

        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            TableEntity thisT = null;
            thisT = session.get(TableEntity.class, tableNumId);
            thisT.setFree(true);

            // Committing the change in the database.
            session.flush();
            tx.commit();

            // Fetching saved data - FOR CHECKING CURRENT TABLES IN DB
            List<TableEntity> tableEntityList = session.createQuery("from TableEntity").list();
            for (TableEntity tTable : tableEntityList) {
                System.out.println("t-num: " + tTable.getTableNumber());
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            tx.rollback();

        } finally{
            if(session != null) {
                session.close();
            }
        }
    }



//This curly bracket is this class scope
}






