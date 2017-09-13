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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Gives all tables in the database
    public static List<TableEntity> allTables() {

    // Configure the session factory
        configureSessionFactory();

        Session session = null;

        List<TableEntity> tableEntityList = new ArrayList<>();

        try {
            session = sessionFactory.openSession();

        // Fetching saved data - FOR CHECKING CURRENT TABLES IN DB
        tableEntityList = session.createQuery("from TableEntity").list();

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
        return tableEntityList;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
     * @return tableEntityList, return an array list of table entities that are free and match the user seat request
     */
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**FREE a Table entity in the database by the table number
     *@param tableNumId The table number
     */
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






