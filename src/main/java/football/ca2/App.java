package football.ca2;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Scanner;
public class App {

    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Scanner scanner = new Scanner(System.in);

        footballers b1 = new footballers();
		b1.setId(10);
		b1.setName("jurgen klopp");
		b1.setValue(163000);
		
        footballers b2 = new footballers();
		b2.setId(6);
		b2.setName("thomas tuchel");
		b2.setValue(105000);

		striker s1 = new striker();
		s1.setId(11);
		s1.setName("lionel messi");
		s1.setGoals(816);
		s1.setValue(2800000);
		
             striker s2 = new striker();
		s2.setId(7);
		s2.setName("kylian mbappe");
		s2.setGoals(282);
		s2.setValue(7200000);


		midfielder m1 = new midfielder();
		m1.setId(12);
		m1.setName("kevin de bruyne");
		m1.setAssists(102);
		m1.setValue(5600000);
		
             midfielder m2 = new midfielder();
		m2.setId(8);
		m2.setName("enzo fernandez");
		m2.setAssists(20);
		m2.setValue(1600000);
		
             
		defender d1 = new defender();
		d1.setId(14);
		d1.setName("virgil van dijk");
		d1.setTackles(352);
		d1.setValue(2700000);
		
             defender d2 = new defender();
		d2.setId(9);
		d2.setName("ruben dias");
		d2.setTackles(90);
		d2.setValue(930000);

		session.save(b1);
		session.save(s1);
		session.save(m1);
		session.save(d1);
             session.save(b2);
             session.save(s2);
             session.save(m2);
             session.save(d2);
		System.out.println("Data stored Successfully");
        while (true) {
            System.out.println("1. Add Football Player");
            System.out.println("2. Check Maximum Salary");
            System.out.println("3. Delete Football Player");
            System.out.println("4. View Football Player Details");
            System.out.println("5. View Football Players in Ascending Order of ID");
            System.out.println("6. View Football Players in Descending Order of ID");
            System.out.println("0. Exit");
            System.out.println("Choose an option:");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter the player's name: ");
                    String playerName = scanner.nextLine();

                    System.out.print("Enter the player's value: ");
                    int playerValue = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Player Position Striker(S) or Midfielder(M) or Defender(D)? : ");
                    String newposition = scanner.nextLine();

                    Query<Integer> lastIdQuery = session.createQuery("select max(id) from footballers", Integer.class);
                    Integer lastId = lastIdQuery.uniqueResult();
                    int newId = (lastId != null) ? lastId + 1 : 1;

                    footballers newPosition;
                    if ("S".equalsIgnoreCase(newposition)) {
                        striker fn1 = new striker();
                        fn1.setId(newId);
                        fn1.setName(playerName);
                        fn1.setValue(playerValue);

                        System.out.print("Enter the total goals: ");
                        int goals = scanner.nextInt();
                        fn1.setGoals(goals);

                        newPosition = fn1;
                    } else if ("M".equalsIgnoreCase(newposition)) {
                        midfielder fn2 = new midfielder();
                        fn2.setId(newId);
                        fn2.setName(playerName);
                        fn2.setValue(playerValue);

                        System.out.print("Enter the total assists: ");
                        int assists = scanner.nextInt();
                        fn2.setAssists(assists);

                        newPosition = fn2;
                    } else if ("D".equalsIgnoreCase(newposition)) {
                        defender fn3 = new defender();
                        fn3.setId(newId);
                        fn3.setName(playerName);
                        fn3.setValue(playerValue);

                        System.out.print("Enter the total tackles: ");
                        int tackles = scanner.nextInt();
                        fn3.setTackles(tackles);

                        newPosition = fn3;
                    } else {
                        System.out.println("Invalid choice. Please choose 'S' for Strikers or 'M' for Midfielder or 'D' for Defender.");
                        continue;
                    }
                    try {
                        session.save(newPosition);
                        tx.commit();
                        System.out.println("Football player added successfully.");
                    } catch (Exception e) {
                        tx.rollback();
                        System.out.println("Error occurred while adding the football player.");
                        e.printStackTrace();
                    }
                    break;

                case 2:
                	
                    Query<Integer> maxValueQuery = session.createQuery("select max(value) from footballers", Integer.class);
                    Integer maxValue = maxValueQuery.uniqueResult();

                    if (maxValue != null) {
                        System.out.println("Maximum value in the 'footballers' table: " + maxValue);
                    } else {
                        System.out.println("No records in the 'footballers' table.");
                    }
                    break;
                case 3:
                    System.out.print("Enter the ID of the player to delete: ");
                    int playerIdToDelete = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    Criteria criteria = session.createCriteria(footballers.class);
                    criteria.add(Restrictions.eq("id", playerIdToDelete));

                    footballers playerToDelete = (footballers) criteria.uniqueResult();

                    if (playerToDelete != null) {
                        try {
                            session.delete(playerToDelete);
                            tx.commit();
                            System.out.println("Football player with ID " + playerIdToDelete + " deleted successfully.");
                        } catch (Exception e) {
                            tx.rollback();
                            System.out.println("Error occurred while deleting the football player.");
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Football player with ID " + playerIdToDelete + " not found.");
                    }
                    break;

                case 4:
                    Query<footballers> query = session.createQuery("from footballers");
                    List<footballers> list = query.list();

                    for (footballers foot : list) {
                        System.out.println("ID: " + foot.getId());
                        System.out.println("Name: " + foot.getName());
                        System.out.println("Value: " + foot.getValue());
                    }
                    break;
                case 5:
                    String qu = "from footballers";
                    Query q1 = session.createQuery(qu);
                    Criteria c1 = session.createCriteria(footballers.class);
                    c1.addOrder(Order.asc("id"));
                    List<footballers> list1 = c1.list();
                    for (footballers r : list1) {
                        System.out.println(r.getId() + " " + r.getName() + "  " + r.getValue());
                    }
                    break;
                case 6:
                    String qo = "from footballers";
                    Query q2 = session.createQuery(qo);
                    Criteria c2 = session.createCriteria(footballers.class);
                    c2.addOrder(Order.desc("id"));
                    List<footballers> list2 = c2.list();
                    for (footballers r : list2) {
                        System.out.println(r.getId() + " " + r.getName() + "  " + r.getValue());
                    }
                    break;
                case 0:
                    // Exit
                    session.close();
                    factory.close();
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

