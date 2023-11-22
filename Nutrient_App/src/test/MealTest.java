 package src.test;

 import src.server.DataServices.MealQueries;
 import src.server.DataServices.UserQueries;

 public class MealTest {
     public static void main(String[] args) {
         // TODO Auto-generated method stub
         System.out.println(MealQueries.getTop6FoodGroupByPercentage(2));
         UserQueries a= new UserQueries();
//         System.out.println(a.getUserIDbyUsername("bao"));
//         System.out.println(ExerciseQueries.getCaloriesExpended(2));
//         try {
//             User newUser = new User("tester1", "test1", "ok", "nha", "F", "2003-11-11", 60, 170, "metric");
//
//             // Move the createUser method call into the try block
//             if (UserQueries.createUser(newUser)) {
//                 newUser.setId(UserQueries.getUserID());
//                 System.out.println("id trong test: " + newUser.getId()); // Debugging statement
//                 System.out.println("User signed up successfully!");
//
//                 FoodItem item1 = MealQueries.getFoodItem().get(0);
//                 FoodItem item2 = MealQueries.getFoodItem().get(1);
//                 FoodItem item3 = MealQueries.getFoodItem().get(2);
//
//                 Ingredient ingredient = new Ingredient(item1, 1, "g");
//                 Ingredient ingredient1 = new Ingredient(item2, 3, "g");
//                 Ingredient ingredient2 = new Ingredient(item3, 6, "g");
//
//                 List<Ingredient> ingredients = new ArrayList<>(Arrays.asList(ingredient, ingredient1, ingredient2));
//
//                 Calendar cal = Calendar.getInstance();
//                 cal.set(Calendar.YEAR, 2023);
//                 cal.set(Calendar.MONTH, Calendar.MAY);
//                 cal.set(Calendar.DAY_OF_MONTH, 1);
//                 DateLog dateLog = new DateLog(newUser.getId(), 0, cal.getTime(), null, null);
//
//                 Meal meal =  new Meal(1, MealType.BREAKFAST, ingredients);
//                 MealQueries.addMeal(dateLog, meal);
//
//                 System.out.println("Meal added successfully!");
//             } else {
//                 System.out.println("User signed up unsuccessfully!");
//             }
//         } catch (NumberFormatException ex) {
//             System.out.println("number");
//         } catch (SQLIntegrityConstraintViolationException ex) {
//             System.out.println("SQL");
//         } catch (Exception ex) {
//             System.out.println("other");
//         }
     }
 }
