import cs_1c.*;

//------------------------------------------------------
public class Foothill {
   // ------- main --------------
   public static void main(String[] args) throws Exception {
      int k, arraySize;
      double maxX, minX, maxY, minY, maxZ, minZ;
      final int NUM_COLS = 70;
      final int NUM_ROWS = 35;

      StarNearEarthReader starInput = new StarNearEarthReader(
            "nearest_stars.txt");

      if (starInput.readError()) {
         System.out.println("couldn't open " + starInput.getFileName()
               + " for input.");
         return;
      }

      // do this just to see if our read went well
      System.out.println(starInput.getFileName());
      System.out.println(starInput.getNumStars());

      // create an array of objects for our own use:
      arraySize = starInput.getNumStars();
      SNE_Analyzer[] starArray = new SNE_Analyzer[arraySize];
      for (k = 0; k < arraySize; k++)
         starArray[k] = new SNE_Analyzer(starInput.getStar(k));

      // display cartesian coords
      for (k = 0; k < arraySize; k++)
         System.out.printf("%35s: %15s \n", starArray[k].getNameCommon(),
               starArray[k].coordToString());

      // now for the graphing
      // get max and min coords for scaling
      double[] xVals, yVals, zVals;
      xVals = new double[arraySize];
      yVals = new double[arraySize];
      zVals = new double[arraySize];
      int[] ranks = new int[arraySize];
      maxX = minX = maxY = minY = maxZ = minZ = 0;
      for (k = 0; k < arraySize; k++) {
         double x = starArray[k].getX();
         double y = starArray[k].getY();
         double z = starArray[k].getZ();
         xVals[k] = x;
         yVals[k] = y;
         zVals[k] = z;
         ranks[k] = starArray[k].getRank();
         if (x > maxX)
            maxX = x;
         else if (x < minX)
            minX = x;
         if (y > maxY)
            maxY = y;
         else if (y < minY)
            minY = y;
         if (z > maxZ)
            maxZ = z;
         else if (z < minZ)
            minZ = z;
      }
      System.out.println("Display of x-y axis: ");
      populateMatrix(xVals, yVals, ranks, NUM_ROWS, NUM_COLS, minX, maxX, minY,
            maxY, arraySize);
   }

   /**
    * @param xVals
    *           /yVals/ranks, array of values to be passed in, to be read in
    *           parallel
    * @param rows
    *           , number of rows(default 70)
    * @param cols
    *           , number of columns(default 35)
    */
   public static void populateMatrix(double[] aVals, double[] bVals,
         int[] ranks, int rows, int cols, double minA, double maxA,
         double minB, double maxB, int arraySize) {
      int row, col;
      SparseMat<Character> starMap = new SparseMat<Character>(rows, cols, ' ');
      for (int k = 0; k < arraySize; k++) {
         int[] adjCoord = adjustToSize(new double[] { aVals[k], bVals[k] },
               minA, maxA, minB, maxB, rows, cols);
         row = adjCoord[0];
         col = adjCoord[1];
         if (ranks[k] < 10) {
            starMap.set(row, col, ("" + ranks[k]).charAt(0));
         } else
            starMap.set(row, col, '*');
      }

      // set sun at center
      int[] adjCoord = adjustToSize(new double[] { 0, 0 }, minA, maxA, minB,
            maxB, rows, cols);
      row = adjCoord[0];
      col = adjCoord[1];
      starMap.set(row, col, 'S');

      String outString = "";
      for (row = 0; row < rows; row++) {
         outString = "";
         for (col = 0; col < cols; col++) {
            outString += starMap.get(row, col);
         }
         System.out.println(outString);
      }
   }

   public static int[] adjustToSize(double[] coord, double minA, double maxA,
         double minB, double maxB, int aParts, int bParts) {
      int[] adjustedArr = new int[2];
      adjustedArr[0] = (int) ((coord[0] - minA) * (aParts) / (maxA - minA));
      adjustedArr[1] = (int) ((coord[1] - minB) * (bParts) / (maxB - minB));
      return adjustedArr;
   }
}
