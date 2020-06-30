package machine;

import java.util.Scanner;

enum Action {
    BUY("buy"), FILL("fill"), TAKE("take"),
    REMAINING("remaining"), BACK("back"), EXIT("exit");

    String action;

    Action(String action) {
        this.action = action;
    }

    public static Action findByInput(String action) {
        for (Action value : values()) {
            if (action.equals(value.action)) {
                return value;
            }
        }
        throw new IllegalArgumentException(action +
                " is not a valid action");
    }
}

enum CoffeeType {
    ESPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6);

    private final int water;
    private final int milk;
    private final int coffeeBeans;
    private final int cost;

    CoffeeType(int water, int milk, int coffeeBeans, int cost) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cost = cost;
    }

    public int getWater() {
        return water;
    }

    public int getMilk() {
        return milk;
    }

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public int getCost() {
        return cost;
    }

}

public class CoffeeMachine {
    private static final Scanner scanner = new Scanner(System.in);
    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private int money;

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;
    }

    private static boolean chooseAction(CoffeeMachine coffeeMachine, String choose) {
        switch (Action.findByInput(choose)) {
            case BUY:
                coffeeMachine.buy();
                break;
            case FILL:
                coffeeMachine.fill();
                break;
            case TAKE:
                coffeeMachine.take();
                break;
            case REMAINING:
                coffeeMachine.showInfo();
                break;
            case EXIT:
                return false;
            default:
                System.out.println("Invalid input!");
                break;
        }
        return true;
    }

    private void buy() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        switch (scanner.next()) {
            case "1":
                checkResourcesAndBuy(CoffeeType.ESPRESSO);
                break;
            case "2":
                checkResourcesAndBuy(CoffeeType.LATTE);
                break;
            case "3":
                checkResourcesAndBuy(CoffeeType.CAPPUCCINO);
                break;
            case "back":
                break;
            default:
                System.out.println("coffee type not found. enter a valid input");
                break;
        }
    }

    private void checkResourcesAndBuy(CoffeeType coffee) {
        if (water - coffee.getWater() < 0) {
            System.out.println("Sorry, not enough water!\n");
        } else if (milk - coffee.getMilk() < 0) {
            System.out.println("Sorry, not enough milk!\n");
        } else if (coffeeBeans - coffee.getCoffeeBeans() < 0) {
            System.out.println("Sorry, not enough coffeeBeans!\n");
        } else if (cups - 1 < 0) {
            System.out.println("Sorry, not enough cups!\n");
        } else {
            System.out.println("I have enough resources, making you a coffee!\n");

            water -= coffee.getWater();
            milk -= coffee.getMilk();
            coffeeBeans -= coffee.getCoffeeBeans();
            cups -= 1;
            money += coffee.getCost();
        }
    }

    private void fill() {
        System.out.println("Write how many ml of water do you want to add:");
        water += scanner.nextInt();
        System.out.println("Write how many ml of milk do you want to add:");
        milk += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans do you want to add:");
        coffeeBeans += scanner.nextInt();
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        cups += scanner.nextInt();
    }

    private void take() {
        System.out.println("I gave you $" + money);
        money = 0;
    }

    public void showInfo() {
        System.out.println("\nThe coffee machine has:\n" +
                String.format("%d of water\n", water) +
                String.format("%d of milk\n", milk) +
                String.format("%d of coffee beans\n", coffeeBeans) +
                String.format("%d of disposable cups\n", cups) +
                String.format("$%d of money\n", money));
    }

    public static void main(String[] args) {

        CoffeeMachine coffeeMachine = new CoffeeMachine(400, 540, 120, 9, 550);

        do {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
        } while (chooseAction(coffeeMachine, scanner.next()));


    }
}