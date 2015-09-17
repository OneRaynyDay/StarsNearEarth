import java.text.DecimalFormat;

import cs_1c.StarNearEarth;

public class SNE_Analyzer extends StarNearEarth {
   private double x, y, z;

   // constructors
   public SNE_Analyzer() {
      super();
   }

   public SNE_Analyzer(StarNearEarth sne) {
      setRank(sne.getRank());
      setNameCns(sne.getNameCns());
      setNumComponents(sne.getNumComponents());
      setNameLhs(sne.getNameLhs());
      setRAsc(sne.getRAsc());
      setDec(sne.getDec());
      setPropMotionMag(sne.getPropMotionMag());
      setPropMotionDir(sne.getPropMotionDir());
      setParallaxMean(sne.getParallaxMean());
      setParallaxVariance(sne.getParallaxVariance());
      SetBWhiteDwarfFlag(sne.getWhiteDwarfFlag());
      setSpectralType(sne.getSpectralType());
      setMagApparent(sne.getMagApparent());
      setMagAbsolute(sne.getMagAbsolute());
      setMass(sne.getMass());
      setNotes(sne.getNotes());
      setNameCommon(sne.getNameCommon());
      calcCartCoords();
   }

   // accessors
   double getX() {
      return x;
   }

   double getY() {
      return y;
   }

   double getZ() {
      return z;
   }

   public void calcCartCoords() {
      double lightYears = 3.262 / (getParallaxMean());
      double rAsc = Math.toRadians(getRAsc());
      double dec = Math.toRadians(getDec());
      x = lightYears * Math.cos(dec) * Math.cos(rAsc);
      y = lightYears * Math.cos(dec) * Math.sin(rAsc);
      z = lightYears * Math.sin(dec);
   }

   public String coordToString() {
      DecimalFormat df = new DecimalFormat("##.####");
      return String.format("x : %15s y : %15s z : %15s", df.format(x),
            df.format(y), df.format(z));
   }
}
