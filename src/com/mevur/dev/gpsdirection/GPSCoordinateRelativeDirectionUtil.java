package com.mevur.dev.gpsdirection;

public class GPSCoordinateRelativeDirectionUtil {
    private final int NTU_FACTOR = 100000;
    private final int PI_TO_DEGREE = 180;
    public static void main(String[] args) {

        GPSCoordinateRelativeDirectionUtil gps = new GPSCoordinateRelativeDirectionUtil();
//        double d = gps.relativeDirection(106.560372, 29.526256,
//                                         106.559490, 29.527529);
//        = 268.4414533271115
//        double d = gps.relativeDirection(106.560372, 29.526256,
//                                         106.561585, 29.526672);
//        = 30.531301304337212
//        double d = gps.relativeDirection(106.560372, 29.526256,
//                106.560385, 29.526523);
//        double[] src = CoordinateTransformUtil.bd09towgs84(106.560372, 29.526256);
//        double[] desc = CoordinateTransformUtil.bd09towgs84(106.561189, 29.526393);
//        double d = gps.relativeDirection(src[0], src[1],
//                desc[0], desc[1]);
        double d = gps.relativeDirection(106.560372, 29.526256,
                106.56088, 29.525686);
        System.out.println(d);
    }

    /**
     * line two gps coordinate and calculate the direction angel between the line and the North direction
     * @param lat1 lat of point1
     * @param lng1 lng of point1
     * @param lat2 lat of point2
     * @param lng2 lng of point2
     * @return the direction angel
     */
    public double relativeDirection(double lat1, double lng1, double lat2, double lng2) {
        //convert to ntu lat & lng
        lat1 *= NTU_FACTOR;
        lng1 *= NTU_FACTOR;
        lat2 *= NTU_FACTOR;
        lng2 *= NTU_FACTOR;

        double dx = lat2 - lat1;
        double dy = lng2 - lng1;
        System.out.println(dx + " : " + dy);
        if (dx == 0) {
            return dy >= 0 ? 0 : 180;
        }
        if (dy == 0) {
            return dx >= 0 ? 90 : 270;
        }

        if (dx > 0 && dy > 0) {
            double theta = Math.atan(dx / dy);
            //consider the first cycle
            while (theta > 0.5) {
                theta -= 0.5;
            }
            return theta * PI_TO_DEGREE;
        } else if (dx > 0 && dy < 0) {
            double theta = Math.atan(Math.abs(dy) / dx);
            while (theta > 0.5) {
                theta -= 0.5;
            }
            return 90 +  theta * PI_TO_DEGREE;
        } else if (dx < 0 && dy < 0) {
            double theta = Math.atan(dx / dy);
            while (theta > 0.5) {
                theta -= 0.5;
            }
            return 180 + theta * PI_TO_DEGREE;
        } else {
            double theta = Math.atan(dy / Math.atan(dx));
            while (theta > 0.5) {
                theta -= 0.5;
            }
            return 270 + theta * PI_TO_DEGREE;
        }
    }

    /**
     * check if the theta between in currentDirection +- area
     * @param currentDirection current Direction base North direction
     * @param theta theta base North direction
     * @param area float area
     * @return if theta in the range of currentDirection - area to currentDirection + area,
     * return true;
     * otherwise return false
     */
    public boolean inFanArea(double currentDirection, double theta, double area) {
        return theta >= currentDirection - theta && theta <= currentDirection + area;
    }
}
