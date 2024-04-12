import java.util.ArrayList;
import java.util.Collections;
import java.util.scanner;

public class Card {
    private String suit;
    private String value;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    public int getValue() {
        if (value.equals("Ace")) return 11;
        if (value.equals("Two")) return 2;
        if (value.equals("Three")) return 3;
        if (value.equals("Four")) return 4;
        if (value.equals("Five")) return 5;
        if (value.equals("Six")) return 6;
        if (value.equals("Seven")) return 7;
        if (value.equals("Eight")) return 8;
        if (value.equals("Nine")) return 9;
        return 10;  // Ten, Jack, Queen, King
    }

    public String toString() {
        return value + " of " + suit;
    }
}

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
        String[] values = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
        for (String suit : suits) {
            for (String value : values) {
                cards.add(new Card(suit, value));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card deal() {
        return cards.remove(cards.size() - 1);
    }
}

public class Player {
    protected ArrayList<Card> hand;

    public Player() {
        hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public int calculateTotal() {
        int total = 0;
        int aces = 0;

        for (Card card : hand) {
            int value = card.getValue();
            if (value == 11) aces++;
            total += value;
        }

        while (total > 21 && aces > 0) {
            total -= 10;  // Using Ace as 1 instead of 11
            aces--;
        }

        return total;
    }

    public void displayHand() {
        for (Card card : hand) {
            System.out.println(card);
        }
        System.out.println("Total: " + calculateTotal());
    }

    public boolean hasBusted() {
        return calculateTotal() > 21;
    }
}

public class Player {
    protected ArrayList<Card> hand;

    public Player() {
        hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public int calculateTotal() {
        int total = 0;
        int aces = 0;

        for (Card card : hand) {
            int value = card.getValue();
            if (value == 11) aces++;
            total += value;
        }

        while (total > 21 && aces > 0) {
            total -= 10;  // Using Ace as 1 instead of 11
            aces--;
        }

        return total;
    }

    public void displayHand() {
        for (Card card : hand) {
            System.out.println(card);
        }
        System.out.println("Total: " + calculateTotal());
    }

    public boolean hasBusted() {
        return calculateTotal() > 21;
    }
}

public class Dealer extends Player {

    public void showFirstCard() {
        if (!hand.isEmpty()) {
            System.out.println("Dealer shows: " + hand.get(0));
        }
    }

    public boolean shouldHit() {
        return calculateTotal() < 17;
    }
}

public class Game {
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private Scanner scanner;

    public Game() {
        deck = new Deck();
        player = new Player();
        dealer = new Dealer();
        scanner = new Scanner(System.in);
    }

    public void initializeGame() {
        deck.shuffle();
        player.addCard(deck.deal());
        dealer.addCard(deck.deal());
        player.addCard(deck.deal());
        dealer.addCard(deck.deal());
    }

    public void playRound() {
        System.out.println("Your hand:");
        player.displayHand();

        System.out.println("Dealer shows:");
        dealer.showFirstCard();

        while (true) {
            System.out.print("Hit or Stand? (enter H or S): ");
            String action = scanner.nextLine();
            if (action.equalsIgnoreCase("H")) {
                player.addCard(deck.deal());
                player.displayHand();
                if (player.hasBusted()) {
                    System.out.println("You busted!");
                    return;
                }
            } else if (action.equalsIgnoreCase("S")) {
                break;
            }
        }

        dealerTurn();
    }

    private void dealerTurn() {
        System.out.println("Dealer's hand:");
        dealer.displayHand();

        while (dealer.shouldHit()) {
            dealer.addCard(deck.deal());
            System.out.println("Dealer hits.");
            dealer.displayHand();
            if (dealer.hasBusted()) {
                System.out.println("Dealer busts!");
                return;
            }
        }

        System.out.println("Dealer stands.");
    }

    public void checkWinCondition() {
        if (player.hasBusted()) {
            System.out.println("Dealer wins!");
        } else if (dealer.hasBusted()) {
            System.out.println("Player wins!");
        } else {
            int playerTotal = player.calculateTotal();
            int dealerTotal = dealer.calculateTotal();
            if (playerTotal > dealerTotal) {
                System.out.println("Player wins!");
            } else if (playerTotal < dealerTotal) {
                System.out.println("Dealer wins!");
            } else {
                System.out.println("It's a tie!");
            }
        }
    }

    public void playGame() {
        initializeGame();
        playRound();
        checkWinCondition();
    }
}

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
    }
}
