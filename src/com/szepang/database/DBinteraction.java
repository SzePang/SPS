package com.szepang.database;

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
public class DBinteraction {

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

    /**
     * Adds a table to the database
     * @param table pu
     */
    public static void addTable(TableEntity table) {
        // Configure the session factory
        configureSessionFactory();

        Session session = null;
        Transaction tx=null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Creating Table entity that will be save to the sqlite database
            //TableEntity tTable1 = new TableEntity(1, 5, false, false, false, false, false, false,
            //        false, false);
           // TableEntity tTable2 = new TableEntity(2, 6, false, false, false, false, false, false,
           //         false, false);

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

    /**Deletes a Table entity in the database by the table number
     *
     * @param tableNumId
     */
//TODO Check that this works - PASS
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

    /**
     * Deletes all table entries in the database
     */
    //TODO Check that this works - PASS
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

    /**Prints all tables in the database*/
    public void printAllTables () {

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
            System.out.println("t-num: " + tTable.getTableNumber());
        }
            // Committing the change in the database.
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
    }


//TODO check this works - MUST PROPERLY IMPLEMENT
    public static TableEntity getTableEntity(int tableNumId) {
        // Configure the session factory
        configureSessionFactory();

        Session session = null;
        Transaction tx = null;
        TableEntity thisT = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            thisT = session.get(TableEntity.class, tableNumId);

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
        return thisT;
    }





//This curly bracket is this class scope
}






