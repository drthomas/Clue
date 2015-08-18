package com.me.clue.carddata;


import com.me.clue.model.BCharacter;

import java.util.ArrayList;
import java.util.Random;


public class Cards
{
    private Random rnd = new Random();

    public ArrayList<String> _deck = new ArrayList<String>(){};
    public ArrayList<String> _solution = new ArrayList<String>(){};
    public ArrayList<ArrayList<String>> _allCards = new ArrayList<ArrayList<String>>(){};
    public ArrayList<String> _faceUpCards = new ArrayList<String>(){};

    public ArrayList<String> getDeck() { return _deck; }
    public void setDeck(ArrayList<String> list) { _deck = list; }

    public ArrayList<String> getSolution() { return _solution; }
    public void setSolution(ArrayList<String> list) { _solution = list; }

    public ArrayList<ArrayList<String>> getAllCards() { return _allCards; }
    public void setAllCards(ArrayList<ArrayList<String>> list) { _allCards = list; }

    public ArrayList<String> getFaceUpCards() { return _faceUpCards; }
    public void setFaceUpCards(ArrayList<String> list) { _faceUpCards = list; }

    public Cards()
    {
        CreateDeck();
        CreateSolution();
    }

    public String GetType(String card)
    {
        if (CharacterCards.Characters.contains(card))
        {
            return "Character";
        }
        else if (LocationCards.Locations.contains(card))
        {
            return "Location";
        }
        else if (WeaponCards.Weapons.contains(card))
        {
            return "Weapon";
        }

        return null;
    }

    private void CreateDeck()
    {
        _deck.addAll(CharacterCards.Characters);
        _deck.addAll(LocationCards.Locations);
        _deck.addAll(WeaponCards.Weapons);

        _allCards.add(CharacterCards.Characters);
        _allCards.add(LocationCards.Locations);
        _allCards.add(WeaponCards.Weapons);
    }

    private void CreateSolution()
    {
        int n = CharacterCards.Characters.size();
        String solutionCharacter = CharacterCards.Characters.get(rnd.nextInt(n));
        _deck.remove(solutionCharacter);

        n = LocationCards.Locations.size();
        String solutionLocation = LocationCards.Locations.get(rnd.nextInt(n));
        _deck.remove(solutionLocation);

        n = WeaponCards.Weapons.size();
        String solutionWeapon = WeaponCards.Weapons.get(rnd.nextInt(n));
        _deck.remove(solutionWeapon);

        _solution.add(solutionCharacter);
        _solution.add(solutionLocation);
        _solution.add(solutionWeapon);
    }

    public void deal(ArrayList<BCharacter> players)
    {
        int handSize = _deck.size() / players.size();

        for (BCharacter player : players)
        {
            ArrayList<String> newHand = new ArrayList<String>() { };
            for (int i = 0; i < handSize; i++)
            {
                newHand.add(_deck.get(i));
            }

            for (String card : newHand)
            {
                _deck.remove(card);
            }

            player.getHand().addAll(newHand);
            player.getKnownCards().addAll(newHand);
        }

        _faceUpCards.addAll(_deck);

        for (BCharacter player : players)
        {
            player.getKnownCards().addAll(_faceUpCards);

            for (ArrayList<String> cards : _allCards)
            {
                player.getUnknownCards().addAll(cards);
            }

            for (String card : player.getKnownCards())
            {
                player.getUnknownCards().remove(card);
            }
        }
    }

    public void shuffle()
    {
        Random rnd = new Random();
        int n = _deck.size();
        while (n > 1)
        {
            n--;
            int k = rnd.nextInt(n + 1);
            String value = _deck.get(k);
            _deck.set(k, _deck.get(n));
            _deck.set(n, value);
        }
    }
}
