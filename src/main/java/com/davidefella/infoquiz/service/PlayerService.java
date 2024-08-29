package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.persistence.Player;
import com.davidefella.infoquiz.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }

    public Optional<Player> findByLastNameAndFirstName(String lastName, String firstName) {
        return playerRepository.findByLastNameAndFirstName(lastName, firstName);
    }

    /*
    * TODO: Add check fields
    * */
    public List<Player> saveAll(List<Player> players) {
        return playerRepository.saveAll(players);
    }


    public Player save(String firstName, String lastName) {

        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("First name and last name cannot be null");
        }

        Player player = new Player(lastName, firstName);
        return playerRepository.save(player);
    }

    /* Utile per test e DataLoader */
    public Player save(Player p ){
        return playerRepository.save(p);
    }

}
