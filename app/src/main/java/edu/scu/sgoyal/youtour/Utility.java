package edu.scu.sgoyal.youtour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by suragys on 5/24/16.
 */
public class Utility {

    public static Map<Destination, Boolean> tourStatus ;


    public static Map<Destination,Boolean> getTourStatus()
    {
        if(tourStatus == null)
        {
            return setTourStatus();
        }
        return tourStatus;
    }

    public static Map<Destination,Boolean> setTourStatus()
    {
        tourStatus = new HashMap<Destination,Boolean>();
        ArrayList<Destination> destinations = new ArrayList<Destination>();
        destinations = Destination.getDestinations();
        for(Destination d : destinations)
        {
            tourStatus.put(d , false);
        }
        return tourStatus;
    }

//    public static ArrayList<Destination> configureDestination() {
//        ArrayList<Destination> destinations = new ArrayList<Destination>();
//
//
//        Destination d ;
//        destinations = new ArrayList<>();
////        Region r = new Region(
////                "monitored region",
////                UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
////                47753, 22035);
////        d = new Destination("Church", "ElRLtIus6xk", "Historic Mission Santa Clara is a beautiful icon that sits at the center of our campus.  First established in 1777, the Franciscan Order handed the Mission over to the Society of Jesus (the Jesuits) in 1851, who then started Santa Clara College, the first institution of higher education in California. " +
////                "Today, the Mission serves as the student chapel for Santa Clara Universtiy.", "church.jpg", 37.349284, -121.941061 );
//        destinations.add(new Destination("Library", "ez9Z7rHqk1Y", "The Learning Commons was built to create gathering places to use informational, technological, and media resources for teaching, learning, and scholarship. The full range of technology, coupled with" +
//                " knowledgeable human resources, helps visitors find, understand, evaluate, and manipulate information" +
//                "to meet their diverse needs.", "library.jpg", 37.348317, -121.938034,
//                new Region(
//                        "Library Beacon",
//                        UUID.fromString("8492E75F-4FD6-469D-B132-043FE94921D8"),
//                        11837, 15433)));
//        destinations.add(new Destination("Church", "ElRLtIus6xk", "Historic Mission Santa Clara is a beautiful icon that sits at the center of our campus.  First established in 1777, the Franciscan Order handed the Mission over to the Society of Jesus (the Jesuits) in 1851, who then started Santa Clara College, the first institution of higher education in California. " +
//                "Today, the Mission serves as the student chapel for Santa Clara Universtiy.", "church.jpg", 37.349284, -121.941061,
////                new Region(
////                        "Chruch Beacon",
////                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
////                        47753, 22035)));
//                new Region(
//                        "Church Beacon",
//                        UUID.fromString("8492E75F-4FD6-469D-B132-043FE94921D8"),
//                        1862, 3289)));
//        destinations.add(new Destination("Museum", "fekk_11n4Zw", "The de Saisset Museum houses an extensive collection numbering more than 12,000 objects. The museum's Art collection includes painting and sculpture, works on paper and photography, new media, and decorative arts. The History collection is divided into two main areas: California History and Mission-era liturgical vestments. Since its founding in 1955, the museum has developed large and encyclopedic holdings, covering a wide range of art historical periods and styles. Although the museum is committed to caring for every piece in its collection," +
//                "current collecting activity is predominantly focused on works by contemporary California artists.", "museum.jpg", 37.349989, -121.940611));
//        destinations.add(new Destination("Solar house", "RZP1ljoQLDA", "Radiant House is a 980 square-foot, net-zero energy home designed and constructed by Santa Clara University students from January 2012 through August, 2013, to compete in the Solar Decathalon.", "solar.jpg", 37.347774, -121.939269));
//        destinations.add(new Destination("Malley Center", "pQkyPbUW6Cw", "It is designed to be a gathering place for members of the campus community interested in physical activity. All individuals, regardless of ability level, are encouraged to utilize this campus resource to improve personal health and well-being. Both the building layout and facility amenities are intended to create a welcoming, user-friendly environment.\n" +
//                "\n" +
//                "The Malley Center has many features to ensure a rewarding recreational experience: air conditioning and heating in all rooms, numerous windows, and a \"clear story\" throughout the building to allow natural light to filter into the facility.", "maley.jpg", 37.348653, -121.936480,
//                new Region(
//                        "Malley Center",
//                        UUID.fromString("8492E75F-4FD6-469D-B132-043FE94921D8"),
//                        3990, 15433)));
//
//        return destinations;
//    }

}
