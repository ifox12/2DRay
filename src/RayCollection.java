import java.util.ArrayList;

public class RayCollection {
    ArrayList<Ray> rays = new ArrayList<>();
    int upperBound;
    int lowerBound;

    public RayCollection(Vector2D pov, Vector2D povDirectionInDegree) {
//        upperBound = povDirectionInDegree + 45;
//        if (upperBound > 360) {
//            upperBound -= 360;
//        }
//        lowerBound = povDirectionInDegree - 45;
//        if (lowerBound < 0) {
//            lowerBound += 360;
//        }

//        for (int currentAngle = lowerBound; currentAngle < upperBound; currentAngle++) {
            rays.add(new Ray(pov, povDirectionInDegree/*, povDirectionInDegree*/));
  //      }
    }

}
