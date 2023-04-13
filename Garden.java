import java.util.Scanner;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * Garden Class uses the methods from Contract to create and change gardens
 */
public class Garden implements Contract {

    private int energyLevel;
    private int sprayAmount;
    private Hashtable<String, Integer> plants;
    private Hashtable<String, Integer> basket;
    public static final Scanner userInput = new Scanner(System.in);


    /**
     * Class constructor which starts
     * @param name
     */
    public Garden(String name) {
        this.energyLevel = 100;
        this.sprayAmount = 64; // in ounces, equal to 1 gallon of pesticide
        this.plants = new Hashtable<String, Integer>();
        this.basket = new Hashtable<String, Integer>();
    }

    /**
     * methods which grows plants in garden by allowing user to input what type of plant they want to add to garden and how much to plant
     * @return the number of a given plant the user is currently growing in the garden at the end of the method
     */
    public Number grow() {
        System.out.println("Enter here what type of plant you would like to grow in this garden? ");
        String whatPlant = userInput.nextLine();
        System.out.println("How many of this plant would you like to have growing in your garden? (input as a digit)");
        Integer numPlants = userInput.nextInt();
        if (plants.containsKey(whatPlant)) {
            this.plants.replace(whatPlant, plants.get(whatPlant) + numPlants);
        } else {
            this.plants.put(whatPlant, numPlants);
        }
        System.out.println("You are now growing " + plants.get(whatPlant) + " " + whatPlant + " in this garden!");
        return plants.get(whatPlant);
    }

    /**
     * overloaded version of the grow method which takes in a plant and the number of this plant and adds this so this amount of the plant is now growing in the garden
     * @param plant
     * @param num
     * @return the number of a given plant the user is currently growing in the garden at the end of the method
     */
    public Number grow(String plant, int numPlant) {
        if (plants.containsKey(plant)) { 
            int num = plants.get(plant);
            this.plants.remove(plant);
            this.plants.put(plant, num + numPlant);
        } else {
            this.plants.put(plant, numPlant);
        }
        System.out.println("You are now growing " + plants.get(plant) + " " + plant + " in this garden!");
        return plants.get(plant);
    }


    /**
     * examines a plant in your garden inputted by the user and decide whether you want to grab the plant (by calling grab method) or not 
     * @param plant
     */
    public void examine(String plant) { 
        if (this.plants.containsKey(plant)) {
            System.out.println("Would you like to pick " + plant + " and put it in your basket? (yes/no)");
            String user_response = userInput.nextLine();  
            if (user_response.equals("yes")) {
                grab(plant);
            } else if (user_response.equals("no")) {
                System.out.println("You have chosen not to pick this plant.");
            } else {
                throw new RuntimeException("You cannot input text other than 'yes' or 'no'.");
            }
        } else {
            throw new RuntimeException("You can't examine " + plant + " because it is not in your garden.");
        }
    }
    
    /**
     * grabs a certain amount of a plant from the garden by removing it from the garden hashtable and adding it to the basket hashtable
     * @param plant
     */
    public void grab(String plant) {
        System.out.println("How many " + plant + " would you like to grab from your garden?");
        int grabPlant_response = userInput.nextInt();
        if (grabPlant_response > plants.get(plant)) {
            throw new RuntimeException("You can't grab more plants than you have currently growing in your garden.");
        } else{
            int num = plants.get(plant);
            this.plants.remove(plant);
            this.plants.put(plant, num - grabPlant_response);
            this.basket.put(plant, grabPlant_response);
        }
        if (plants.get(plant) == 0) { 
            this.plants.remove(plant);
            System.out.println("You have picked all " + plant + " in this garden.");
        }
        System.out.println("You have picked your " + plant + " and added it to your basket.");
        System.out.println("You now have the following plants in your basket: ");
        Enumeration<String> Enum = basket.keys();
        while (Enum.hasMoreElements()) {
          String key = Enum.nextElement();
          System.out.println("Plant: " + key + "\t Amount : " + basket.get(key));
        }
    }

    /**
     * the use method allows you to remove plants from your basket to use outside of the garden
     * @param plant
     */
    public void use(String plant) {
        if (basket.get(plant) == null) { 
            System.out.println("You can't call the use method for this plant because it is not in your basket.");
        } else {
            int num = basket.get(plant);
            System.out.println("How many of the " + num + " " + plant + " in your basket would you like to use?");
            int useResponse = userInput.nextInt();  
            this.basket.remove(plant);
            this.basket.put(plant, num - useResponse);
            if (basket.get(plant) == 0) {
                this.basket.remove(plant);
                System.out.println("You have now used all the " + plant + " in your basket.");
            } else{
            System.out.println("You now have " + (num - useResponse) + " " + plant + " in your basket.");
            }

        }
    } 

