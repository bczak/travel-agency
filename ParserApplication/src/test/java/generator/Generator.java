package generator;

import RSP.model.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class Generator
{
    private static final Random RAND = new Random();

    public static int randomInt()
    {
        return RAND.nextInt();
    }

    public static boolean randomBoolean()
    {
        return RAND.nextBoolean();
    }

//    public static User generateUser()
//    {
//        final User user = new User();
//
//        return user;
//    }


    public static Trip generateTrip() throws ParseException {
        final Trip trip = new Trip();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        String dateInString = "31-08-2021";
        Date date = sdf.parse(dateInString);

        trip.setStartDate(new Date());
        trip.setEndDate(date);
        trip.setLocation("ABAS City");
        trip.setName("Test" + randomInt());
        trip.setPrice(randomInt());

        return trip;
    }
}
