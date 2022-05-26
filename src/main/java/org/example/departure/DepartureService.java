package org.example.departure;

import lombok.RequiredArgsConstructor;
import org.example.postoffice.PostOfficeService;
import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DepartureService {
    private final SessionFactory sessionFactory;
    private Session session;

    private final PostOfficeService postOfficeService;

    @PostConstruct
    void init() {
        session = sessionFactory.openSession();
    }

    public List<Departure> getDepartures() {
        return session.createQuery("select u from Departure u",
                Departure.class).getResultList();
    }

    public Departure getDeparture(Long id){
        return session.get(Departure.class,id);
    }

    public Departure addDeparture(DepartureOnload onload) {
        Departure departure = new Departure(onload.getType(), onload.getDepartureDate());
        departure.setOffice(postOfficeService.getOffice(onload.getOfficeId()));
        var transaction = session.beginTransaction();
        session.saveOrUpdate(departure);
        transaction.commit();
        return departure;
    }

    public Departure modifyDeparture(DepartureOnload onload, Long id) {
        try {
            var transaction = session.beginTransaction();
            Departure departureFromDB = session.load(Departure.class, id);
            departureFromDB.setType(onload.getType());
            departureFromDB.setDepartureDate(onload.getDepartureDate());
            session.update(departureFromDB);
            transaction.commit();
            return departureFromDB;
        } catch (ObjectNotFoundException exception) {
            return null;
        }

    }

    public Departure deleteDeparture(Long id) {
        try {
            var transaction = session.beginTransaction();
            Departure departure = session.load(Departure.class, id);
            session.delete(departure);
            transaction.commit();
            return departure;
        } catch (ObjectNotFoundException exception) {
            return null;
        }
    }

    public List<Departure> filterDeparturesByType(String type){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Departure> departureCriteriaQuery = builder.createQuery(Departure.class);
        Root<Departure> root = departureCriteriaQuery.from(Departure.class);
        departureCriteriaQuery.select(root).where(builder.equal(root.get("type"),type));
        Query query = session.createQuery(departureCriteriaQuery);

        return query.getResultList();
    }

    public List<Departure> filterDepartures(String field, String param){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Departure> departureCriteriaQuery = builder.createQuery(Departure.class);
        Root<Departure> root = departureCriteriaQuery.from(Departure.class);

        switch (field) {
            case "type" -> {
                departureCriteriaQuery.select(root).where(builder.equal(root.get(field), param));
                Query typeQuery = session.createQuery(departureCriteriaQuery);
                return typeQuery.getResultList();
            }
            case "date" -> {
                departureCriteriaQuery.select(root).where(builder.equal(root.get("departureDate"), param.replace('_', '.')));
                Query dateQuery = session.createQuery(departureCriteriaQuery);
                return dateQuery.getResultList();
            }
            case "office" -> {
                departureCriteriaQuery.select(root).where(builder.equal(root.get(field).get("id"), Long.parseLong(param)));
                Query officeQuery = session.createQuery(departureCriteriaQuery);
                return officeQuery.getResultList();
            }
        }
        return new ArrayList<Departure>(0);

    }

    public List<Departure> filterDeparturesByDate(String date){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Departure> departureCriteriaQuery = builder.createQuery(Departure.class);
        Root<Departure> root = departureCriteriaQuery.from(Departure.class);
        departureCriteriaQuery.select(root).where(builder.equal(root.get("departureDate"),date));
        Query query = session.createQuery(departureCriteriaQuery);
        return query.getResultList();

    }

}