    /**
     * original shrink method takes in user input to check if a certain amount of a specific plant was picked/grabbed, how much the garden would shrink by
     * @return  however many of the given plant would be left if the user shrunk the garden by the given amount. Returns 0 if they gave innacurate values.
     */
    public Number shrink() {
        System.out.println("What plant are you considering grabbing from your garden? ");
        String shrinkResponse = userInput.nextLine();
        if (plants.get(shrinkResponse) != null) { 
            System.out.println("How many " + shrinkResponse + " are you considering grabbing from your garden?(answer with a digit) ");
            int shrinkResponse2 = userInput.nextInt();
            int num = plants.get(shrinkResponse) - shrinkResponse2;
            if (num <= 0) {
                System.out.println("You only have " + plants.get(shrinkResponse) + " " + shrinkResponse + " in your garden so you can't take out " + shrinkResponse2 + ".");
                return 0;
            } else {
                System.out.println("If you pick " + shrinkResponse2 + " " + shrinkResponse + " then you would have " + num + " left.");
                return num;
            }
        } else {
            System.out.println("This plant doesn't exist in your garden.");
            return 0;
        }
    }

    /**
     * overloaded shrink method takes a plant and number of the given plant in the method call and checks how much the garden would shrink by
     * @param plant
     * @param num
     * @return  however many of the given plant would be left if the user shrunk the garden by the given amount. Returns 0 if they gave innacurate values.
     */
    public Number shrink(String plant, int num) {
        System.out.println("You are considering picking " + num + " " + plant + " from your garden.");
        int firstNum = plants.get(plant);
        firstNum = firstNum - num;
        if (firstNum <= 0) {
        System.out.println("You only have " + plants.get(plant) + " " + plant + " so you can't take out " + num + ".");
        return 0;
        } else {
            System.out.println("If you pick " + num + " " + plant + " then you would have " + firstNum + " left.");
            return firstNum;
        }
    }

    /**
     * Will go on walk around garden in whatever direction the user has inputted unless they have too low energy in which they can't take a walk
     * @param direction
     * @return true if the user talks a walk and false if not
     */
    public boolean walk(String direction) {
        if (energyLevel - 2 < 0) {
            System.out.println("You can't take a walk, you're too tired. Trying resting first!");
            return false;
        } else {
            energyLevel -= 20; 
            System.out.println("You have just taken a walk in the " + direction + " direction. You're energy level has been lowered to " + energyLevel + "%.");
            return true;
        }
    }

    /**
     * method which restores the users energy level from whatever it currently is to 100
     */
    public void rest() {
        energyLevel = 100;
        System.out.println("You are now taking a rest.");
        System.out.println("You're energy level has been restored to 100%!");
    }

    /**
     * method that undos one of the plants that the user planted in the garden by removing it without putting it in your bucket.
     */
    public void undo() {
        System.out.println("What plant from your garden would you like to get rid of? ");
        String undoResponse = userInput.nextLine();
        if (plants.containsKey(undoResponse)) {
            plants.remove(undoResponse);
            System.out.println("You have just gotten rid of all of the " + undoResponse + " in your garden!");
        } else {
            System.out.println("You already don't have this plant in your garden.");
        }

    }

    /**
     * this method gets rid of flies in the garden by spraying pesticides, it takes in how much of the pesticide you want to use for one spray, and how manys sprays you want, after checking that you have enough pesticide to spray
     * @param x
     * @param y
     * @return true if you can spray the pesticide, false if there's not enough pesticide spray to use
     */
    public boolean fly(int numSprays, int amtSpray) {
        // 3 sprays, 10 oz of pesticide per spray
        int total_amt_spray = numSprays * amtSpray;
        if (sprayAmount < total_amt_spray) {
            System.out.println("You don't have enough pesticide spray to use this much on your garden.");
            return false;
        } else {
            sprayAmount -= total_amt_spray;
            System.out.println("You sprayed your garden with pesticides. Now you've gotten rid of your flies! You have a remaining " + sprayAmount + " oz of pesticide spray left to use.");
            return true;
        }
    }

    /**
     * This method takes in an item and then the user is told that they have dropped this item in their garden. 
     * @param item
     * @return the sentence that the user gets about what they dropped in their garden
     */
    public String drop(String item) {
        String dropSentence = "You just dropped a " + item + " in your garden!";
        System.out.println(dropSentence);
        return dropSentence;
    }

    /**
     * main method that creates gardens and calls methods that change them
     * @param args
     */
    public static void main(String[] args) { 
        Garden myGarden = new Garden("Julia's Garden");
        myGarden.grow("tomatoes", 3);
        myGarden.grow("tomatoes", 2);
        myGarden.examine("tomatoes");
        myGarden.use("tomatoes");
        myGarden.shrink("tomatoes", 2);
        myGarden.walk("Right");
        myGarden.rest();
        myGarden.undo();
        myGarden.fly(3, 10);
        myGarden.fly(2, 40);
        myGarden.drop("hose");
    }
}
