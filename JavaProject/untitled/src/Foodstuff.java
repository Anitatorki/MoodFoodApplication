import java.sql.*;
import java.util.Scanner;
/*
-Full Name: Anita Torki Harchegani
-Id: 2211985
-Course: ADVANCED OBJECT ORIENTED PROGRAMMING
-Subject: Final Project - Application FOOD MOOD
-Description of the application:
I create two table: table1 : foodmood/foodstuff and table2: foodmood/user
when application is run it first ask question about username and password and insert the information into the table user
then when user answer the question about food stuff(type of food, time , ...) insert information into the table foodstuff
and the application suggetion which food is better for user.
 */
public class Foodstuff {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;


        try {
            //register the my  mysql Driver

            Class.forName("com.mysql.cj.jdbc.Driver");
            // Create Connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodmood", "root", "Root");
            System.out.println("Connecting to database...");

            //Create statment
            String sqlSelect = "SELECT * FROM user WHERE username = ? AND  userpassword = ?";
            PreparedStatement pstm = conn.prepareStatement(sqlSelect);

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter UserName : ");
            String username = sc.nextLine();
            System.out.println("Enter Password: ");
            String password = sc.nextLine();


            if(isUserValid(conn, username, password)){
                System.out.println("Welcome " + username + "!");

            }else {
                addUser(conn, username, password);
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to Mood Food Application! :)");
            System.out.println("--------------------------------------------");

            //ask user about the write the type of food
            System.out.println("Please enter your type of food you ate today?");
            String typeFood = scanner.nextLine();

            //ask user about the write the portion size
            System.out.println("Please enter your portion size you ate today?(Grams)");
            String portionSize = scanner.nextLine();

            //ask user about the write the  portion size
            System.out.println("Please enter your time of day ate today?(AM, PM) ");
            String timeOfDay = scanner.nextLine();


            //ask user to enter number about the mood befor ate food
            System.out.println("Please enter the your mood before eating (1 to 10): ");
            int moodBefore = scanner.nextInt();



            //ask user to enter number about the mood after ate food
            System.out.println("Please enter the your mood after eating (1 to 10): ");
            int moodafter = scanner.nextInt();

            //ask user about their individual preferences and dietary restrictions(yes or no)
            System.out.println("Do you have some diet preferences or dietary restrictions?(Yes or No)");
            String dietindicidual = scanner.next();
            if(dietindicidual.contains("yes")) {

                System.out.println("Do you have some diet preferences or dietary restrictions?(vegetarian, vegan, Oats, Fermented foods, Fatty fish, Dark chocolate? ");
                String preferences = scanner.next();
                //suggetion to user for foods that are likely to improve the user's mood
                // I use information as this site: healtline.com and webmd.com/diet
                System.out.println("Why You Need Tryptophan? " + "\r\n " +
                        " Tryptophan has the lowest concentration in the body of any amino acid, yet," +
                        "\n" + " it is vital for a wide variety of metabolic functions that affect" +
                        " your mood, cognition, behaviour, depression, Learning, memory skills, visual cognition, and Aggression control.");

                System.out.println("--------------------------------------------------------------------------------------------");

                if (preferences.contains("vegetarian") && !typeFood.contains("meat")) {
                    System.out.println("Consider trying some lentils or tofu next time to boost your mood! Greens like spinach are high in tryptophan," +
                            " at 80 mg per ounce. Another cheap and accessible food source, potatoes are a great source of vitamin B6," +
                            "\n"+
                            " potassium, vitamin C and dietary fiber. Potatoes also contain 80 mg of tryptophan per ounce");
                } else if (preferences.contains("vegan") && !typeFood.contains("meat") && !typeFood.contains("dairy")) {
                    System.out.println("A vegan diet with plenty of nuts and seeds, soya, tofu, tempeh, beans, lentils, oats, " +
                            "leafy greens and bananas will provide you with enough serotonin building material." + "\n" +
                            "Oat Bran containing 315 mg (113% RDA) of tryptophan per cup, oat bran is cheap and easy to add to cereal, " +
                            "salads and other dishes. Wheat is also a good source of this amino acid.!");
                }  else if (preferences.contains("Oats")) {
                    System.out.println("Oats are a whole grain that can keep you in good spirits all morning. " + "\r" +
                            "You can enjoy them in many forms, such as overnight oats, oatmeal, muesli, and granola.\n" +
                            "\n" +
                            "They’re an excellent source of fiber, providing 8 grams in a single raw cup (81 grams)" +
                            "Prepared oatmeal can also be a good source of tryptophan, with 147 milligrams per cup");
                }
                else if (preferences.contains("Fermented foods")) {
                    System.out.println("Fermented foods are a source of tryptophan, an amino acid key to the production of serotonin, " +
                            "a messenger in the brain which influences several aspects of brain function, including mood.");
                }
                else if (typeFood.contains("Fatty fish")) {
                    System.out.println("Fish is a great source of omega-3 fatty acids, which are known to improve mood. " + "\r" +
                            "Fatty fish like salmon are rich in omega-3 fatty acids, which may lower your risk of depression." +
                            " Try some salmon or tuna, " +
                            "snapper, mackerel, cod, trout, carp, next time!");
                } else if (typeFood.contains("Dark chocolate")) {
                    System.out.println("Dark chocolate contains serotonin, which is a mood booster." +
                            " Dark chocolate is rich in compounds that may increase feel-good chemicals in your brain." +
                            " Chocolate can contain up to 18 milligrams of tryptophan per ounce.");
                } else {
                    System.out.println("Try to incorporate more fruits, vegetables, and whole grains into your diet to boost your mood!" +
                            "Some common sources of tryptophan are oats, bananas, dried prunes, milk, tuna fish, cheese, bread, chicken, turkey, peanuts, and chocolate!");
                }

            }else {
                System.out.println("Thank you for using the Food Mood application!");

            }


            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.println("The Food type: " + typeFood);
            System.out.println("The portion size: " + portionSize + "grams");
            System.out.println("The time of day: " + timeOfDay );
            System.out.println("your mood before eating food: " + moodBefore);
            System.out.println("Your mood after eating food: " + moodafter);
            System.out.println("Food Preferences and Restrictions: " + dietindicidual);

            System.out.println("--------------------------------------------------------------------------------------------");



            //generate insights about the relationship between food and mood for each user!!

            int moodFoodModify = moodafter - moodBefore;
            if (moodFoodModify > 0){
                System.out.println("This food seems to have a positive effect to your body and mood.It is better to eat that food again.");

            } else if (moodFoodModify<0) {
                System.out.println("This food seems to have a negitive effect to your body and mood.It is better not to eat that food again.");

            }else{
                System.out.println("This food seems to have no effect to your body and mood. \n " +
                        "Try to incorporate more fruits, vegetables, and whole grains into your diet to boost your mood!");
            }

            System.out.println("--------------------------------------------------------------------------------------------");

            try (PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO foodstuff (typeFood, portionSize, timeOfDay, moodBefore, moodafter, dietindicidual) VALUES (?, ?, ?, ?, ?, ?)")) {
                preparedStatement.setString(1, typeFood);
                preparedStatement.setString(2, portionSize);
                preparedStatement.setString(3, timeOfDay);
                preparedStatement.setInt(4, moodBefore);
                preparedStatement.setInt(5, moodafter);
                preparedStatement.setString(6, dietindicidual);
                preparedStatement.executeUpdate();
                System.out.println("Food mood entry inserted successfully!");
            }

            // Close the database connection
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


    private static void addUser(Connection conn, String username, String password) throws SQLException{
        String sqlInsert = "INSERT INTO user (username, userpassword) VALUES (?, ?)";
        PreparedStatement pstmInsert = conn.prepareStatement(sqlInsert);
        pstmInsert.setString(1, username);
        pstmInsert.setString(2, password);
        int rows = pstmInsert.executeUpdate();
        System.out.println("your are not a user but Your credentials will be added");
        System.out.println("Welcom " + username + "!" + "You are new user!");
    }

    //user valid
    private static boolean isUserValid(Connection conn, String username, String password) throws SQLException {
        boolean result = false;
        String sqlSelect = "SELECT * FROM user WHERE username = ? AND userpassword = ?";

        PreparedStatement pstm = conn.prepareStatement(sqlSelect);
        pstm.setString(1, username);
        pstm.setString(2, password);

        ResultSet rs = pstm.executeQuery();
        if(rs.next()) {
            String usernameFromDB = rs.getString("username");
            String passwordFromDB = rs.getString("userpassword");

            if(usernameFromDB.equals(username) && passwordFromDB.equals(password)){
                result = true;
            }
        }
        return result;
    }
}

