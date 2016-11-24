package com.clouway.travel_agency;

import com.clouway.travel_agency.domain_layer.Person;
import com.clouway.travel_agency.domain_layer.PersonRepository;
import com.clouway.travel_agency.domain_layer.Trip;
import com.clouway.travel_agency.domain_layer.TripRepository;
import com.clouway.travel_agency.persistence_layer.DataStore;
import com.clouway.travel_agency.persistence_layer.PersistencePersonRepository;
import com.clouway.travel_agency.persistence_layer.PersistenceTripRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by zumba on 15.11.16.
 *
 * @author Alexander Vladimirov
 *         <alexandervladimirov1902@gmail.com>
 */
public class InsertAndUpdateTripTest {
    @Rule
    public DataBaseConnectionRule dataBaseConnectionRule = new DataBaseConnectionRule();
    private Connection connection = dataBaseConnectionRule.connection;
    private TripRepository tripRepository = new PersistenceTripRepository(connection);
    private PersonRepository personRepository = new PersistencePersonRepository(connection);
    private DataStore dataStore = new DataStore(connection);
    public InsertAndUpdateTripTest() throws SQLException {
    }

    @Before
    public void createPeopleTableAndPopulate() {
        dataStore.update("SET FOREIGN_KEY_CHECKS = 0");
        dataStore.update("TRUNCATE TABLE Trip");
        dataStore.update("TRUNCATE TABLE People");
        dataStore.update("SET FOREIGN_KEY_CHECKS = 1");  personRepository.register(new Person("Gosho", 9090909090L, 23, "email@email.com"));
        personRepository.register(new Person("Pesho", 9191919191L, 27, "gemail@gemail.com"));
        personRepository.register(new Person("Petur", 9292929292L, 28, "semail@semail.com"));
       }

    @Test(expected = IndexOutOfBoundsException.class)
    public void deleteTrip() {
        tripRepository.register(new Trip(9090909090L, new java.sql.Date(1290262492000L), new java.sql.Date(1290694492000L), "Pleven"));
        tripRepository.delete(9090909090L);
        List<Trip> trips = tripRepository.getAll();

        trips.get(0);
    }

    @Test
    public void updateTrip() {
        System.out.println(new Date(1290262492000L));
        tripRepository.register(new Trip(9090909090L, new java.sql.Date(1290262492000L), new java.sql.Date(1290694492000L), "Pleven"));
        Trip expected = new Trip(9090909090L, new java.sql.Date(1290262492000L), new java.sql.Date(1290694492000L), "Sofia");
        tripRepository.updateTrip(new Trip(9090909090L, new java.sql.Date(1290262492000L), new java.sql.Date(1290694492000L), "Sofia"));
        List<Trip> trips = tripRepository.getAll();
        Trip actual = trips.get(0);
        assertThat(actual.equals(expected), is(true));
    }
}