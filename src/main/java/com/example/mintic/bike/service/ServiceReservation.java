package com.example.mintic.bike.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mintic.bike.model.Reservation;
import com.example.mintic.bike.repository.RepositoryReservation;

@Service
public class ServiceReservation {
    
    @Autowired
    private RepositoryReservation repositoryReservation;

    
    public List<Reservation> getAll() {
        return repositoryReservation.getAll();
    }

    public Optional<Reservation> getReservation(int id) {
        return repositoryReservation.getReservation(id);
    }

    public Reservation save(Reservation reservation) {
        if (reservation.getIdReservation() == null) {
            return repositoryReservation.save(reservation);
        } else {
            Optional<Reservation> reservation1 = repositoryReservation.getReservation(reservation.getIdReservation());
            if (reservation1.isEmpty()) {
                return repositoryReservation.save(reservation);
            } else {
                return reservation;
            }
        }
    }

    public Reservation update(Reservation reservation){
        if(reservation.getIdReservation()!=null){
            Optional<Reservation> e = repositoryReservation.getReservation(reservation.getIdReservation());
            if(!e.isEmpty()){
                if(reservation.getStartDate()!=null){
                    e.get().setStartDate(reservation.getStartDate());
                }
                if (reservation.getDevolutionDate()!=null){
                    e.get().setDevolutionDate(reservation.getDevolutionDate());
                }
                if (reservation.getStatus()!=null){
                    e.get().setStatus(reservation.getStatus());
                }
                repositoryReservation.save(e.get());
                return e.get();
            } else {
                return reservation;
            }
        } else {
            return reservation;
        }
    }

    public boolean deleteReservation(int id) {
        Boolean d = getReservation(id).map(reservation -> {
            repositoryReservation.delete(reservation);
            return true;
        }).orElse(false);
        return d;
    }

}
