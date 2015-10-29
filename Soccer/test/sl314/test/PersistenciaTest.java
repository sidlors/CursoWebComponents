/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sl314.test;

import java.util.List;
import java.util.Properties;
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
    private  EntityManagerFactory factory;
    private  EntityManager em;
    private  League league;

    @BeforeClass
    public static void setUpClass() {
        

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
       
        Properties props = new Properties();
        props.put("javax.persistence.transactionType", "RESOURCE_LOCAL");
        props.put("javax.persistence.jtaDataSource", "");
        props.put("javax.persistence.nonJtaDataSource", "");
        props.put("eclipselink.jdbc.driver", "com.mysql.jdbc.Driver");
        props.put("eclipselink.jdbc.url", "jdbc:mysql://localhost:3306/mydb");
        props.put("eclipselink.jdbc.user", "root");
        props.put("eclipselink.jdbc.password", "Semeolvid0");
        //esta propiedad es para 'dropear' tablas antes de correr la prueba unitaria
        //props.put("eclipselink.ddl-generation", "drop-and-create-tables");
        props.put("eclipselink.logging.level", "INFO");
        factory = Persistence.createEntityManagerFactory("SoccerPU",props);
        em = factory.createEntityManager();
        
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
            System.err.println(ex.getMessage());
            etx.rollback();
        }
     @Test
    public void ListLeagueTest() {

       List<League> lista=league.findLeagueEntities(em,true,5,1);
       assertNotNull(lista);
       
       for(League liga:lista){
           System.out.println(liga.getIdLeague() +" "+ liga.getName());
       }

    }

    }
}
