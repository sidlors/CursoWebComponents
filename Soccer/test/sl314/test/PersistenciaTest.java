/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sl314.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sl314.persistencia.League;
//import sl314.persistencia.control.LeagueJpaController;

/**
 *
 * @author juanmanuel
 */
public class PersistenciaTest {

   //private LeagueJpaController control;
    private static EntityManagerFactory factory;
    private static EntityManager em;
    private  League league;

    @BeforeClass
    public static void setUpClass() {
        factory = Persistence.createEntityManagerFactory("SoccerPU");
        em = factory.createEntityManager();

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        league=new League();
        league.setName("Liga MX");
        league.setYear(2000);
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void TestCreate() {
        try {
            EntityTransaction etx = em.getTransaction();
            
            etx.begin();
            em.persist(league);
            etx.commit();
            em.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
